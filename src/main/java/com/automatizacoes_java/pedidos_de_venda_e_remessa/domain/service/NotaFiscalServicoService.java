package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.NotaFiscalServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.OrdemServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.NotaFiscalServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.OrdemServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.NfseDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarNfseResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

import jakarta.transaction.Transactional;

@Service
public class NotaFiscalServicoService {
	@Autowired
	NotaFiscalServicoRepository repository;

	@Autowired
	OrdemServicoRepository osRepository;

	@Autowired
	OmieApiClientService omieApiClientService;

	@Transactional
	public NotaFiscalServicoEntity criarOuAtualizarPorOmie(NfseDTO dto, EmpresaEntity empresa) {

		OrdemServicoId idOs = new OrdemServicoId(dto.getOrdemServico().getCodigoOs(), empresa.getCodigo());
		OrdemServicoEntity os = osRepository.findById(idOs).orElse(null);

		EntidadeCompostaId id = new EntidadeCompostaId(String.valueOf(dto.getCabecalho().getCodigoNf()),
				empresa.getCodigo());

		// Procura o cliente, se não existir, cria um novo
		NotaFiscalServicoEntity entidade = repository.findById(id)
				.orElse(new NotaFiscalServicoEntity(dto, empresa, os));

		// Atualiza os dados com as informações do DTO
		entidade.atualizarDados(dto);

		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}

	@Transactional
	public CompletableFuture<ResponseEntity<?>> getAllOmieUpdateBDA(List<EmpresaEntity> empresas) {

		if (empresas.isEmpty())
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Nenhuma Empresa Encontrada"));

		int totalRegistros = 0;
		for (EmpresaEntity e : empresas) {
			int paginaAtual = 1, totalPaginas = 0;

			try {
				Thread.sleep(1000L);
				do {
					OmieListarNfseResponse res = omieApiClientService.listarNfsePorPagina(e, paginaAtual).get();

					if (res == null || res.getNfseEncontradas().isEmpty())
						break;

					totalPaginas = res.getTotalDePaginas();

					for (NfseDTO dto : res.getNfseEncontradas()) {
						criarOuAtualizarPorOmie(dto, e); // este método: findById -> atualizarDados -> save
						totalRegistros++;
					}

//					System.out.printf("Empresa:\t%s\t|\tPagina:\t%d\t|\tTotalPagina:\t%d\t|\tTotalRegistros:\t%d%n",e.getNomeFantasia(), paginaAtual, totalPaginas, totalRegistros);

					paginaAtual++;
				} while (paginaAtual <= totalPaginas);
			} catch (InterruptedException | ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Erro no fluxo!"));
			}
		}

		return CompletableFuture
				.completedFuture(ResponseEntity.ok("Total de " + totalRegistros + " registros persistidos"));

	}
}

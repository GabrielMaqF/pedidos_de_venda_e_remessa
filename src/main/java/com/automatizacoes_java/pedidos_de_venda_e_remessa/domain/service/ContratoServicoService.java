package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContratoServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ContratoServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ContratoServicoCadastroDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarContratosServicoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

import jakarta.transaction.Transactional;

@Service
public class ContratoServicoService {

	/**
	 * O Spring injetará o ClienteRepository aqui automaticamente. Nós então o
	 * passamos para o construtor da classe mãe (BaseService) para que os métodos
	 * genéricos (findAll, findById) possam usá-lo.
	 *
	 * @param clienteRepository O repositório específico para ClienteEntity.
	 */
	@Autowired
	ContratoServicoRepository repository;

	@Autowired
	OmieApiClientService omieApiClientService;

	@Transactional
	public ContratoServicoEntity criarOuAtualizarPorOmie(ContratoServicoCadastroDTO dto, EmpresaEntity empresa) {
		EntidadeCompostaId id = new EntidadeCompostaId(String.valueOf(dto.getCabecalho().getCodigoContrato()),
				empresa.getCodigo());

		// Procura o cliente, se não existir, cria um novo
		ContratoServicoEntity entidade = repository.findById(id).orElse(new ContratoServicoEntity(dto, empresa));

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
					OmieListarContratosServicoResponse res = omieApiClientService
							.listarContratoServicoPorPagina(e, paginaAtual).get();

					if (res == null || res.getContratos() == null || res.getContratos().isEmpty())
						break;

					totalPaginas = res.getTotalDePaginas();

					for (ContratoServicoCadastroDTO dto : res.getContratos()) {
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

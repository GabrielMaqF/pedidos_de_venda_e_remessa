package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ProjetoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ProjetoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarProjetoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

import jakarta.transaction.Transactional;

@Service
public class ProjetoService extends BaseService<ProjetoEntity, EntidadeCompostaId, Long> {
	@Autowired
	ProjetoRepository repository;


	@Autowired
	OmieApiClientService omieApiClientService;

	@Autowired
	EmpresaService empresaService;

	@Transactional
	public ProjetoEntity criarOuAtualizarPorOmie(ProjetoDTO dto, EmpresaEntity empresa) {
		EntidadeCompostaId id = new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo());
		
		// Procura o cliente, se não existir, cria um novo
		ProjetoEntity entidade = repository.findById(id).orElse(new ProjetoEntity(dto, empresa));
		
		// Atualiza os dados com as informações do DTO
		entidade.atualizarDados(dto);
		
		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}

	@Transactional
	public CompletableFuture<ResponseEntity<?>> getAllOmieUpdateBDA() {
		List<EmpresaEntity> empresas = empresaService.findAll();

		if (empresas.isEmpty())
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Nenhuma Empresa Encontrada"));

		int paginaAtual = 1, totalPaginas, totalRegistros = 0;
		for (EmpresaEntity e : empresas) {

			try {
				Thread.sleep(1000L);
				do {
					OmieListarProjetoResponse res = omieApiClientService.listarProjetoPorPagina(e, paginaAtual).get();

					if (res == null || res.getCadastro().isEmpty())
						break;

					totalPaginas = res.getTotalDePaginas();

					for (ProjetoDTO dto : res.getCadastro()) {
						criarOuAtualizarPorOmie(dto, e); // este método: findById -> atualizarDados -> save
						totalRegistros++;
					}

					System.out.printf("Empresa:\t%s\t|\tPagina:\t%d\t|\tTotalPagina:\t%d\t|\tTotalRegistros:\t%d%n",
							e.getNomeFantasia(), paginaAtual, totalPaginas, totalRegistros);

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

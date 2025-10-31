package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.DepartamentoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.DepartamentoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarDepartamentoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

import jakarta.transaction.Transactional;

@Service
public class DepartamentoService extends BaseService<DepartamentoEntity, EntidadeCompostaId, String> {

	/**
	 * O Spring injetará o ClienteRepository aqui automaticamente. Nós então o
	 * passamos para o construtor da classe mãe (BaseService) para que os métodos
	 * genéricos (findAll, findById) possam usá-lo.
	 *
	 * @param clienteRepository O repositório específico para ClienteEntity.
	 */
	@Autowired
	DepartamentoRepository repository;

	@Autowired
	OmieApiClientService omieApiClientService;

	@Transactional
	public DepartamentoEntity criarOuAtualizarPorOmie(DepartamentoDTO dto, EmpresaEntity empresa) {
		EntidadeCompostaId id = new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo());

		// Procura o cliente, se não existir, cria um novo
		DepartamentoEntity entidade = repository.findById(id).orElse(new DepartamentoEntity(dto, empresa));

		// Atualiza os dados com as informações do DTO
		entidade.atualizarDados(dto);

		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}

	@Transactional
	public CompletableFuture<ResponseEntity<?>> getAllOmieUpdateBDA(List<EmpresaEntity> empresas) {

		if (empresas.isEmpty())
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Nenhuma Empresa Encontrada"));

		int paginaAtual = 1, totalPaginas, totalRegistros = 0;
		for (EmpresaEntity e : empresas) {

			try {
				Thread.sleep(1000L);
				do {
					OmieListarDepartamentoResponse res = omieApiClientService
							.listarDepartamentoPorPagina(e, paginaAtual).get();

					if (res == null || res.getDepartamentos().isEmpty())
						break;

					totalPaginas = res.getTotalDePaginas();

					for (DepartamentoDTO dto : res.getDepartamentos()) {
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

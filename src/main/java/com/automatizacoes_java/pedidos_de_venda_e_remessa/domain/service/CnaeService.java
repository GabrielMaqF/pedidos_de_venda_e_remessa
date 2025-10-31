package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CnaeEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.CnaeRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.CnaeDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarCnaeResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

import jakarta.transaction.Transactional;

@Service
public class CnaeService {

	@Autowired
	CnaeRepository repository;

	@Autowired
	OmieApiClientService omieApiClientService;

	@Transactional
	public CnaeEntity criarOuAtualizarPorOmie(CnaeDTO dto) {
		// Procura o cliente, se não existir, cria um novo
		CnaeEntity entidade = repository.findById(dto.getCodigo()).orElse(new CnaeEntity(dto));

		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}

	@Transactional
	public CompletableFuture<ResponseEntity<?>> getAllOmieUpdateBDA(List<EmpresaEntity> le) {
		EmpresaEntity e = le.getFirst();

		if (e == null)
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Nenhuma Empresa Encontrada"));

		int paginaAtual = 1, totalPaginas, totalRegistros = 0;

		try {
			Thread.sleep(1000L);
			do {
				OmieListarCnaeResponse res = omieApiClientService.listarCnaePorPagina(e, paginaAtual).get();

				if (res == null || res.getCadastros().isEmpty())
					break;

				totalPaginas = res.getTotalDePaginas();

				for (CnaeDTO dto : res.getCadastros()) {
					criarOuAtualizarPorOmie(dto); // este método: findById -> atualizarDados -> save
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

		return CompletableFuture
				.completedFuture(ResponseEntity.ok("Total de " + totalRegistros + " registros persistidos"));

	}
}

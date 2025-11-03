package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.TipoFaturamentoContratoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.TipoFaturamentoContratoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.TipoFaturamentoContratoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarTiposFaturamentoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

import jakarta.transaction.Transactional;

@Service
public class TipoFaturamentoContratoService {

	@Autowired
	TipoFaturamentoContratoRepository repository;

	@Autowired
	OmieApiClientService omieApiClientService;

	@Transactional
	public TipoFaturamentoContratoEntity criarOuAtualizarPorOmie(TipoFaturamentoContratoDTO dto) {
		// Procura o cliente, se não existir, cria um novo
		TipoFaturamentoContratoEntity entidade = repository.findById(dto.getCodigo())
				.orElse(new TipoFaturamentoContratoEntity(dto));

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
			OmieListarTiposFaturamentoResponse res = omieApiClientService.listarTiposFaturamento(e).get();

			if (res == null || res.getCadastros().isEmpty())
				return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Tipos de Faturamento de Contrato VAZIO!"));

			totalPaginas = res.getTotalDePaginas();

			for (TipoFaturamentoContratoDTO dto : res.getCadastros()) {
				criarOuAtualizarPorOmie(dto); // este método: findById -> atualizarDados -> save
				totalRegistros++;
			}

			System.out.printf("Empresa:\t%s\t|\tPagina:\t%d\t|\tTotalPagina:\t%d\t|\tTotalRegistros:\t%d%n",
					e.getNomeFantasia(), paginaAtual, totalPaginas, totalRegistros);

		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Erro no fluxo!"));
		}

		return CompletableFuture
				.completedFuture(ResponseEntity.ok("Total de " + totalRegistros + " registros persistidos"));

	}
}

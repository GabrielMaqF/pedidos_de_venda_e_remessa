package com.automatizacoes_java.pedidos_de_venda_e_remessa.config;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.CategoriaService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.ClienteFornecedorService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.CnaeService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.ContaCorrenteService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.DepartamentoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.EmpresaService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.NotaFiscalServicoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.OrdemServicoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.ProjetoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.VendedorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@Profile("test")
public class Testador implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Testador.class);
	private final EmpresaService eService;
	private final ClienteFornecedorService cfService;
	private final ProjetoService pjService;
	private final VendedorService vdService;
	private final ContaCorrenteService ccService;
	private final CategoriaService cgService;
	private final DepartamentoService dpService;
	private final CnaeService cnService;
	private final OrdemServicoService osService;
	private final NotaFiscalServicoService nfService;

	@Override
	public void run(String... args) {
		logger.info("--- INICIADO ---");

		List<EmpresaEntity> le = eService.findAll();

		if (le.isEmpty()) {
			logger.info("Sem Empresa");
			return;
		}

		logger.info("Total Empresa: ", le.size());
		for (EmpresaEntity e : le) {
			logger.info(e.toString());
		}

//      checkClienteFornecedor(le); 
//		checkProjeto(le);
//		checkVendedor(le);
//		checkContaCorrente(le);
//		checkCategoria(le);
//		checkDepartamento(le);
//		checkCnae(le);
//		checkOrdemServico(le);
//		checkNotaFiscal(le);
	}

	@Async
	public void checkClienteFornecedor(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = cfService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkProjeto(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = pjService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkVendedor(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = vdService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkContaCorrente(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = ccService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkCategoria(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = cgService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkDepartamento(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = dpService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkCnae(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = cnService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkOrdemServico(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = osService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

	@Async
	public void checkNotaFiscal(List<EmpresaEntity> le) {
		CompletableFuture<ResponseEntity<?>> fut = nfService.getAllOmieUpdateBDA(le);

		fut.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
				return;
			}
			if (res != null) {
				logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
			} else {
				logger.warn("Future completou, mas o ResponseEntity veio null");
			}
		}).join(); // aguarda para garantir que o log apareça no startup
	}

}

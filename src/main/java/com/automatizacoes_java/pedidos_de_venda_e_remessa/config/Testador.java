package com.automatizacoes_java.pedidos_de_venda_e_remessa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service.SincronizacaoService;

@Configuration
@Profile("test")
public class Testador implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(Testador.class);

	@Autowired
	SincronizacaoService sincronizacaoService;

	// --- DEPENDÊNCIAS ADICIONADAS PARA O TESTE OMIE ---
//	@Autowired
//	private OmieApiClientService omieApiClientService;
//
//	@Autowired
//	private EmpresaRepository empresaRepository;
	// ---------------------------------------------------

	@Override
	public void run(String... args) throws Exception {
//		sincronizacaoService.sincronizarEmpresas();
//		sincronizacaoService.sincronizarContratosServico();
		testarTudo();
	}

	private void testarTudo() throws Exception {

		// 1. Sincroniza os dados base do SharePoint (Empresas, Clientes, etc.)
		logger.info("--- INICIANDO TESTE: Sincronização de dados do SharePoint ---");
		sincronizacaoService.sincronizarTudo();
		logger.info("--- FIM TESTE: Sincronização de dados do SharePoint ---");

		// 2. Sincroniza as Ordens de Serviço do OMIE, que dependem dos dados acima
		logger.info("--- INICIANDO TESTE: Sincronização de Ordens de Serviço do OMIE ---");
		sincronizacaoService.sincronizarOrdensDeServico();
		logger.info("--- FIM TESTE: Sincronização de Ordens de Serviço do OMIE ---");

		sincronizacaoService.sincronizarNotasFiscais();

		System.out.println(" ===== FIM ===== ");
	}

}

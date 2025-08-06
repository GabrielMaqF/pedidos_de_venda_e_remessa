package com.automatizacoes_java.pedidos_de_venda_e_remessa.config;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service.SincronizacaoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarServicosResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

@Configuration
@Profile("test")
public class Testador implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(Testador.class);

	@Autowired
	SincronizacaoService sincronizacaoService;

	// --- DEPENDÊNCIAS PARA O TESTE INDIVIDUAL ---
	@Autowired
	private OmieApiClientService omieApiService;

	@Autowired
	private EmpresaRepository empresaRepository;
	// --- DEPENDÊNCIAS ADICIONADAS PARA O TESTE OMIE ---
//	@Autowired
//	private OmieApiClientService omieApiClientService;
//
//	@Autowired
//	private EmpresaRepository empresaRepository;
	// ---------------------------------------------------

	@Override
	public void run(String... args) throws Exception {
		sincronizacaoService.sincronizarEmpresas();
		sincronizacaoService.sincronizarServicosCadastrados();
	}
	
	private void testarTudo() throws Exception{

		// 1. Sincroniza os dados base do SharePoint (Empresas, Clientes, etc.)
		logger.info("--- INICIANDO TESTE: Sincronização de dados do SharePoint ---");
		sincronizacaoService.sincronizarTudo();
		logger.info("--- FIM TESTE: Sincronização de dados do SharePoint ---");

		// 2. Sincroniza as Ordens de Serviço do OMIE, que dependem dos dados acima
		logger.info("--- INICIANDO TESTE: Sincronização de Ordens de Serviço do OMIE ---");
		sincronizacaoService.sincronizarOrdensDeServico();
		logger.info("--- FIM TESTE: Sincronização de Ordens de Serviço do OMIE ---");
		
		System.out.println(" ===== FIM ===== ");
	}
	
	private void testarListarServicoCadastrado() throws ExecutionException, InterruptedException {
		sincronizacaoService.sincronizarEmpresas();
		logger.info("--- INICIANDO TESTE INDIVIDUAL DO MÉTODO listarServicosPorPagina ---");

		// 1. Tenta encontrar a primeira empresa no banco para usar nas credenciais
//		Optional<EmpresaEntity> empresaOptional = empresaRepository.findAll().stream().findFirst();
		Optional<EmpresaEntity> empresaOptional = empresaRepository.findByCodigo(2943821285L);

		if (empresaOptional.isEmpty()) {
			logger.error("!!! TESTE FALHOU: Nenhuma empresa encontrada no banco de dados para obter as credenciais da API.");
			return;
		}

		EmpresaEntity empresaTeste = empresaOptional.get();
		logger.info("Utilizando credenciais da empresa: '{}'", empresaTeste.getNomeFantasia());

		try {
			// 2. Chama o método que queremos testar e aguarda a sua conclusão
			logger.info("Chamando a API OMIE para ListarCadastroServico...");
			OmieListarServicosResponse resposta = omieApiService.listarServicosPorPagina(empresaTeste, 1).get();

			// 3. Imprime o resultado para análise
			logger.info(">>> RESPOSTA DA API RECEBIDA COM SUCESSO! <<<");
			logger.info("Página: {}", resposta.getPagina());
			logger.info("Total de Páginas: {}", resposta.getTotalDePaginas());
			if (resposta.getServicos() != null) {
				logger.info("Quantidade de serviços retornados: {}", resposta.getServicos().size());
				if (!resposta.getServicos().isEmpty()) {
					logger.info("Primeiro serviço retornado: {}", resposta.getServicos().get(0));
				}
			} else {
				logger.warn("A lista de serviços retornou nula.");
			}

		} catch (Exception e) {
			logger.error("!!! OCORREU UM ERRO DURANTE A CHAMADA DA API !!!", e);
		}

		logger.info("--- FIM DO TESTE INDIVIDUAL ---");
		System.out.println(" ===== FIM DO TESTE ===== ");
	}
	
//
//	private void testarListagemDeOrdemDeServico() {
//		// Busca a primeira empresa cadastrada no banco para usar como teste
//		empresaRepository.findAll().stream().forEach(e -> {
//
//			logger.info("Utilizando a empresa '{}' (CNPJ: {}) para o teste da OMIE.", e.getNomeFantasia(), e.getCnpj());
//
//			try {
//				// Chama o método e aguarda a resposta (bloqueia a thread principal aqui, o que
//				// é aceitável para um teste simples)
//				OmieListarOsResponse resposta = omieApiClientService.listarPrimeiraPaginaOs(e).get();
//
//				logger.info("Resposta da API OMIE recebida com sucesso!");
//				logger.info("Página: {}", resposta.getPagina());
//				logger.info("Total de Páginas: {}", resposta.getTotalDePaginas());
//				logger.info("Total de Registros: {}", resposta.getTotalDeRegistros());
//				logger.info("Quantidade de Ordens de Serviço nesta página: {}", resposta.getOrdensDeServico().size());
//
//				// Opcional: Imprimir detalhes da primeira OS, se existir
//				if (!resposta.getOrdensDeServico().isEmpty()) {
//					logger.info("Detalhes da primeira OS: {}", resposta.getOrdensDeServico().get(0));
//				}
//
//			} catch (Exception err) {
//				logger.error("!!! OCORREU UM ERRO AO CHAMAR A API DO OMIE !!!", err);
//			}
//
//		});
//
//	}
//	private void testarListagemDeOrdemDeServico() {
//		// Busca a primeira empresa cadastrada no banco para usar como teste
//		Optional<EmpresaEntity> empresaOptional = empresaRepository.findAll().stream().findFirst();
//
//		if (empresaOptional.isPresent()) {
//			EmpresaEntity empresaDeTeste = empresaOptional.get();
//			logger.info("Utilizando a empresa '{}' (CNPJ: {}) para o teste da OMIE.", empresaDeTeste.getNomeFantasia(),
//					empresaDeTeste.getCnpj());
//
//			try {
//				// Chama o método e aguarda a resposta (bloqueia a thread principal aqui, o que
//				// é aceitável para um teste simples)
//				OmieListarOsResponse resposta = omieApiClientService.listarPrimeiraPaginaOs(empresaDeTeste).get();
//
//				logger.info("Resposta da API OMIE recebida com sucesso!");
//				logger.info("Página: {}", resposta.getPagina());
//				logger.info("Total de Páginas: {}", resposta.getTotalDePaginas());
//				logger.info("Total de Registros: {}", resposta.getTotalDeRegistros());
//				logger.info("Quantidade de Ordens de Serviço nesta página: {}", resposta.getOrdensDeServico().size());
//
//				// Opcional: Imprimir detalhes da primeira OS, se existir
//				if (!resposta.getOrdensDeServico().isEmpty()) {
//					logger.info("Detalhes da primeira OS: {}", resposta.getOrdensDeServico().get(0));
//				}
//
//			} catch (Exception e) {
//				logger.error("!!! OCORREU UM ERRO AO CHAMAR A API DO OMIE !!!", e);
//			}
//		} else {
//			logger.warn("Nenhuma empresa encontrada no banco de dados. O teste da API OMIE não pode ser executado.");
//		}
//	}
}

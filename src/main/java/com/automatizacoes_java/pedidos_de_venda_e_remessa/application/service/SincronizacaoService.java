package com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.VendedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.ServicoPrestadoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_cnae.CnaeEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_contrato_servico.ContratoServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_nfse.NotaFiscalServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico.OrdemServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico.ServicoPrestadoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_servico_cadastrado.ServicoCadastroEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_tipo_faturamento_contrato.TipoFaturamentoContratoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.CategoriaRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ClienteRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ContaCorrenteRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.DepartamentoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ProjetoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.VendedorRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_cnae.CnaeRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_contrato_servico.ContratoServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_nfse.NotaFiscalServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_ordem_servico.OrdemServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_ordem_servico.ServicoPrestadoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_servico_cadastrado.ServicoCadastroRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_tipo_faturamento_contrato.TipoFaturamentoContratoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.CategoriaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ContaCorrenteDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.DepartamentoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ProjetoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.SharePointItemBaseDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.VendedorDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.AbstractSharePointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.CategoriaSharepointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ClienteSharepointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ContaCorrenteSharepointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.DepartamentoSharepointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.EmpresaSharepointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ProjetoSharepointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.VendedorSharepointService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_contrato_servico.ContratoServicoCadastroDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_nfse.NfseDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_servicos_cadastrados.ServicoCadastroDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarCnaeResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarContratosServicoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarNfseResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarServicosResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarTiposFaturamentoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

@Service
public class SincronizacaoService {

	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoService.class);

	@Autowired
	private EmpresaSharepointService empresaSharepointService;
	@Autowired
	private CategoriaSharepointService categoriaSharepointService;
	@Autowired
	private ClienteSharepointService clienteSharepointService;
	@Autowired
	private DepartamentoSharepointService departamentoSharepointService;
	@Autowired
	private ProjetoSharepointService projetoSharepointService;
	@Autowired
	private ContaCorrenteSharepointService contaCorrenteSharepointService;
	@Autowired
	private VendedorSharepointService vendedorSharepointService;

	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	private ProjetoRepository projetoRepository;
	@Autowired
	private VendedorRepository vendedorRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ServicoPrestadoRepository servicoPrestadoRepository;
	@Autowired
	private OmieApiClientService omieApiService;
	@Autowired
	private ServicoCadastroRepository servicoCadastroRepository;
	@Autowired
	private ContratoServicoRepository contratoRepository;
	@Autowired
	private TipoFaturamentoContratoRepository tipoFaturamentoRepository;
	@Autowired
	private CnaeRepository cnaeRepository;
	@Autowired
	private NotaFiscalServicoRepository notaFiscalServicoRepository;

	// ---------------------------- SHAREPOINT ------------------------------
	public void sincronizarTudo() {
		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO COMPLETA ---");
		try {
			sincronizarEmpresas();
			sincronizarCategorias();
			sincronizarDepartamento();
			sincronizarProjetos();
			sincronizarContaCorrente();
			sincronizarVendedor();
			sincronizarClientes();
			sincronizarServicosCadastrados();
			sincronizarContratosServico();
			sincronizarTiposFaturamentoContrato();
			sincronizarCnae();
			logger.info("--- ROTINA DE SINCRONIZAÇÃO COMPLETA CONCLUÍDA COM SUCESSO ---");
		} catch (Exception e) {
			logger.error("!!! ERRO CRÍTICO DURANTE A SINCRONIZAÇÃO !!!", e);
		}
	}

	@Transactional
	public void sincronizarEmpresas() throws ExecutionException, InterruptedException {
		logger.info("Sincronizando Empresas...");
		List<EmpresaDTO> dtos = empresaSharepointService.listarTodos().get();
		if (dtos.isEmpty()) {
			logger.info("Nenhuma empresa encontrada no SharePoint para sincronizar.");
			return;
		}

		// 1. Coletar os códigos das empresas (que são as chaves primárias)
		Set<Long> codigosEmpresa = dtos.stream().map(EmpresaDTO::getCodigoEmpresa).collect(Collectors.toSet());

		// 2. Buscar as entidades existentes pelos seus códigos
		Map<Long, EmpresaEntity> entidadesExistentes = empresaRepository.findAllById(codigosEmpresa).stream()
				.collect(Collectors.toMap(EmpresaEntity::getCodigo, Function.identity()));

		List<EmpresaEntity> entidadesParaSalvar = new ArrayList<>();
		for (EmpresaDTO dto : dtos) {
			// 3. Verificar se a entidade existe usando o código da empresa
			EmpresaEntity entidade = entidadesExistentes.get(dto.getCodigoEmpresa());
			if (entidade != null) {
				entidade.atualizarDados(dto);
			} else {
				entidade = new EmpresaEntity(dto);
			}
			entidadesParaSalvar.add(entidade);
		}

		empresaRepository.saveAll(entidadesParaSalvar);
		logger.info("Sincronização de Empresas concluída. {} registros processados.", entidadesParaSalvar.size());
	}

	@Transactional
	public void sincronizarCategorias() throws ExecutionException, InterruptedException {
		sincronizarEntidadeDependente("Categorias", categoriaSharepointService, categoriaRepository,
				CategoriaDTO::getNomeFantasiaEmpresa,
				(dto, empresa) -> new EntidadeCompostaId(dto.getCodigo(), empresa.getCodigo()), CategoriaEntity::new,
				(entidade, dto) -> entidade.atualizarDados(dto));
	}

	@Transactional
	public void sincronizarClientes() throws ExecutionException, InterruptedException {
		sincronizarEntidadeDependente("Clientes", clienteSharepointService, clienteRepository,
				ClienteDTO::getNomeFantasiaEmpresa,
				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
				ClienteEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
	}

	@Transactional
	public void sincronizarDepartamento() throws ExecutionException, InterruptedException {
		sincronizarEntidadeDependente("Departamentos", departamentoSharepointService, departamentoRepository,
				DepartamentoDTO::getNomeFantasiaEmpresa,
				(dto, empresa) -> new EntidadeCompostaId(dto.getCodigo(), empresa.getCodigo()), DepartamentoEntity::new,
				(entidade, dto) -> entidade.atualizarDados(dto));
	}

	@Transactional
	public void sincronizarProjetos() throws ExecutionException, InterruptedException {
		sincronizarEntidadeDependente("Projetos", projetoSharepointService, projetoRepository,
				ProjetoDTO::getNomeFantasiaEmpresa,
				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
				ProjetoEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
	}

	@Transactional
	public void sincronizarContaCorrente() throws ExecutionException, InterruptedException {
		sincronizarEntidadeDependente("Contas Correntes", contaCorrenteSharepointService, contaCorrenteRepository,
				ContaCorrenteDTO::getNomeFantasiaEmpresa,
				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
				ContaCorrenteEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
	}

	@Transactional
	public void sincronizarVendedor() throws ExecutionException, InterruptedException {
		sincronizarEntidadeDependente("Vendedores", vendedorSharepointService, vendedorRepository,
				VendedorDTO::getNomeFantasiaEmpresa,
				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
				VendedorEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
	}

	private <T_DTO extends SharePointItemBaseDTO,
			// CORREÇÃO 3: Usar wildcard (?) para indicar que aceitamos qualquer tipo de
			// BaseComposedEntity
			T_ENTITY extends BaseComposedEntity<?>> void sincronizarEntidadeDependente(String entidadeNome,
					AbstractSharePointService<T_DTO, ?> service, JpaRepository<T_ENTITY, EntidadeCompostaId> repository,
					Function<T_DTO, String> empresaNomeExtractor,
					BiFunction<T_DTO, EmpresaEntity, EntidadeCompostaId> idCreator,
					BiFunction<T_DTO, EmpresaEntity, T_ENTITY> entityCreator, BiConsumer<T_ENTITY, T_DTO> entityUpdater)
					throws ExecutionException, InterruptedException {

		logger.info("Sincronizando {}...", entidadeNome);

		List<T_DTO> dtos = service.listarTodos().get();
		if (dtos.isEmpty()) {
			logger.info("Nenhum(a) {} encontrado(a) no SharePoint para sincronizar.", entidadeNome.toLowerCase());
			return;
		}

		Set<String> nomesEmpresa = dtos.stream().map(empresaNomeExtractor).collect(Collectors.toSet());
		Map<String, EmpresaEntity> mapaDeEmpresas = empresaRepository.findByNomeFantasiaIn(nomesEmpresa).stream()
				.collect(Collectors.toMap(EmpresaEntity::getNomeFantasia, Function.identity()));

		Set<EntidadeCompostaId> idsParaBuscar = dtos.stream().map(dto -> {
			EmpresaEntity empresa = mapaDeEmpresas.get(empresaNomeExtractor.apply(dto));
			return empresa != null ? idCreator.apply(dto, empresa) : null;
		}).filter(id -> id != null).collect(Collectors.toSet());

		// Converte o Set para uma List para poder dividir em lotes
		List<EntidadeCompostaId> idsList = new ArrayList<>(idsParaBuscar);
		int batchSize = 500; // Um tamanho de lote razoável para a maioria dos bancos de dados
		Map<EntidadeCompostaId, T_ENTITY> entidadesExistentes = new java.util.HashMap<>();

		// Itera sobre a lista de IDs em lotes de 'batchSize'
		for (int i = 0; i < idsList.size(); i += batchSize) {
			int end = Math.min(i + batchSize, idsList.size());
			List<EntidadeCompostaId> batchIds = idsList.subList(i, end);

			// Busca apenas um lote de cada vez e adiciona ao mapa de resultados
			repository.findAllById(batchIds).forEach(entity -> entidadesExistentes.put(entity.getId(), entity));
		}

		// Bloco novo com salvamento em lotes
		List<T_ENTITY> loteParaSalvar = new ArrayList<>();
		final int batchSizeSave = 500; // O mesmo tamanho do lote de leitura ou um valor otimizado para escrita.
		int totalProcessado = 0;

		for (T_DTO dto : dtos) {
			EmpresaEntity empresaAssociada = mapaDeEmpresas.get(empresaNomeExtractor.apply(dto));
			if (empresaAssociada == null) {
				logger.warn("{} com SharePoint ID '{}' ignorado(a): empresa '{}' não encontrada.", entidadeNome,
						dto.getId(), empresaNomeExtractor.apply(dto));
				continue;
			}

			EntidadeCompostaId idAtual = idCreator.apply(dto, empresaAssociada);
			T_ENTITY entidade = entidadesExistentes.get(idAtual);

			if (entidade != null) {
				entidade.setEmpresa(empresaAssociada);
				entityUpdater.accept(entidade, dto);
			} else {
				entidade = entityCreator.apply(dto, empresaAssociada);
			}
			loteParaSalvar.add(entidade);

			// Verifica se o lote atingiu o tamanho máximo
			if (loteParaSalvar.size() == batchSizeSave) {
				repository.saveAll(loteParaSalvar);
				totalProcessado += loteParaSalvar.size();
				loteParaSalvar.clear(); // Limpa a lista para o próximo lote
				logger.info("... {} {} processados...", totalProcessado, entidadeNome);
			}
		}

		// Salva o lote final (caso o número total de itens não seja múltiplo do
		// batchSizeSave)
		if (!loteParaSalvar.isEmpty()) {
			repository.saveAll(loteParaSalvar);
			totalProcessado += loteParaSalvar.size();
		}

		logger.info("Sincronização de {} concluída. {} registros processados no total.", entidadeNome, totalProcessado);
	}

	// ---------------------------- OMIE ------------------------------
	@Transactional
	public void sincronizarNotasFiscais() {
		logger.info("--- Sincronizando Notas Fiscais (OMIE) ---");
		List<EmpresaEntity> empresas = empresaRepository.findAll();

		for (EmpresaEntity empresa : empresas) {
			try {
				Thread.sleep(1000L);
				
				logger.info("Processando Notas Fiscais para a empresa: {}", empresa.getNomeFantasia());
				int paginaAtual = 1;
				int totalPaginas;
				do {
					OmieListarNfseResponse resposta = omieApiService.listarNfsePorPagina(empresa, paginaAtual).get();
					if (resposta == null || resposta.getNfseEncontradas() == null
							|| resposta.getNfseEncontradas().isEmpty()) {
						break;
					}
					totalPaginas = resposta.getTotalDePaginas();

					List<NotaFiscalServicoEntity> loteParaSalvar = new ArrayList<>();
					for (NfseDTO dto : resposta.getNfseEncontradas()) {
						if (dto.getCabecalho() == null || dto.getCabecalho().getCodigoNf() == null)
							continue;
						OrdemServicoId osId = new OrdemServicoId(dto.getOrdemServico().getCodigoOs(),
								empresa.getCodigo());
						Optional<OrdemServicoEntity> osOptional = ordemServicoRepository.findById(osId);
						if (osOptional.isEmpty()) {
							logger.warn(
									"Não foi possível associar a NFS-e nº {} pois a OS com código '{}' não foi encontrada.",
									dto.getCabecalho().getNumeroNfse(), dto.getOrdemServico().getCodigoOs());
							continue;
						}
						OrdemServicoEntity osEncontrada = osOptional.get();

						EntidadeCompostaId nfId = new EntidadeCompostaId(
								String.valueOf(dto.getCabecalho().getCodigoNf()), empresa.getCodigo());
						NotaFiscalServicoEntity entidade = notaFiscalServicoRepository.findById(nfId)
								.orElseGet(() -> new NotaFiscalServicoEntity(dto, empresa, osEncontrada));

						entidade.setOrdemServico(osEncontrada); // Garante a associação
						entidade.atualizarDados(dto);
						loteParaSalvar.add(entidade);
					}
					notaFiscalServicoRepository.saveAll(loteParaSalvar);
					paginaAtual++;
				} while (paginaAtual <= totalPaginas);
			} catch (Exception e) {
				logger.error(
						"!!! FALHA ao sincronizar Notas Fiscais para a empresa '{}'. Continuando para a próxima. Erro: {}",
						empresa.getNomeFantasia(), e.getMessage());
			}
		}
		logger.info("--- Sincronização de Notas Fiscais concluída ---");
	}

	@Transactional
	public void sincronizarCnae() {
		logger.info("--- Sincronizando CNAE (OMIE) ---");
		empresaRepository.findAll().stream().findFirst().ifPresent(empresa -> {
			try {
				Thread.sleep(1000L);
				int paginaAtual = 1;
				int totalPaginas;
				do {
					OmieListarCnaeResponse resposta = omieApiService.listarCnaePorPagina(empresa, paginaAtual).get();
					if (resposta == null || resposta.getCadastros() == null || resposta.getCadastros().isEmpty()) {
						break;
					}
					totalPaginas = resposta.getTotalDePaginas();

					List<CnaeEntity> loteParaSalvar = resposta.getCadastros().stream().map(CnaeEntity::new)
							.collect(Collectors.toList());

					cnaeRepository.saveAll(loteParaSalvar);
					paginaAtual++;
				} while (paginaAtual <= totalPaginas);
			} catch (Exception e) {
				logger.error("!!! FALHA ao sincronizar CNAE. Erro: {}", e.getMessage());
			}
		});
		logger.info("--- Sincronização de CNAE concluída ---");
	}

	@Transactional
	public void sincronizarTiposFaturamentoContrato() {
		logger.info("--- Sincronizando Tipos de Faturamento de Contrato (OMIE) ---");
		// Como os tipos são globais, pegamos a primeira empresa apenas para ter as
		// credenciais
		empresaRepository.findAll().stream().findFirst().ifPresent(empresa -> {
			try {
				OmieListarTiposFaturamentoResponse resposta = omieApiService.listarTiposFaturamento(empresa).get();
				if (resposta != null && resposta.getCadastros() != null) {
					List<TipoFaturamentoContratoEntity> listaParaSalvar = resposta.getCadastros().stream()
							.map(TipoFaturamentoContratoEntity::new).collect(Collectors.toList());
					tipoFaturamentoRepository.saveAll(listaParaSalvar);
				}
			} catch (Exception e) {
				logger.error("!!! FALHA ao sincronizar Tipos de Faturamento de Contrato. Erro: {}", e.getMessage());
			}
		});
		logger.info("--- Sincronização de Tipos de Faturamento de Contrato concluída ---");
	}

	@Transactional
	public void sincronizarServicosCadastrados() throws ExecutionException, InterruptedException {
		logger.info("--- Sincronizando Serviços Cadastrados (OMIE) ---");
		List<EmpresaEntity> empresas = empresaRepository.findAll();

		for (EmpresaEntity empresa : empresas) {
			Thread.sleep(1000L);
			int paginaAtual = 1;
			int totalPaginas;
			do {
				OmieListarServicosResponse resposta = omieApiService.listarServicosPorPagina(empresa, paginaAtual)
						.get();
				if (resposta == null || resposta.getServicos() == null || resposta.getServicos().isEmpty()) {
					break;
				}
				totalPaginas = resposta.getTotalDePaginas();

				List<ServicoCadastroEntity> loteParaSalvar = new ArrayList<>();
				for (ServicoCadastroDTO dto : resposta.getServicos()) {
					if (dto.getIntListar() == null || dto.getIntListar().getCodigoServico() == null)
						continue;

					EntidadeCompostaId id = new EntidadeCompostaId(
							String.valueOf(dto.getIntListar().getCodigoServico()), empresa.getCodigo());
					ServicoCadastroEntity entidade = servicoCadastroRepository.findById(id)
							.orElseGet(() -> new ServicoCadastroEntity(dto, empresa));
					entidade.atualizarDados(dto);
					loteParaSalvar.add(entidade);
				}
				servicoCadastroRepository.saveAll(loteParaSalvar);
				paginaAtual++;
			} while (paginaAtual <= totalPaginas);
		}
		logger.info("--- Sincronização de Serviços Cadastrados concluída ---");
	}

	@Transactional
	public void sincronizarContratosServico() {
		logger.info("--- Sincronizando Contratos (OMIE) ---");
		List<EmpresaEntity> empresas = empresaRepository.findAll();

		for (EmpresaEntity empresa : empresas) {
			try {
				Thread.sleep(1000L);
				logger.info("Processando Contratos para a empresa: {}", empresa.getNomeFantasia());
				int paginaAtual = 1;
				int totalPaginas;
				do {
					OmieListarContratosServicoResponse resposta = omieApiService
							.listarContratosPorPagina(empresa, paginaAtual).get();
					if (resposta == null || resposta.getContratos() == null || resposta.getContratos().isEmpty()) {
						break;
					}
					totalPaginas = resposta.getTotalDePaginas();

					List<ContratoServicoEntity> loteParaSalvar = new ArrayList<>();
					for (ContratoServicoCadastroDTO dto : resposta.getContratos()) {
						if (dto.getCabecalho() == null || dto.getCabecalho().getCodigoContrato() == null)
							continue;

						EntidadeCompostaId id = new EntidadeCompostaId(
								String.valueOf(dto.getCabecalho().getCodigoContrato()), empresa.getCodigo());
						ContratoServicoEntity entidade = contratoRepository.findById(id)
								.orElseGet(() -> new ContratoServicoEntity(dto, empresa));
						entidade.atualizarDados(dto);
						loteParaSalvar.add(entidade);
					}
					contratoRepository.saveAll(loteParaSalvar);
					paginaAtual++;
				} while (paginaAtual <= totalPaginas);

			} catch (Exception e) {
				logger.error(
						"!!! FALHA ao sincronizar contratos para a empresa '{}'. Continuando para a próxima. Erro: {}",
						empresa.getNomeFantasia(), e.getMessage());
			}
		}
		logger.info("--- Sincronização de Contratos concluída ---");
	}

	@Transactional
	public void sincronizarOrdensDeServico() throws ExecutionException, InterruptedException {
		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
		List<EmpresaEntity> empresas = empresaRepository.findAll();

		for (EmpresaEntity empresa : empresas) {
			Thread.sleep(1000L);
			logger.info("Processando Ordens de Serviço para a empresa: {}", empresa.getNomeFantasia());
			int paginaAtual = 1;
			int totalPaginas;

			do {
				if (paginaAtual > 1) {
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}

				OmieListarOsResponse resposta = omieApiService.listarOsPorPagina(empresa, paginaAtual).get();
				totalPaginas = resposta.getTotalDePaginas();

				if (resposta.getOrdensDeServico() == null || resposta.getOrdensDeServico().isEmpty()) {
					logger.info("Nenhuma OS encontrada na página {} para a empresa {}. Fim da paginação.", paginaAtual,
							empresa.getNomeFantasia());
					break;
				}

				List<OrdemServicoDTO> osDtosDaPagina = resposta.getOrdensDeServico();

				// 1. Construir todas as chaves compostas dos itens de serviço da página
				Set<ServicoPrestadoId> todasAsChavesDeItens = osDtosDaPagina.stream()
						.filter(os -> os.getServicosPrestados() != null)
						.flatMap(
								os -> os.getServicosPrestados().stream()
										.map(itemDto -> new ServicoPrestadoId(os.getCabecalho().getCodigoOs(),
												empresa.getCodigo(), itemDto.getSequenciaItem())))
						.collect(Collectors.toSet());

				// 2. Buscar todos os itens existentes no banco com base na chave composta
				Map<ServicoPrestadoId, ServicoPrestadoEntity> mapaItensExistentes = servicoPrestadoRepository
						.findAllById(todasAsChavesDeItens).stream()
						.collect(Collectors.toMap(ServicoPrestadoEntity::getId, Function.identity()));

				List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();

				for (OrdemServicoDTO dto : osDtosDaPagina) {
					// ... (toda a sua lógica de validação de Cliente, Categoria, etc., permanece
					// EXATAMENTE A MESMA) ...
					ClienteEntity cliente = findCliente(dto.getCabecalho().getCodigoCliente(), empresa);
					if (cliente == null) {
						logger.warn(
								"OS nº {} IGNORADA: Cliente com código OMIE '{}' não encontrado para a empresa '{}'.",
								dto.getCabecalho().getNumeroOs(), dto.getCabecalho().getCodigoCliente(),
								empresa.getNomeFantasia());
						continue;
					}
					CategoriaEntity categoria = findCategoria(dto.getInformacoesAdicionais().getCodigoCategoria(),
							empresa);
					ContaCorrenteEntity contaCorrente = findContaCorrente(
							dto.getInformacoesAdicionais().getCodigoContaCorrente(), empresa);
					ProjetoEntity projeto = findProjeto(dto.getInformacoesAdicionais().getCodigoProjeto(), empresa);
					List<DepartamentoEntity> departamentosAssociados = new ArrayList<>();
					if (dto.getDepartamentos() != null) {
						for (var deptoDto : dto.getDepartamentos()) {
							findDepartamento(deptoDto.getCodigoDepartamento(), empresa)
									.ifPresent(departamentosAssociados::add);
						}
					}

					VendedorEntity vendedor = findVendedor(dto.getCabecalho().getCodigoVendedor(), empresa);

					// 3. Crie ou atualize a OrdemServicoEntity
					OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
					OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId)
							.orElseGet(OrdemServicoEntity::new);

					// 4. Atualize os dados da OS (que agora inclui parcelas, email e departamentos)
					osEntity.atualizarDados(dto, empresa, cliente, categoria, contaCorrente, projeto,
							departamentosAssociados, vendedor);

					// 5. Atualize a coleção de itens de serviço manualmente
					atualizarItensDeServico(osEntity, dto.getServicosPrestados(), mapaItensExistentes);

					loteParaSalvar.add(osEntity);
				}

				if (!loteParaSalvar.isEmpty()) {
					ordemServicoRepository.saveAll(loteParaSalvar);
				}

				paginaAtual++;
			} while (paginaAtual <= totalPaginas);

			logger.info("Sincronização de Ordens de Serviço concluída para a empresa: {}", empresa.getNomeFantasia());
		}
		logger.info("--- FIM DA ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
	}

	private void atualizarItensDeServico(OrdemServicoEntity osEntity, List<ServicoPrestadoDTO> dtos,
			Map<ServicoPrestadoId, ServicoPrestadoEntity> mapaItensExistentes) {

		if (dtos == null) {
			osEntity.getServicos().clear();
			return;
		}

		// Mantém um controle dos itens que já foram processados
		Set<ServicoPrestadoId> idsProcessados = new java.util.HashSet<>();

		for (ServicoPrestadoDTO dto : dtos) {
			// Cria a chave composta para o item atual
			ServicoPrestadoId itemId = new ServicoPrestadoId(osEntity.getId().getCodigoOs(),
					osEntity.getId().getEmpresaCodigo(), dto.getSequenciaItem());
			idsProcessados.add(itemId);

			// Busca o item no mapa de itens pré-buscados do banco
			ServicoPrestadoEntity itemEntity = mapaItensExistentes.get(itemId);

			if (itemEntity != null) {
				// Se já existe no banco, apenas atualiza os dados
				itemEntity.atualizarDados(dto);
			} else {
				// Se não existe no banco, cria uma nova entidade e a adiciona à coleção
				itemEntity = new ServicoPrestadoEntity(dto, osEntity);
				osEntity.getServicos().add(itemEntity);
			}
		}

		// Remove da coleção da entidade os itens que não vieram mais no DTO (se
		// necessário)
		osEntity.getServicos().removeIf(item -> !idsProcessados.contains(item.getId()));
	}

	// Métodos auxiliares para buscar dependências (findCliente, findCategoria,
	// etc.)
	private ClienteEntity findCliente(Long cod, EmpresaEntity emp) {
		return (cod == null) ? null
				: clienteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null);
	}

	private CategoriaEntity findCategoria(String cod, EmpresaEntity emp) {
		return (cod == null || cod.trim().isEmpty()) ? null
				: categoriaRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo())).orElse(null);
	}

	private ContaCorrenteEntity findContaCorrente(Long cod, EmpresaEntity emp) {
		return (cod == null) ? null
				: contaCorrenteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo()))
						.orElse(null);
	}

	private ProjetoEntity findProjeto(Long cod, EmpresaEntity emp) {
		return (cod == null) ? null
				: projetoRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null);
	}

	private VendedorEntity findVendedor(Long cod, EmpresaEntity emp) {
		return (cod == null) ? null
				: vendedorRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo()))
						.orElse(null);
	}

	private Optional<DepartamentoEntity> findDepartamento(String cod, EmpresaEntity emp) {
		return (cod == null) ? Optional.empty()
				: departamentoRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo()));
	}
}
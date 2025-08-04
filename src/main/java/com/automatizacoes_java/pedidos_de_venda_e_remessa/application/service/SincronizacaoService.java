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
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.OrdemServicoDepartamentoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.OrdemServicoEmailEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.OrdemServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.OrdemServicoParcelaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.ServicoPrestadoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.CategoriaRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ClienteRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ContaCorrenteRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.DepartamentoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ProjetoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.VendedorRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.relatorio.OrdemServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.relatorio.ServicoPrestadoRepository;
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
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.EmailDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ParcelaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
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
	public void sincronizarOrdensDeServico() throws ExecutionException, InterruptedException {
		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
		List<EmpresaEntity> empresas = empresaRepository.findAll();

		for (EmpresaEntity empresa : empresas) {
			logger.info("Processando Ordens de Serviço para a empresa: {}", empresa.getNomeFantasia());
			int paginaAtual = 1;
			int totalPaginas;

			do {
				if (paginaAtual > 1) {
					try {
						Thread.sleep(1000L); // Delay de 1 segundo
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
				// 1. Colete todos os IDs de itens de serviço da página atual
				List<OrdemServicoDTO> osDtosDaPagina = resposta.getOrdensDeServico();
				Set<Long> todosOsIdItensDaPagina = osDtosDaPagina.stream()
						.filter(os -> os.getServicosPrestados() != null)
						.flatMap(os -> os.getServicosPrestados().stream()).map(ServicoPrestadoDTO::getIdItem)
						.collect(Collectors.toSet());

				// 2. Busque todos os itens existentes no banco de uma só vez
				Map<Long, ServicoPrestadoEntity> mapaItensExistentes = servicoPrestadoRepository
						.findAllById(todosOsIdItensDaPagina).stream()
						.collect(Collectors.toMap(ServicoPrestadoEntity::getId, Function.identity()));

				List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();

				for (OrdemServicoDTO dto : osDtosDaPagina) {
					// ... (toda a sua lógica de validação de Cliente, Categoria, etc., permanece a
					// mesma) ...
					ClienteEntity cliente = findCliente(dto.getCabecalho().getCodigoCliente(), empresa);
					if (cliente == null) {
						/* ... */ continue;
					}
					CategoriaEntity categoria = findCategoria(dto.getInformacoesAdicionais().getCodigoCategoria(),
							empresa);
					ContaCorrenteEntity contaCorrente = findContaCorrente(
							dto.getInformacoesAdicionais().getCodigoContaCorrente(), empresa);
					ProjetoEntity projeto = findProjeto(dto.getInformacoesAdicionais().getCodigoProjeto(), empresa);
					List<DepartamentoEntity> departamentosAssociados = new ArrayList<>();
					if (dto.getDepartamentos() != null) {
						/* ... */ }

					// 3. Crie ou atualize a OrdemServicoEntity (sem as coleções)
					OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
					OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId)
							.orElseGet(OrdemServicoEntity::new);
					osEntity.atualizarDados(dto, empresa, cliente, categoria, contaCorrente, projeto,
							departamentosAssociados);

					// 4. ATUALIZE AS COLEÇÕES MANUALMENTE
					atualizarItensDeServico(osEntity, dto.getServicosPrestados(), mapaItensExistentes);
					atualizarParcelas(osEntity, dto.getParcelas());
					atualizarDepartamentos(osEntity, dto.getDepartamentos(), departamentosAssociados);
					atualizarEmail(osEntity, dto.getEmail());

					loteParaSalvar.add(osEntity);
				}
//				logger.info("Processando página {} de {} para a empresa '{}'. {} registros encontrados.", paginaAtual,
//						totalPaginas, empresa.getNomeFantasia(), resposta.getOrdensDeServico().size());
//				List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();
				// Dentro do método sincronizarOrdensDeServico, substitua o loop 'for' por este:
//
//				for (OrdemServicoDTO dto : resposta.getOrdensDeServico()) {
//					// --- 1. Validação do Cliente (já estava correto) ---
//					ClienteEntity cliente = findCliente(dto.getCabecalho().getCodigoCliente(), empresa);
//					if (cliente == null) {
//						logger.warn(
//								"OS nº {} IGNORADA: Cliente com código OMIE '{}' não encontrado para a empresa '{}'.",
//								dto.getCabecalho().getNumeroOs(), dto.getCabecalho().getCodigoCliente(),
//								empresa.getNomeFantasia());
//						continue;
//					}
//
//					// --- 2. Validação das outras dependências (ADIÇÃO SUGERIDA) ---
//					String codigoCategoriaDTO = dto.getInformacoesAdicionais().getCodigoCategoria();
//					CategoriaEntity categoria = findCategoria(codigoCategoriaDTO, empresa);
//					if (codigoCategoriaDTO != null && !codigoCategoriaDTO.trim().isEmpty() && categoria == null) {
//						logger.warn(
//								"OS nº {}: Categoria com código OMIE '{}' não encontrada para a empresa '{}'. O campo ficará nulo.",
//								dto.getCabecalho().getNumeroOs(), codigoCategoriaDTO, empresa.getNomeFantasia());
//					}
//
//					Long codigoContaCorrenteDTO = dto.getInformacoesAdicionais().getCodigoContaCorrente();
//					ContaCorrenteEntity contaCorrente = findContaCorrente(codigoContaCorrenteDTO, empresa);
//					if (codigoContaCorrenteDTO != null && contaCorrente == null) {
//						logger.warn(
//								"OS nº {}: Conta Corrente com código OMIE '{}' não encontrada para a empresa '{}'. O campo ficará nulo.",
//								dto.getCabecalho().getNumeroOs(), codigoContaCorrenteDTO, empresa.getNomeFantasia());
//					}
//
//					Long codigoProjetoDTO = dto.getInformacoesAdicionais().getCodigoProjeto();
//					ProjetoEntity projeto = findProjeto(codigoProjetoDTO, empresa);
//					if (codigoProjetoDTO != null && projeto == null) {
//						logger.warn(
//								"OS nº {}: Projeto com código OMIE '{}' não encontrado para a empresa '{}'. O campo ficará nulo.",
//								dto.getCabecalho().getNumeroOs(), codigoProjetoDTO, empresa.getNomeFantasia());
//					}
//
//					// --- 3. Busca dos Departamentos (já estava correto) ---
//					List<DepartamentoEntity> departamentosAssociados = new ArrayList<>();
//					if (dto.getDepartamentos() != null) {
//						for (var deptoDto : dto.getDepartamentos()) {
//							findDepartamento(deptoDto.getCodigoDepartamento(), empresa).ifPresentOrElse(
//									departamentosAssociados::add,
//									() -> logger.warn(
//											"OS nº {}: Departamento com código OMIE '{}' não encontrado para a empresa '{}'. Não será associado.",
//											dto.getCabecalho().getNumeroOs(), deptoDto.getCodigoDepartamento(),
//											empresa.getNomeFantasia()));
//						}
//					}
//
//					// --- 4. Criação/Atualização da Entidade (já estava correto) ---
//					OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
//					OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId)
//							.orElseGet(OrdemServicoEntity::new);
//
//					osEntity.atualizarDados(dto, empresa, cliente, categoria, contaCorrente, projeto,
//							departamentosAssociados);
//					loteParaSalvar.add(osEntity);
//				}

				if (!loteParaSalvar.isEmpty()) {
					ordemServicoRepository.saveAll(loteParaSalvar);
				}

				paginaAtual++;
			} while (paginaAtual <= totalPaginas);

			logger.info("Sincronização de Ordens de Serviço concluída para a empresa: {}", empresa.getNomeFantasia());
		}
		logger.info("--- FIM DA ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
	}

	private ClienteEntity findCliente(Long cod, EmpresaEntity emp) {
		return (cod == null) ? null
				: clienteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null);
	}

	private CategoriaEntity findCategoria(String cod, EmpresaEntity emp) {
		return (cod == null) ? null
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

	private void atualizarItensDeServico(OrdemServicoEntity osEntity, List<ServicoPrestadoDTO> dtos,
			Map<Long, ServicoPrestadoEntity> mapaItensExistentes) {
		osEntity.getServicos().clear(); // Limpa a lista atual da entidade
		if (dtos == null)
			return;

		for (ServicoPrestadoDTO dto : dtos) {
			// Busca o item no mapa de itens pré-buscados
			ServicoPrestadoEntity itemEntity = mapaItensExistentes.get(dto.getIdItem());
			if (itemEntity != null) {
				// Se existe, atualiza os dados
				itemEntity.atualizarDados(dto);
				itemEntity.setOrdemServico(osEntity); // Garante a associação correta
			} else {
				// Se não existe, cria uma nova entidade
				itemEntity = new ServicoPrestadoEntity(dto, osEntity);
			}
			osEntity.getServicos().add(itemEntity);
		}
	}

	private void atualizarParcelas(OrdemServicoEntity osEntity, List<ParcelaDTO> dtos) {
		osEntity.getParcelas().clear();
		if (dtos != null) {
			dtos.forEach(dto -> osEntity.getParcelas().add(new OrdemServicoParcelaEntity(dto, osEntity)));
		}
	}

	private Optional<DepartamentoEntity> findDepartamento(String cod, EmpresaEntity emp) {
		return (cod == null) ? Optional.empty()
				: departamentoRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo()));
	}
	
	private void atualizarDepartamentos(OrdemServicoEntity osEntity, List<DepartamentoOsDTO> dtos,
			List<DepartamentoEntity> deptoEntities) {
		osEntity.getDepartamentos().clear();
		if (dtos == null || deptoEntities == null)
			return;

		Map<String, DepartamentoEntity> deptoMap = deptoEntities.stream()
				.collect(Collectors.toMap(de -> de.getId().getCodigo(), Function.identity()));

		dtos.forEach(dto -> {
			DepartamentoEntity deptoEntity = deptoMap.get(dto.getCodigoDepartamento());
			if (deptoEntity != null) {
				osEntity.getDepartamentos().add(new OrdemServicoDepartamentoEntity(dto, osEntity, deptoEntity));
			}
		});
	}

	private void atualizarEmail(OrdemServicoEntity osEntity,
			EmailDTO emailDto) {
		if (emailDto != null) {
			if (osEntity.getEmail() == null) {
				osEntity.setEmail(new OrdemServicoEmailEntity(emailDto, osEntity));
			} else {
				osEntity.getEmail().atualizarDados(emailDto);
			}
		} else {
			osEntity.setEmail(null);
		}
	}
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ExecutionException;
//import java.util.function.BiConsumer;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.VendedorEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.OrdemServicoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.CategoriaRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ClienteRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ContaCorrenteRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.DepartamentoRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ProjetoRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.VendedorRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.relatorio.OrdemServicoRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.CategoriaDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ContaCorrenteDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.DepartamentoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ProjetoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.SharePointItemBaseDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.VendedorDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.AbstractSharePointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.CategoriaSharepointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ClienteSharepointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ContaCorrenteSharepointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.DepartamentoSharepointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.EmpresaSharepointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ProjetoSharepointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.VendedorSharepointService;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;
//
//@Service
//public class SincronizacaoService {
//
//	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoService.class);
//
//	// --- INJEÇÃO DE DEPENDÊNCIAS ---
//	@Autowired
//	private EmpresaSharepointService empresaSharepointService;
//	@Autowired
//	private CategoriaSharepointService categoriaSharepointService;
//	@Autowired
//	private ClienteSharepointService clienteSharepointService;
//	@Autowired
//	private DepartamentoSharepointService departamentoSharepointService;
//	@Autowired
//	private ProjetoSharepointService projetoSharepointService;
//	@Autowired
//	private ContaCorrenteSharepointService contaCorrenteSharepointService;
//	@Autowired
//	private VendedorSharepointService vendedorSharepointService;
//
//	@Autowired
//	private EmpresaRepository empresaRepository;
//	@Autowired
//	private CategoriaRepository categoriaRepository;
//	@Autowired
//	private ClienteRepository clienteRepository;
//	@Autowired
//	private ContaCorrenteRepository contaCorrenteRepository;
//	@Autowired
//	private DepartamentoRepository departamentoRepository;
//	@Autowired
//	private ProjetoRepository projetoRepository;
//	@Autowired
//	private VendedorRepository vendedorRepository;
//
//	// --- INJEÇÃO DE DEPENDÊNCIAS | OMIE ---
//	@Autowired
//	private OmieApiClientService omieApiService;
//	@Autowired
//	private OrdemServicoRepository ordemServicoRepository;
//
//	public void sincronizarTudo() {
//		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO COMPLETA ---");
//		try {
//			sincronizarEmpresas();
//			sincronizarCategorias();
//			sincronizarDepartamento();
//			sincronizarProjetos();
//			sincronizarContaCorrente();
//			sincronizarVendedor();
//			sincronizarClientes();
//			logger.info("--- ROTINA DE SINCRONIZAÇÃO COMPLETA CONCLUÍDA COM SUCESSO ---");
//		} catch (Exception e) {
//			logger.error("!!! ERRO CRÍTICO DURANTE A SINCRONIZAÇÃO !!!", e);
//		}
//	}
//
//	@Transactional
//	public void sincronizarEmpresas() throws ExecutionException, InterruptedException {
//		logger.info("Sincronizando Empresas...");
//		List<EmpresaDTO> dtos = empresaSharepointService.listarTodos().get();
//		if (dtos.isEmpty()) {
//			logger.info("Nenhuma empresa encontrada no SharePoint para sincronizar.");
//			return;
//		}
//
//		// 1. Coletar os códigos das empresas (que são as chaves primárias)
//		Set<Long> codigosEmpresa = dtos.stream().map(EmpresaDTO::getCodigoEmpresa).collect(Collectors.toSet());
//
//		// 2. Buscar as entidades existentes pelos seus códigos
//		Map<Long, EmpresaEntity> entidadesExistentes = empresaRepository.findAllById(codigosEmpresa).stream()
//				.collect(Collectors.toMap(EmpresaEntity::getCodigo, Function.identity()));
//
//		List<EmpresaEntity> entidadesParaSalvar = new ArrayList<>();
//		for (EmpresaDTO dto : dtos) {
//			// 3. Verificar se a entidade existe usando o código da empresa
//			EmpresaEntity entidade = entidadesExistentes.get(dto.getCodigoEmpresa());
//			if (entidade != null) {
//				entidade.atualizarDados(dto);
//			} else {
//				entidade = new EmpresaEntity(dto);
//			}
//			entidadesParaSalvar.add(entidade);
//		}
//
//		empresaRepository.saveAll(entidadesParaSalvar);
//		logger.info("Sincronização de Empresas concluída. {} registros processados.", entidadesParaSalvar.size());
//	}
//	/*
//	 * @Transactional public void sincronizarEmpresas() throws ExecutionException,
//	 * InterruptedException { logger.info("Sincronizando Empresas...");
//	 * List<EmpresaDTO> dtos = empresaSharepointService.listarTodos().get(); if
//	 * (dtos.isEmpty()) {
//	 * logger.info("Nenhuma empresa encontrada no SharePoint para sincronizar.");
//	 * return; }
//	 * 
//	 * // CORREÇÃO 1: Usar o código da empresa (PK) para buscar e mapear. Set<Long>
//	 * codigosEmpresa =
//	 * dtos.stream().map(EmpresaDTO::getCodigoEmpresa).collect(Collectors.toSet());
//	 * Map<Long, EmpresaEntity> entidadesExistentes =
//	 * empresaRepository.findAllById(codigosEmpresa).stream()
//	 * .collect(Collectors.toMap(EmpresaEntity::getCodigo, Function.identity()));
//	 * 
//	 * List<EmpresaEntity> entidadesParaSalvar = new ArrayList<>(); for (EmpresaDTO
//	 * dto : dtos) { // CORREÇÃO 2: Usar o código da empresa para a busca no mapa.
//	 * EmpresaEntity entidade = entidadesExistentes.get(dto.getCodigoEmpresa()); if
//	 * (entidade != null) { entidade.atualizarDados(dto); } else { entidade = new
//	 * EmpresaEntity(dto); } entidadesParaSalvar.add(entidade); }
//	 * 
//	 * empresaRepository.saveAll(entidadesParaSalvar);
//	 * logger.info("Sincronização de Empresas concluída. {} registros processados.",
//	 * entidadesParaSalvar.size()); }
//	 */
//
//	@Transactional
//	public void sincronizarCategorias() throws ExecutionException, InterruptedException {
//		sincronizarEntidadeDependente("Categorias", categoriaSharepointService, categoriaRepository,
//				CategoriaDTO::getNomeFantasiaEmpresa,
//				(dto, empresa) -> new EntidadeCompostaId(dto.getCodigo(), empresa.getCodigo()), CategoriaEntity::new,
//				(entidade, dto) -> entidade.atualizarDados(dto));
//	}
//
//	@Transactional
//	public void sincronizarClientes() throws ExecutionException, InterruptedException {
//		sincronizarEntidadeDependente("Clientes", clienteSharepointService, clienteRepository,
//				ClienteDTO::getNomeFantasiaEmpresa,
//				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
//				ClienteEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
//	}
//
//	@Transactional
//	public void sincronizarDepartamento() throws ExecutionException, InterruptedException {
//		sincronizarEntidadeDependente("Departamentos", departamentoSharepointService, departamentoRepository,
//				DepartamentoDTO::getNomeFantasiaEmpresa,
//				(dto, empresa) -> new EntidadeCompostaId(dto.getCodigo(), empresa.getCodigo()), DepartamentoEntity::new,
//				(entidade, dto) -> entidade.atualizarDados(dto));
//	}
//
//	@Transactional
//	public void sincronizarProjetos() throws ExecutionException, InterruptedException {
//		sincronizarEntidadeDependente("Projetos", projetoSharepointService, projetoRepository,
//				ProjetoDTO::getNomeFantasiaEmpresa,
//				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
//				ProjetoEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
//	}
//
//	@Transactional
//	public void sincronizarContaCorrente() throws ExecutionException, InterruptedException {
//		sincronizarEntidadeDependente("Contas Correntes", contaCorrenteSharepointService, contaCorrenteRepository,
//				ContaCorrenteDTO::getNomeFantasiaEmpresa,
//				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
//				ContaCorrenteEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
//	}
//
//	@Transactional
//	public void sincronizarVendedor() throws ExecutionException, InterruptedException {
//		sincronizarEntidadeDependente("Vendedores", vendedorSharepointService, vendedorRepository,
//				VendedorDTO::getNomeFantasiaEmpresa,
//				(dto, empresa) -> new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo()),
//				VendedorEntity::new, (entidade, dto) -> entidade.atualizarDados(dto));
//	}
//
//	private <T_DTO extends SharePointItemBaseDTO,
//			// CORREÇÃO 3: Usar wildcard (?) para indicar que aceitamos qualquer tipo de
//			// BaseComposedEntity
//			T_ENTITY extends BaseComposedEntity<?>> void sincronizarEntidadeDependente(String entidadeNome,
//					AbstractSharePointService<T_DTO, ?> service, JpaRepository<T_ENTITY, EntidadeCompostaId> repository,
//					Function<T_DTO, String> empresaNomeExtractor,
//					BiFunction<T_DTO, EmpresaEntity, EntidadeCompostaId> idCreator,
//					BiFunction<T_DTO, EmpresaEntity, T_ENTITY> entityCreator, BiConsumer<T_ENTITY, T_DTO> entityUpdater)
//					throws ExecutionException, InterruptedException {
//
//		logger.info("Sincronizando {}...", entidadeNome);
//
//		List<T_DTO> dtos = service.listarTodos().get();
//		if (dtos.isEmpty()) {
//			logger.info("Nenhum(a) {} encontrado(a) no SharePoint para sincronizar.", entidadeNome.toLowerCase());
//			return;
//		}
//
//		Set<String> nomesEmpresa = dtos.stream().map(empresaNomeExtractor).collect(Collectors.toSet());
//		Map<String, EmpresaEntity> mapaDeEmpresas = empresaRepository.findByNomeFantasiaIn(nomesEmpresa).stream()
//				.collect(Collectors.toMap(EmpresaEntity::getNomeFantasia, Function.identity()));
//
//		Set<EntidadeCompostaId> idsParaBuscar = dtos.stream().map(dto -> {
//			EmpresaEntity empresa = mapaDeEmpresas.get(empresaNomeExtractor.apply(dto));
//			return empresa != null ? idCreator.apply(dto, empresa) : null;
//		}).filter(id -> id != null).collect(Collectors.toSet());
//
//		/*
//		 * Map<EntidadeCompostaId, T_ENTITY> entidadesExistentes =
//		 * repository.findAllById(idsParaBuscar).stream()
//		 * .collect(Collectors.toMap(T_ENTITY::getId, Function.identity()));
//		 */
//
//		// Converte o Set para uma List para poder dividir em lotes
//		List<EntidadeCompostaId> idsList = new ArrayList<>(idsParaBuscar);
//		int batchSize = 500; // Um tamanho de lote razoável para a maioria dos bancos de dados
//		Map<EntidadeCompostaId, T_ENTITY> entidadesExistentes = new java.util.HashMap<>();
//
//		// Itera sobre a lista de IDs em lotes de 'batchSize'
//		for (int i = 0; i < idsList.size(); i += batchSize) {
//			int end = Math.min(i + batchSize, idsList.size());
//			List<EntidadeCompostaId> batchIds = idsList.subList(i, end);
//
//			// Busca apenas um lote de cada vez e adiciona ao mapa de resultados
//			repository.findAllById(batchIds).forEach(entity -> entidadesExistentes.put(entity.getId(), entity));
//		}
//
//		/*
//		 * List<T_ENTITY> entidadesParaSalvar = new ArrayList<>(); for (T_DTO dto :
//		 * dtos) { EmpresaEntity empresaAssociada =
//		 * mapaDeEmpresas.get(empresaNomeExtractor.apply(dto)); if (empresaAssociada ==
//		 * null) { logger.
//		 * warn("{} com SharePoint ID '{}' ignorado(a): empresa '{}' não encontrada.",
//		 * entidadeNome, dto.getId(), empresaNomeExtractor.apply(dto)); continue; }
//		 * 
//		 * EntidadeCompostaId idAtual = idCreator.apply(dto, empresaAssociada); T_ENTITY
//		 * entidade = entidadesExistentes.get(idAtual);
//		 * 
//		 * if (entidade != null) { // Garante que a referência da empresa esteja
//		 * atualizada entidade.setEmpresa(empresaAssociada);
//		 * entityUpdater.accept(entidade, dto); } else { entidade =
//		 * entityCreator.apply(dto, empresaAssociada); }
//		 * entidadesParaSalvar.add(entidade); }
//		 * 
//		 * repository.saveAll(entidadesParaSalvar);
//		 * logger.info("Sincronização de {} concluída. {} registros processados.",
//		 * entidadeNome, entidadesParaSalvar.size());
//		 */
//
//		// Bloco novo com salvamento em lotes
//		List<T_ENTITY> loteParaSalvar = new ArrayList<>();
//		final int batchSizeSave = 500; // O mesmo tamanho do lote de leitura ou um valor otimizado para escrita.
//		int totalProcessado = 0;
//
//		for (T_DTO dto : dtos) {
//			EmpresaEntity empresaAssociada = mapaDeEmpresas.get(empresaNomeExtractor.apply(dto));
//			if (empresaAssociada == null) {
//				logger.warn("{} com SharePoint ID '{}' ignorado(a): empresa '{}' não encontrada.", entidadeNome,
//						dto.getId(), empresaNomeExtractor.apply(dto));
//				continue;
//			}
//
//			EntidadeCompostaId idAtual = idCreator.apply(dto, empresaAssociada);
//			T_ENTITY entidade = entidadesExistentes.get(idAtual);
//
//			if (entidade != null) {
//				entidade.setEmpresa(empresaAssociada);
//				entityUpdater.accept(entidade, dto);
//			} else {
//				entidade = entityCreator.apply(dto, empresaAssociada);
//			}
//			loteParaSalvar.add(entidade);
//
//			// Verifica se o lote atingiu o tamanho máximo
//			if (loteParaSalvar.size() == batchSizeSave) {
//				repository.saveAll(loteParaSalvar);
//				totalProcessado += loteParaSalvar.size();
//				loteParaSalvar.clear(); // Limpa a lista para o próximo lote
//				logger.info("... {} {} processados...", totalProcessado, entidadeNome);
//			}
//		}
//
//		// Salva o lote final (caso o número total de itens não seja múltiplo do
//		// batchSizeSave)
//		if (!loteParaSalvar.isEmpty()) {
//			repository.saveAll(loteParaSalvar);
//			totalProcessado += loteParaSalvar.size();
//		}
//
//		logger.info("Sincronização de {} concluída. {} registros processados no total.", entidadeNome, totalProcessado);
//	}
//
//    @Transactional
//    public void sincronizarOrdensDeServico() throws ExecutionException, InterruptedException {
//        logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//        List<EmpresaEntity> empresas = empresaRepository.findAll();
//
//        for (EmpresaEntity empresa : empresas) {
//            logger.info("Processando Ordens de Serviço para a empresa: {}", empresa.getNomeFantasia());
//            
//            int paginaAtual = 1;
//            int totalPaginas;
//            
//            do {
//                if (paginaAtual > 1) {
//                    try {
//                        Thread.sleep(1000L);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        break;
//                    }
//                }
//
//                OmieListarOsResponse resposta = omieApiService.listarOsPorPagina(empresa, paginaAtual).get();
//                totalPaginas = resposta.getTotalDePaginas();
//
//                if (resposta.getOrdensDeServico() == null || resposta.getOrdensDeServico().isEmpty()) {
//                    logger.info("Nenhuma OS encontrada na página {} para a empresa {}. Fim da paginação.", paginaAtual, empresa.getNomeFantasia());
//                    break; 
//                }
//                
//                logger.info("Processando página {} de {} para a empresa '{}'. {} registros encontrados.", paginaAtual, totalPaginas, empresa.getNomeFantasia(), resposta.getOrdensDeServico().size());
//                List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();
//
//                for (OrdemServicoDTO dto : resposta.getOrdensDeServico()) {
//                    // 1. Buscar todas as dependências primeiro
//                    ClienteEntity cliente = findCliente(dto.getCabecalho().getCodigoCliente(), empresa);
//                    if (cliente == null) {
//                        logger.warn("Cliente com código OMIE {} não encontrado. Pulando OS nº {}.", dto.getCabecalho().getCodigoCliente(), dto.getCabecalho().getNumeroOs());
//                        continue;
//                    }
//
//                    CategoriaEntity categoria = findCategoria(dto.getInformacoesAdicionais().getCodigoCategoria(), empresa);
//                    ContaCorrenteEntity contaCorrente = findContaCorrente(dto.getInformacoesAdicionais().getCodigoContaCorrente(), empresa);
//                    ProjetoEntity projeto = findProjeto(dto.getInformacoesAdicionais().getCodigoProjeto(), empresa);
//                    
//                    // 2. Busca a lista de Departamentos para a OS
//                    List<DepartamentoEntity> departamentosAssociados = new ArrayList<>();
//                    if(dto.getDepartamentos() != null){
//                        for(var deptoDto : dto.getDepartamentos()){
//                            findDepartamento(deptoDto.getCodigoDepartamento(), empresa).ifPresent(departamentosAssociados::add);
//                        }
//                    }
//
//                    // 3. Criar ou Atualizar a Entidade
//                    OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
//                    OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId).orElseGet(() -> new OrdemServicoEntity());
//
//                    // 4. Passar os dados e as dependências já resolvidas para a entidade
//                    osEntity.atualizarDados(dto, empresa, cliente, categoria, contaCorrente, projeto, departamentosAssociados);
//                    loteParaSalvar.add(osEntity);
//                }
//
//                if (!loteParaSalvar.isEmpty()) {
//                    ordemServicoRepository.saveAll(loteParaSalvar);
//                }
//                
//                paginaAtual++;
//
//            } while (paginaAtual <= totalPaginas);
//
//            logger.info("Sincronização de Ordens de Serviço concluída para a empresa: {}", empresa.getNomeFantasia());
//        }
//        logger.info("--- FIM DA ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//    }
//    
//    // Métodos auxiliares para buscar dependências
//    private ClienteEntity findCliente(Long cod, EmpresaEntity emp) { return (cod == null) ? null : clienteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null); }
//    private CategoriaEntity findCategoria(String cod, EmpresaEntity emp) { return (cod == null) ? null : categoriaRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo())).orElse(null); }
//    private ContaCorrenteEntity findContaCorrente(Long cod, EmpresaEntity emp) { return (cod == null) ? null : contaCorrenteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null); }
//    private ProjetoEntity findProjeto(Long cod, EmpresaEntity emp) { return (cod == null) ? null : projetoRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null); }
//    private java.util.Optional<DepartamentoEntity> findDepartamento(String cod, EmpresaEntity emp) { return (cod == null) ? java.util.Optional.empty() : departamentoRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo())); }
////	// Dentro da classe SincronizacaoService
////
////	@Transactional
////	public void sincronizarOrdensDeServico() throws ExecutionException, InterruptedException {
////		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
////		List<EmpresaEntity> empresas = empresaRepository.findAll();
////
////		for (EmpresaEntity empresa : empresas) {
////			logger.info("Processando Ordens de Serviço para a empresa: {}", empresa.getNomeFantasia());
////
////			int paginaAtual = 1;
////			int totalPaginas;
////			List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();
////
////			do {
////				// Delay antes de fazer a próxima chamada para respeitar o Rate Limit da API
////				// Não aplica o delay na primeira página
////				if (paginaAtual > 1) {
////					try {
////						long delayMs = 1000L; // 1 segundo de delay
////						logger.info("Aguardando {}ms antes de buscar a próxima página...", delayMs);
////						Thread.sleep(delayMs);
////					} catch (InterruptedException e) {
////						logger.error("A thread foi interrompida durante o delay de rate limiting.", e);
////						Thread.currentThread().interrupt(); // Restaura o status de interrupção
////						break; // Sai do loop se a thread for interrompida
////					}
////				}
////
////				OmieListarOsResponse resposta = omieApiService.listarOsPorPagina(empresa, paginaAtual).get();
////				totalPaginas = resposta.getTotalDePaginas();
////
////				if (resposta.getOrdensDeServico() == null || resposta.getOrdensDeServico().isEmpty()) {
////					logger.info("Nenhuma OS encontrada na página {} para a empresa {}. Fim da paginação.", paginaAtual,
////							empresa.getNomeFantasia());
////					break;
////				}
////
////				logger.info("Processando página {} de {} para a empresa '{}'. {} registros encontrados.", paginaAtual,
////						totalPaginas, empresa.getNomeFantasia(), resposta.getOrdensDeServico().size());
////
////				for (OrdemServicoDTO dto : resposta.getOrdensDeServico()) {
////					// Lógica de busca de dependências e criação/atualização da entidade...
////					ClienteEntity cliente = findCliente(dto.getCabecalho().getCodigoCliente(), empresa);
////					CategoriaEntity categoria = findCategoria(dto.getInformacoesAdicionais().getCodigoCategoria(),
////							empresa);
////					ContaCorrenteEntity contaCorrente = findContaCorrente(
////							dto.getInformacoesAdicionais().getCodigoContaCorrente(), empresa);
////					ProjetoEntity projeto = findProjeto(dto.getInformacoesAdicionais().getCodigoProjeto(), empresa);
////
////					if (cliente == null) {
////						logger.warn(
////								"Cliente com código OMIE {} não encontrado no banco de dados para a empresa {}. Pulando OS nº {}.",
////								dto.getCabecalho().getCodigoCliente(), empresa.getNomeFantasia(),
////								dto.getCabecalho().getNumeroOs());
////						continue;
////					}
////
////					OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
////					OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId)
////							.orElse(new OrdemServicoEntity(dto, empresa, cliente, categoria, contaCorrente, projeto));
////
////					osEntity.atualizarDados(dto);
////
////					osEntity.setCliente(cliente);
////					osEntity.setCategoria(categoria);
////					osEntity.setContaCorrente(contaCorrente);
////					osEntity.setProjeto(projeto);
////
////					loteParaSalvar.add(osEntity);
////				}
////
////				if (!loteParaSalvar.isEmpty()) {
////					ordemServicoRepository.saveAll(loteParaSalvar);
////					loteParaSalvar.clear();
////				}
////
////				paginaAtual++;
////
////			} while (paginaAtual <= totalPaginas);
////
////			logger.info("Sincronização de Ordens de Serviço concluída para a empresa: {}", empresa.getNomeFantasia());
////		}
////		logger.info("--- FIM DA ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
////	}
////
////	// O resto dos seus métodos auxiliares (findCliente, findCategoria, etc.)
////	// permanecem os mesmos
//////	@Transactional
//////	public void sincronizarOrdensDeServico() throws ExecutionException, InterruptedException {
//////		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//////		List<EmpresaEntity> empresas = empresaRepository.findAll();
//////
//////		for (EmpresaEntity empresa : empresas) {
//////			logger.info("Processando Ordens de Serviço para a empresa: {}", empresa.getNomeFantasia());
//////
//////			int paginaAtual = 1;
//////			int totalPaginas;
//////			List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();
//////
//////			do {
//////				OmieListarOsResponse resposta = omieApiService.listarOsPorPagina(empresa, paginaAtual).get();
//////				totalPaginas = resposta.getTotalDePaginas();
//////
//////				if (resposta.getOrdensDeServico() == null || resposta.getOrdensDeServico().isEmpty()) {
//////					logger.info("Nenhuma OS encontrada na página {} para a empresa {}.", paginaAtual,
//////							empresa.getNomeFantasia());
//////					break;
//////				}
//////
//////				for (OrdemServicoDTO dto : resposta.getOrdensDeServico()) {
//////					// 1. Buscar dependências (Cliente, Categoria, etc.)
//////					// PONTO DE ATENÇÃO: Assumindo que os códigos do OMIE correspondem aos códigos
//////					// do SharePoint
//////					ClienteEntity cliente = findCliente(dto.getCabecalho().getCodigoCliente(), empresa);
//////					CategoriaEntity categoria = findCategoria(dto.getInformacoesAdicionais().getCodigoCategoria(),
//////							empresa);
//////					ContaCorrenteEntity contaCorrente = findContaCorrente(
//////							dto.getInformacoesAdicionais().getCodigoContaCorrente(), empresa);
//////					ProjetoEntity projeto = findProjeto(dto.getInformacoesAdicionais().getCodigoProjeto(), empresa);
//////
//////					if (cliente == null) {
//////						logger.warn(
//////								"Cliente com código OMIE {} não encontrado no banco de dados para a empresa {}. Pulando OS nº {}.",
//////								dto.getCabecalho().getCodigoCliente(), empresa.getNomeFantasia(),
//////								dto.getCabecalho().getNumeroOs());
//////						continue; // Pula para a próxima OS
//////					}
//////
//////					// 2. Criar ou Atualizar a OrdemServicoEntity
//////					OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
//////					OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId)
//////							.orElse(new OrdemServicoEntity(dto, empresa, cliente, categoria, contaCorrente, projeto));
//////
//////					osEntity.atualizarDados(dto);
//////
//////					// Adiciona as dependências que podem ter sido nulas na criação inicial
//////					osEntity.setCliente(cliente);
//////					osEntity.setCategoria(categoria);
//////					osEntity.setContaCorrente(contaCorrente);
//////					osEntity.setProjeto(projeto);
//////
//////					loteParaSalvar.add(osEntity);
//////				}
//////
//////				if (!loteParaSalvar.isEmpty()) {
//////					logger.info("Salvando lote de {} Ordens de Serviço da página {}...", loteParaSalvar.size(),
//////							paginaAtual);
//////					ordemServicoRepository.saveAll(loteParaSalvar);
//////					loteParaSalvar.clear();
//////				}
//////
//////				paginaAtual++;
//////
//////			} while (paginaAtual <= totalPaginas);
//////
//////			logger.info("Sincronização de Ordens de Serviço concluída para a empresa: {}", empresa.getNomeFantasia());
//////		}
//////		logger.info("--- FIM DA ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//////	}
////
////	// Métodos auxiliares para buscar as dependências com tratamento para valores
////	// nulos
////	private ClienteEntity findCliente(Long clienteCodigo, EmpresaEntity empresa) {
////		if (clienteCodigo == null)
////			return null;
////		return clienteRepository.findById(new EntidadeCompostaId(String.valueOf(clienteCodigo), empresa.getCodigo()))
////				.orElse(null);
////	}
////
////	private CategoriaEntity findCategoria(String categoriaCodigo, EmpresaEntity empresa) {
////		if (categoriaCodigo == null)
////			return null;
////		return categoriaRepository.findById(new EntidadeCompostaId(categoriaCodigo, empresa.getCodigo())).orElse(null);
////	}
////
////	private ContaCorrenteEntity findContaCorrente(Long ccCodigo, EmpresaEntity empresa) {
////		if (ccCodigo == null)
////			return null;
////		return contaCorrenteRepository.findById(new EntidadeCompostaId(String.valueOf(ccCodigo), empresa.getCodigo()))
////				.orElse(null);
////	}
////
////	private ProjetoEntity findProjeto(Long projetoCodigo, EmpresaEntity empresa) {
////		if (projetoCodigo == null)
////			return null;
////		return projetoRepository.findById(new EntidadeCompostaId(String.valueOf(projetoCodigo), empresa.getCodigo()))
////				.orElse(null);
////	}
////
////	private DepartamentoEntity findDepartamento(String deptoCodigo, EmpresaEntity empresa) {
////		if (deptoCodigo == null)
////			return null;
////		return departamentoRepository.findById(new EntidadeCompostaId(deptoCodigo, empresa.getCodigo())).orElse(null);
////	}
////	private void atualizarDepartamentos(OrdemServicoEntity osEntity, List<com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO> dtos) {
////	    osEntity.getDepartamentos().clear();
////	    if (dtos != null) {
////	        dtos.forEach(dto -> {
////	            // 1. Busca a DepartamentoEntity correspondente
////	            DepartamentoEntity deptoEntity = findDepartamento(dto.getCodigoDepartamento(), osEntity.getEmpresa());
////
////	            if (deptoEntity != null) {
////	                // 2. Cria a entidade de ligação passando a OS_Entity e a DepartamentoEntity encontrada
////	                osEntity.getDepartamentos().add(new OrdemServicoDepartamentoEntity(dto, osEntity, deptoEntity));
////	            } else {
////	                logger.warn("Departamento com código OMIE {} não encontrado no banco de dados para a empresa {}. Não será associado à OS nº {}.",
////	                        dto.getCodigoDepartamento(), osEntity.getEmpresa().getNomeFantasia(), osEntity.getNumeroOs());
////	            }
////	        });
////	    }
////	}
//}
//
////---------------------------------------------------------------------------------
//
////package com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Map;
////import java.util.Set;
////import java.util.concurrent.ExecutionException;
////import java.util.function.BiConsumer;
////import java.util.function.BiFunction;
////import java.util.function.Function;
////import java.util.stream.Collectors;
////
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.jpa.repository.JpaRepository;
////import org.springframework.stereotype.Service;
////import org.springframework.transaction.annotation.Transactional;
////
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.VendedorEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.CategoriaRepository;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ClienteRepository;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ContaCorrenteRepository;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.DepartamentoRepository;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ProjetoRepository;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.VendedorRepository;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.CategoriaDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ContaCorrenteDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.DepartamentoDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ProjetoDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.SharePointItemBaseDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.VendedorDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.AbstractSharePointService;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.CategoriaSharepointService;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ClienteSharepointService;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ContaCorrenteSharepointService;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.DepartamentoSharepointService;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.EmpresaSharepointService;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ProjetoSharepointService;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.VendedorSharepointService;
////
////@Service
////public class SincronizacaoService {
////
////	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoService.class);
////
////	// --- INJEÇÃO DE DEPENDÊNCIAS ---
////	// Injetamos todos os serviços e repositórios que vamos orquestrar.
////
////	// Serviços do SharePoint (Camada de Infraestrutura)
////	@Autowired
////	private EmpresaSharepointService empresaSharepointService;
////	@Autowired
////	private CategoriaSharepointService categoriaSharepointService;
////	@Autowired
////	private ClienteSharepointService clienteSharepointService;
////	@Autowired
////	private DepartamentoSharepointService departamentoSharepointService;
////	@Autowired
////	private ProjetoSharepointService projetoSharepointService;
////	@Autowired
////	private ContaCorrenteSharepointService contaCorrenteSharepointService;
////	@Autowired
////	private VendedorSharepointService vendedorSharepointService;
////
////	// Repositórios do Banco de Dados (Camada de Domínio)
////	@Autowired
////	private EmpresaRepository empresaRepository;
////	@Autowired
////	private CategoriaRepository categoriaRepository;
////	@Autowired
////	private ClienteRepository clienteRepository;
////	@Autowired
////	private ContaCorrenteRepository contaCorrenteRepository;
////	@Autowired
////	private DepartamentoRepository departamentoRepository;
////	@Autowired
////	private ProjetoRepository projetoRepository;
////	@Autowired
////	private VendedorRepository vendedorRepository;
////
////	/**
////	 * Método principal que orquestra a sincronização de todas as entidades na ordem
////	 * correta de dependência.
////	 */
////	public void sincronizarTudo() {
////		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO COMPLETA ---");
////		try {
////			// 1. Sincroniza entidades que não têm dependências
////			sincronizarEmpresas();
////
////			// 2. Sincroniza entidades que dependem de Empresa
////			sincronizarCategorias();
////
////			sincronizarClientes();
////
////			sincronizarDepartamento();
////
////			sincronizarProjetos();
////
////			sincronizarContaCorrente();
////
////			sincronizarVendedor();
////
////			logger.info("--- ROTINA DE SINCRONIZAÇÃO COMPLETA CONCLUÍDA COM SUCESSO ---");
////		} catch (Exception e) {
////			logger.error("!!! ERRO CRÍTICO DURANTE A SINCRONIZAÇÃO !!!", e);
////		}
////	}
////
////	// --- MÉTODOS DE SINCRONIZAÇÃO ESPECÍFICOS ---
////
////	@Transactional // Garante que a operação inteira seja atômica (ou tudo funciona, ou nada é
////					// salvo)
////	public void sincronizarEmpresas() throws ExecutionException, InterruptedException {
////		logger.info("Sincronizando Empresas...");
////
////		// 1. BUSCA: Pega os DTOs do SharePoint
////		List<EmpresaDTO> dtos = empresaSharepointService.listarTodos().get();
////		if (dtos.isEmpty()) {
////			logger.info("Nenhuma empresa encontrada no SharePoint para sincronizar.");
////			return;
////		}
////
////		// 2. MAPEIA: Busca todas as entidades existentes no banco DE UMA SÓ VEZ
////		Set<String> sharepointIds = dtos.stream().map(EmpresaDTO::getId).collect(Collectors.toSet());
////		Map<Long, EmpresaEntity> entidadesExistentes = empresaRepository.findAllById(sharepointIds).stream()
////				.collect(Collectors.toMap(EmpresaEntity::getCodigo, Function.identity()));
////
////		List<EmpresaEntity> entidadesParaSalvar = new ArrayList<>();
////		for (EmpresaDTO dto : dtos) {
////			// 3. DECIDE: Verifica se a entidade já existe no mapa
////			EmpresaEntity entidade = entidadesExistentes.get(dto.getId());
////			if (entidade != null) {
////				// Se existe, ATUALIZA
////				entidade.atualizarDados(dto);
////			} else {
////				// Se não existe, CRIA
////				entidade = new EmpresaEntity(dto);
////			}
////			entidadesParaSalvar.add(entidade);
////		}
////
////		// 4. PERSISTE: Salva todas as entidades (novas e atualizadas) em uma única
////		// operação em lote
////		empresaRepository.saveAll(entidadesParaSalvar);
////		logger.info("Sincronização de Empresas concluída. {} registros processados.", entidadesParaSalvar.size());
////	}
////	// --- MÉTODOS PÚBLICOS SIMPLIFICADOS ---
////
////	@Transactional
////	public void sincronizarCategorias() throws ExecutionException, InterruptedException {
////		sincronizarEntidadeDependente("Categorias", // 1. Nome para log
////				categoriaSharepointService, // 2. Serviço do SharePoint
////				categoriaRepository, // 3. Repositório do BD
////				CategoriaDTO::getNomeFantasiaEmpresa, // 4. Como obter o nome da empresa do DTO
////				CategoriaEntity::new, // 5. Como criar uma nova entidade
////				(entidade, dto) -> entidade.atualizarDados(dto) // 6. Como atualizar uma entidade existente
////		);
////	}
////
////	@Transactional
////	public void sincronizarClientes() throws ExecutionException, InterruptedException {
////		sincronizarEntidadeDependente("Clientes", clienteSharepointService, clienteRepository,
////				ClienteDTO::getNomeFantasiaEmpresa, ClienteEntity::new,
////				(entidade, dto) -> entidade.atualizarDados(dto));
////	}
////
////	@Transactional
////	public void sincronizarDepartamento() throws ExecutionException, InterruptedException {
////		sincronizarEntidadeDependente("Departamentos", departamentoSharepointService, departamentoRepository,
////				DepartamentoDTO::getNomeFantasiaEmpresa, DepartamentoEntity::new,
////				(entidade, dto) -> entidade.atualizarDados(dto));
////	}
////
////	@Transactional
////	public void sincronizarProjetos() throws ExecutionException, InterruptedException {
////		sincronizarEntidadeDependente("Projetos", projetoSharepointService, projetoRepository,
////				ProjetoDTO::getNomeFantasiaEmpresa, ProjetoEntity::new,
////				(entidade, dto) -> entidade.atualizarDados(dto));
////	}
////
////	@Transactional
////	public void sincronizarContaCorrente() throws ExecutionException, InterruptedException {
////		sincronizarEntidadeDependente("Contas Correntes", contaCorrenteSharepointService, contaCorrenteRepository,
////				ContaCorrenteDTO::getNomeFantasiaEmpresa, ContaCorrenteEntity::new,
////				(entidade, dto) -> entidade.atualizarDados(dto));
////	}
////
////	@Transactional
////	public void sincronizarVendedor() throws ExecutionException, InterruptedException {
////		sincronizarEntidadeDependente("Vendedores", vendedorSharepointService, vendedorRepository,
////				VendedorDTO::getNomeFantasiaEmpresa, VendedorEntity::new,
////				(entidade, dto) -> entidade.atualizarDados(dto));
////	}
////
////	// --- O MÉTODO MÁGICO E GENÉRICO ---
////
////	private <T_DTO extends SharePointItemBaseDTO, T_ENTITY extends BaseComposedEntity<?>> void sincronizarEntidadeDependente(
////			String entidadeNome, AbstractSharePointService<T_DTO, ?> service,
////			JpaRepository<T_ENTITY, String> repository, Function<T_DTO, String> empresaNomeExtractor,
////			BiFunction<T_DTO, EmpresaEntity, T_ENTITY> entityCreator, BiConsumer<T_ENTITY, T_DTO> entityUpdater)
////			throws ExecutionException, InterruptedException {
////
////		logger.info("Sincronizando {}...", entidadeNome);
////
////		// 1. BUSCA DTOs
////		List<T_DTO> dtos = service.listarTodos().get();
////		if (dtos.isEmpty()) {
////			logger.info("Nenhum(a) {} encontrado(a) no SharePoint para sincronizar.", entidadeNome.toLowerCase());
////			return;
////		}
////
////		// 2. RESOLVE DEPENDÊNCIA (Empresa)
////		Set<String> nomesEmpresa = dtos.stream().map(empresaNomeExtractor).collect(Collectors.toSet());
////		Map<String, EmpresaEntity> mapaDeEmpresas = empresaRepository.findByNomeFantasiaIn(nomesEmpresa).stream()
////				.collect(Collectors.toMap(EmpresaEntity::getNomeFantasia, Function.identity()));
////
////		// 3. MAPEIA Entidades Existentes
////		Set<String> sharepointIds = dtos.stream().map(SharePointItemBaseDTO::getId).collect(Collectors.toSet());
////		Map<Long, T_ENTITY> entidadesExistentes = repository.findAllById(sharepointIds).stream()
////				.collect(Collectors.toMap(T_ENTITY::getCodigo, Function.identity()));
////
////		List<T_ENTITY> entidadesParaSalvar = new ArrayList<>();
////		for (T_DTO dto : dtos) {
////			EmpresaEntity empresaAssociada = mapaDeEmpresas.get(empresaNomeExtractor.apply(dto));
////			if (empresaAssociada == null) {
////				logger.warn("{} com ID SharePoint '{}' ignorado(a): empresa '{}' não encontrada.", entidadeNome,
////						dto.getId(), empresaNomeExtractor.apply(dto));
////				continue;
////			}
////
////			// 4. DECIDE: Atualiza ou Cria
////			T_ENTITY entidade = entidadesExistentes.get(dto.getId());
////			if (entidade != null) {
////				entidade.setEmpresa(empresaAssociada);
////				entityUpdater.accept(entidade, dto); // Usa a função de atualização
////			} else {
////				entidade = entityCreator.apply(dto, empresaAssociada); // Usa a função de criação
////			}
////			entidadesParaSalvar.add(entidade);
////		}
////
////		// 5. PERSISTE
////		repository.saveAll(entidadesParaSalvar);
////		logger.info("Sincronização de {} concluída. {} registros processados.", entidadeNome,
////				entidadesParaSalvar.size());
////	}
////	/*
////	 * @Transactional public void sincronizarCategorias() throws ExecutionException,
////	 * InterruptedException { logger.info("Sincronizando Categorias...");
////	 *
////	 * // 1. BUSCA DTOs List<CategoriaDTO> dtos =
////	 * categoriaService.listarTodos().get(); if (dtos.isEmpty()) {
////	 * logger.info("Nenhuma categoria encontrada no SharePoint para sincronizar.");
////	 * return; }
////	 *
////	 * // 2. RESOLVE DEPENDÊNCIA (Empresa) Set<String> nomesEmpresa =
////	 * dtos.stream().map(CategoriaDTO::getNomeFantasiaEmpresa).collect(Collectors.
////	 * toSet());
////	 *
////	 * // CORRIJA O NOME DO MÉTODO AQUI Map<String, EmpresaEntity> mapaDeEmpresas =
////	 * empresaRepository.findByNomeFantasiaIn(nomesEmpresa).stream()
////	 * .collect(Collectors.toMap(EmpresaEntity::getNomeFantasia,
////	 * Function.identity()));
////	 *
////	 * // 3. MAPEIA Entidades Existentes Set<String> sharepointIds =
////	 * dtos.stream().map(CategoriaDTO::getId).collect(Collectors.toSet());
////	 * Map<String, CategoriaEntity> entidadesExistentes =
////	 * categoriaRepository.findAllById(sharepointIds).stream()
////	 * .collect(Collectors.toMap(CategoriaEntity::getId, Function.identity()));
////	 *
////	 * List<CategoriaEntity> entidadesParaSalvar = new ArrayList<>(); for
////	 * (CategoriaDTO dto : dtos) { EmpresaEntity empresaAssociada =
////	 * mapaDeEmpresas.get(dto.getNomeFantasiaEmpresa()); if (empresaAssociada ==
////	 * null) { logger.
////	 * warn("Categoria com ID SharePoint '{}' ignorada: empresa '{}' não encontrada."
////	 * , dto.getId(), dto.getNomeFantasiaEmpresa()); continue; }
////	 *
////	 * // 4. DECIDE: Atualiza ou Cria CategoriaEntity entidade =
////	 * entidadesExistentes.get(dto.getId()); if (entidade != null) {
////	 * entidade.setEmpresa(empresaAssociada); // Garante que a referência está
////	 * correta entidade.atualizarDados(dto); } else { entidade = new
////	 * CategoriaEntity(dto, empresaAssociada); } entidadesParaSalvar.add(entidade);
////	 * }
////	 *
////	 * // 5. PERSISTE categoriaRepository.saveAll(entidadesParaSalvar); logger.
////	 * info("Sincronização de Categorias concluída. {} registros processados.",
////	 * entidadesParaSalvar.size()); }
////	 *
////	 * @Transactional public void sincronizarClientes() throws ExecutionException,
////	 * InterruptedException { logger.info("Sincronizando Clientes...");
////	 *
////	 * // 1. BUSCA DTOs List<ClienteDTO> dtos = clienteService.listarTodos().get();
////	 * if (dtos.isEmpty()) {
////	 * logger.info("Nenhuma cliente encontrada no SharePoint para sincronizar.");
////	 * return; }
////	 *
////	 * // 2. RESOLVE DEPENDÊNCIA (Empresa) Set<String> nomesEmpresa =
////	 * dtos.stream().map(ClienteDTO::getNomeFantasiaEmpresa).collect(Collectors.
////	 * toSet());
////	 *
////	 * // CORRIJA O NOME DO MÉTODO AQUI Map<String, EmpresaEntity> mapaDeEmpresas =
////	 * empresaRepository.findByNomeFantasiaIn(nomesEmpresa).stream()
////	 * .collect(Collectors.toMap(EmpresaEntity::getNomeFantasia,
////	 * Function.identity()));
////	 *
////	 * // 3. MAPEIA Entidades Existentes Set<String> sharepointIds =
////	 * dtos.stream().map(ClienteDTO::getId).collect(Collectors.toSet()); Map<String,
////	 * ClienteEntity> entidadesExistentes =
////	 * clienteRepository.findAllById(sharepointIds).stream()
////	 * .collect(Collectors.toMap(ClienteEntity::getId, Function.identity()));
////	 *
////	 * List<ClienteEntity> entidadesParaSalvar = new ArrayList<>(); for (ClienteDTO
////	 * dto : dtos) { EmpresaEntity empresaAssociada =
////	 * mapaDeEmpresas.get(dto.getNomeFantasiaEmpresa()); if (empresaAssociada ==
////	 * null) { logger.
////	 * warn("Categoria com ID SharePoint '{}' ignorada: empresa '{}' não encontrada."
////	 * , dto.getId(), dto.getNomeFantasiaEmpresa()); continue; }
////	 *
////	 * // 4. DECIDE: Atualiza ou Cria ClienteEntity entidade =
////	 * entidadesExistentes.get(dto.getId()); if (entidade != null) {
////	 * entidade.setEmpresa(empresaAssociada); // Garante que a referência está
////	 * correta entidade.atualizarDados(dto); } else { entidade = new
////	 * ClienteEntity(dto, empresaAssociada); } entidadesParaSalvar.add(entidade); }
////	 *
////	 * // 5. PERSISTE clienteRepository.saveAll(entidadesParaSalvar);
////	 * logger.info("Sincronização de Clientes concluída. {} registros processados.",
////	 * entidadesParaSalvar.size()); }
////	 */
////}
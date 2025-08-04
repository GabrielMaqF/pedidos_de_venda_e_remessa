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
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.OrdemServicoEntity;
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
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
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

					// 3. Crie ou atualize a OrdemServicoEntity
					OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
					OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId)
							.orElseGet(OrdemServicoEntity::new);

					// 4. Atualize os dados da OS (que agora inclui parcelas, email e departamentos)
					osEntity.atualizarDados(dto, empresa, cliente, categoria, contaCorrente, projeto,
							departamentosAssociados);

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

	private Optional<DepartamentoEntity> findDepartamento(String cod, EmpresaEntity emp) {
		return (cod == null) ? Optional.empty()
				: departamentoRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo()));
	}
}
//package com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
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
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.ServicoPrestadoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.CategoriaRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ClienteRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ContaCorrenteRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.DepartamentoRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ProjetoRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.VendedorRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.relatorio.OrdemServicoRepository;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.relatorio.ServicoPrestadoRepository;
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
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;
//
//@Service
//public class SincronizacaoService {
//
//	private static final Logger logger = LoggerFactory.getLogger(SincronizacaoService.class);
//
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
//	@Autowired
//	private OrdemServicoRepository ordemServicoRepository;
//	@Autowired
//	private ServicoPrestadoRepository servicoPrestadoRepository;
//	@Autowired
//	private OmieApiClientService omieApiService;
//
//	// ---------------------------- SHAREPOINT ------------------------------
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
//	// ---------------------------- OMIE ------------------------------
//	@Transactional
//	public void sincronizarOrdensDeServico() throws ExecutionException, InterruptedException {
//		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//		List<EmpresaEntity> empresas = empresaRepository.findAll();
//
//		for (EmpresaEntity empresa : empresas) {
//			logger.info("Processando Ordens de Serviço para a empresa: {}", empresa.getNomeFantasia());
//			int paginaAtual = 1;
//			int totalPaginas;
//
//			do {
//				if (paginaAtual > 1) {
//					try {
//						Thread.sleep(1000L);
//					} catch (InterruptedException e) {
//						Thread.currentThread().interrupt();
//						break;
//					}
//				}
//
//				OmieListarOsResponse resposta = omieApiService.listarOsPorPagina(empresa, paginaAtual).get();
//				totalPaginas = resposta.getTotalDePaginas();
//
//				if (resposta.getOrdensDeServico() == null || resposta.getOrdensDeServico().isEmpty()) {
//					logger.info("Nenhuma OS encontrada na página {} para a empresa {}. Fim da paginação.", paginaAtual,
//							empresa.getNomeFantasia());
//					break;
//				}
//
//				List<OrdemServicoDTO> osDtosDaPagina = resposta.getOrdensDeServico();
//
//				Set<Long> todosOsIdItensDaPagina = osDtosDaPagina.stream()
////						.filter(os -> os.getServicosPrestados() != null)
//						.flatMap(os -> os.getServicosPrestados().stream()).map(ServicoPrestadoDTO::getIdItem)
//						.collect(Collectors.toSet());
//
//				Map<Long, ServicoPrestadoEntity> mapaItensExistentes = servicoPrestadoRepository
//						.findAllById(todosOsIdItensDaPagina).stream()
//						.collect(Collectors.toMap(ServicoPrestadoEntity::getId, Function.identity()));
//
//				List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();
//
//				for (OrdemServicoDTO dto : osDtosDaPagina) {
//					ClienteEntity cliente = findCliente(dto.getCabecalho().getCodigoCliente(), empresa);
//					if (cliente == null) {
//						logger.warn(
//								"OS nº {} IGNORADA: Cliente com código OMIE '{}' não encontrado para a empresa '{}'.",
//								dto.getCabecalho().getNumeroOs(), dto.getCabecalho().getCodigoCliente(),
//								empresa.getNomeFantasia());
//						continue;
//					}
//
//					CategoriaEntity categoria = findCategoria(dto.getInformacoesAdicionais().getCodigoCategoria(),
//							empresa);
//					ContaCorrenteEntity contaCorrente = findContaCorrente(
//							dto.getInformacoesAdicionais().getCodigoContaCorrente(), empresa);
//					ProjetoEntity projeto = findProjeto(dto.getInformacoesAdicionais().getCodigoProjeto(), empresa);
//
//					List<DepartamentoEntity> departamentosAssociados = new ArrayList<>();
//					if (dto.getDepartamentos() != null) {
//						for (var deptoDto : dto.getDepartamentos()) {
//							findDepartamento(deptoDto.getCodigoDepartamento(), empresa)
//									.ifPresent(departamentosAssociados::add);
//						}
//					}
//
//					OrdemServicoId osId = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
//					OrdemServicoEntity osEntity = ordemServicoRepository.findById(osId)
//							.orElseGet(OrdemServicoEntity::new);
//
//					osEntity.atualizarDados(dto, empresa, cliente, categoria, contaCorrente, projeto,
//							departamentosAssociados);
//
//					// LÓGICA DE SINCRONIZAÇÃO DOS ITENS MOVIDA PARA CÁ
//					atualizarItensDeServico(osEntity, dto.getServicosPrestados(), mapaItensExistentes);
//
//					loteParaSalvar.add(osEntity);
//				}
//
//				if (!loteParaSalvar.isEmpty()) {
//					ordemServicoRepository.saveAll(loteParaSalvar);
//				}
//
//				paginaAtual++;
//			} while (paginaAtual <= totalPaginas);
//
//			logger.info("Sincronização de Ordens de Serviço concluída para a empresa: {}", empresa.getNomeFantasia());
//		}
//		logger.info("--- FIM DA ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//	}
//
//	private void atualizarItensDeServico(OrdemServicoEntity osEntity, List<ServicoPrestadoDTO> dtos,
//			Map<Long, ServicoPrestadoEntity> mapaItensExistentes) {
//
//		if (dtos == null || dtos.isEmpty()) {
//			osEntity.getServicos().clear();
//			return;
//		}
//
//		Set<Long> dtoItemIds = dtos.stream().map(ServicoPrestadoDTO::getIdItem).collect(Collectors.toSet());
//
//		// 1. Remove da entidade os serviços que não estão mais no DTO
//		osEntity.getServicos().removeIf(servicoExistente -> !dtoItemIds.contains(servicoExistente.getId()));
//
//		Map<Long, ServicoPrestadoEntity> servicosAtuaisDaEntidade = osEntity.getServicos().stream()
//				.collect(Collectors.toMap(ServicoPrestadoEntity::getId, Function.identity()));
//
//		// 2. Itera sobre os DTOs para atualizar os existentes e adicionar os novos
//		for (ServicoPrestadoDTO dto : dtos) {
//			ServicoPrestadoEntity servicoParaProcessar = servicosAtuaisDaEntidade.get(dto.getIdItem());
//
//			if (servicoParaProcessar != null) {
//				// Se o serviço já está associado à OS, apenas atualiza seus dados
//				servicoParaProcessar.atualizarDados(dto);
//			} else {
//				// Se não está na OS, pode ser um item novo ou um item que já existe no banco
//				// (vindo de outra OS nesta página)
//				servicoParaProcessar = mapaItensExistentes.get(dto.getIdItem());
//				if (servicoParaProcessar != null) {
//					// Já existe no banco: atualiza e associa a esta OS
//					servicoParaProcessar.atualizarDados(dto);
//					servicoParaProcessar.setOrdemServico(osEntity);
//				} else {
//					// É realmente novo: cria a entidade
//					servicoParaProcessar = new ServicoPrestadoEntity(dto, osEntity);
//				}
//				osEntity.getServicos().add(servicoParaProcessar);
//			}
//		}
//	}
//
//	// Métodos auxiliares para buscar dependências (findCliente, findCategoria,
//	// etc.)
//	private ClienteEntity findCliente(Long cod, EmpresaEntity emp) {
//		return (cod == null) ? null
//				: clienteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null);
//	}
//
//	private CategoriaEntity findCategoria(String cod, EmpresaEntity emp) {
//		return (cod == null || cod.trim().isEmpty()) ? null
//				: categoriaRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo())).orElse(null);
//	}
//
//	private ContaCorrenteEntity findContaCorrente(Long cod, EmpresaEntity emp) {
//		return (cod == null) ? null
//				: contaCorrenteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo()))
//						.orElse(null);
//	}
//
//	private ProjetoEntity findProjeto(Long cod, EmpresaEntity emp) {
//		return (cod == null) ? null
//				: projetoRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null);
//	}
//
//	private Optional<DepartamentoEntity> findDepartamento(String cod, EmpresaEntity emp) {
//		return (cod == null) ? Optional.empty()
//				: departamentoRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo()));
//	}
//}

//-------------------------------------------------------------------------------------------

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
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
//	@Autowired
//	private OrdemServicoRepository ordemServicoRepository;
////	@Autowired
////	private ServicoPrestadoRepository servicoPrestadoRepository;
//	@Autowired
//	private OmieApiClientService omieApiService;
//	
//	// ---------------------------- SHAREPOINT ------------------------------
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
//	// CORREÇÃO 3: Usar wildcard (?) para indicar que aceitamos qualquer tipo de
//	// BaseComposedEntity
//	T_ENTITY extends BaseComposedEntity<?>> void sincronizarEntidadeDependente(String entidadeNome,
//			AbstractSharePointService<T_DTO, ?> service, JpaRepository<T_ENTITY, EntidadeCompostaId> repository,
//			Function<T_DTO, String> empresaNomeExtractor,
//			BiFunction<T_DTO, EmpresaEntity, EntidadeCompostaId> idCreator,
//			BiFunction<T_DTO, EmpresaEntity, T_ENTITY> entityCreator, BiConsumer<T_ENTITY, T_DTO> entityUpdater)
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
//	// ---------------------------- OMIE ------------------------------
//	@Transactional
//	public void sincronizarOrdensDeServico() throws ExecutionException, InterruptedException {
//		logger.info("--- INICIANDO ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//		List<EmpresaEntity> empresas = empresaRepository.findAll();
//		
//		for (EmpresaEntity empresa : empresas) {
//			logger.info("Processando Ordens de Serviço para a empresa: {}", empresa.getNomeFantasia());
//			int paginaAtual = 1;
//			int totalPaginas;
//			
//			do {
//				if (paginaAtual > 1) {
//					try {
//						Thread.sleep(1000L); // Delay de 1 segundo
//					} catch (InterruptedException e) {
//						Thread.currentThread().interrupt();
//						break;
//					}
//				}
//				
//				OmieListarOsResponse resposta = omieApiService.listarOsPorPagina(empresa, paginaAtual).get();
//				totalPaginas = resposta.getTotalDePaginas();
//				
//				if (resposta.getOrdensDeServico() == null || resposta.getOrdensDeServico().isEmpty()) {
//					logger.info("Nenhuma OS encontrada na página {} para a empresa {}. Fim da paginação.", paginaAtual,
//							empresa.getNomeFantasia());
//					break;
//				}
//				
//				logger.info("Processando página {} de {} para a empresa '{}'. {} registros encontrados.", paginaAtual,
//						totalPaginas, empresa.getNomeFantasia(), resposta.getOrdensDeServico().size());
//				List<OrdemServicoEntity> loteParaSalvar = new ArrayList<>();
////				 Dentro do método sincronizarOrdensDeServico, substitua o loop 'for' por este:
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
//				
//				if (!loteParaSalvar.isEmpty()) {
//					ordemServicoRepository.saveAll(loteParaSalvar);
//				}
//				
//				paginaAtual++;
//			} while (paginaAtual <= totalPaginas);
//			
//			logger.info("Sincronização de Ordens de Serviço concluída para a empresa: {}", empresa.getNomeFantasia());
//		}
//		logger.info("--- FIM DA ROTINA DE SINCRONIZAÇÃO DE ORDENS DE SERVIÇO (OMIE) ---");
//	}
//	
//	private ClienteEntity findCliente(Long cod, EmpresaEntity emp) {
//		return (cod == null) ? null
//				: clienteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null);
//	}
//	
//	private CategoriaEntity findCategoria(String cod, EmpresaEntity emp) {
//		return (cod == null) ? null
//				: categoriaRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo())).orElse(null);
//	}
//	
//	private ContaCorrenteEntity findContaCorrente(Long cod, EmpresaEntity emp) {
//		return (cod == null) ? null
//				: contaCorrenteRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo()))
//				.orElse(null);
//	}
//	
//	private ProjetoEntity findProjeto(Long cod, EmpresaEntity emp) {
//		return (cod == null) ? null
//				: projetoRepository.findById(new EntidadeCompostaId(String.valueOf(cod), emp.getCodigo())).orElse(null);
//	}
//	
//	private Optional<DepartamentoEntity> findDepartamento(String cod, EmpresaEntity emp) {
//		return (cod == null) ? Optional.empty()
//				: departamentoRepository.findById(new EntidadeCompostaId(cod, emp.getCodigo()));
//	}
//}

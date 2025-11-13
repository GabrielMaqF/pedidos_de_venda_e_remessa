package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.ServicoPrestadoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OrdemServicoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OrdemServicoDTO.DepartamentoOsDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OrdemServicoDTO.EmailDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OrdemServicoDTO.ImpostosDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OrdemServicoDTO.ParcelaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OrdemServicoDTO.ServicoPrestadoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ordens_servico_omie")
@Getter
@Setter
@NoArgsConstructor
public class OrdemServicoEntity {

	@EmbeddedId
	private OrdemServicoId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("empresaCodigo")
	@JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo")
	private EmpresaEntity empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "cliente_empresa_codigo", referencedColumnName = "empresa_codigo") })
	private ClienteFornecedorEntity cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "categoria_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "categoria_empresa_codigo", referencedColumnName = "empresa_codigo") })
	private CategoriaEntity categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "conta_corrente_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "conta_corrente_empresa_codigo", referencedColumnName = "empresa_codigo") })
	private ContaCorrenteEntity contaCorrente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "projeto_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "projeto_empresa_codigo", referencedColumnName = "empresa_codigo") })
	private ProjetoEntity projeto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "vendedor_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "vendedor_empresa_codigo", referencedColumnName = "empresa_codigo") })
	private VendedorEntity vendedor;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "nota_fiscal_servico_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "nota_fiscal_servico_empresa_codigo", referencedColumnName = "empresa_codigo") })
//	private NotaFiscalServicoEntity notaFiscalServico;

	// --- SUBSTITUA O @OneToOne pelo @OneToMany ---
	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<NotaFiscalServicoEntity> notasFiscais = new ArrayList<>();
	// --- FIM DA ALTERAÇÃO ---

	private Long codigo;
	private String origem;
	private String numeroOs;
	private String etapa;
	private LocalDate dataPrevisao;
	private BigDecimal valorTotal;
	private String codigoIntegracaoOs;
	private String codigoParcela;
	private Integer quantidadeParcelas;
	private BigDecimal valorTotalImpostosRetidos;
	private LocalDateTime dataInclusao;
	private LocalDateTime dataAlteracao;
	private LocalDateTime dataFaturamento;
	private LocalDateTime dataCancelamento;
	private boolean cancelada;
	private boolean faturada;
	private String numeroContrato;
	private String cidadePrestacaoServico;
	private String contato;
	@Column(columnDefinition = "TEXT")
	private String dadosAdicionaisNF;
	@Column(columnDefinition = "TEXT")
	private String observacoesOs;

	@OneToOne(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private OrdemServicoEmailEntity email;

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ServicoPrestadoEntity> servicos = new ArrayList<>();

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrdemServicoParcelaEntity> parcelas = new ArrayList<>();

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrdemServicoDepartamentoEntity> departamentos = new ArrayList<>();

	public OrdemServicoEntity(OrdemServicoDTO dto, EmpresaEntity empresa) {
		this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
		this.empresa = empresa;
		this.codigo = dto.getCabecalho().getCodigoOs();
	}

	public OrdemServicoEntity(OrdemServicoDTO dto, EmpresaEntity empresa, ClienteFornecedorEntity cliente,
			CategoriaEntity categoria, ContaCorrenteEntity contaCorrente, ProjetoEntity projeto,
			List<DepartamentoEntity> departamentos, VendedorEntity vendedor) {

		this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
		this.empresa = empresa;
		this.codigo = dto.getCabecalho().getCodigoOs();

		atualizarDados(dto, cliente, categoria, contaCorrente, projeto, departamentos, vendedor);
	}

	public void atualizarDados(OrdemServicoDTO dto, ClienteFornecedorEntity cliente, CategoriaEntity categoria,
			ContaCorrenteEntity contaCorrente, ProjetoEntity projeto, List<DepartamentoEntity> departamentos,
			VendedorEntity vendedor) {

		this.cliente = cliente;
		this.categoria = categoria;
		this.contaCorrente = contaCorrente;
		this.projeto = projeto;
		this.vendedor = vendedor;

		Optional.ofNullable(dto.getCabecalho()).ifPresent(cabecalho -> {
			this.numeroOs = cabecalho.getNumeroOs();
			this.etapa = cabecalho.getEtapa();
			this.valorTotal = cabecalho.getValorTotal();
			this.dataPrevisao = DateUtil.parseLocalDate(cabecalho.getDataPrevisao());
			this.codigoIntegracaoOs = cabecalho.getCodigoIntegracaoOs();
			this.codigoParcela = cabecalho.getCodigoParcela();
			this.quantidadeParcelas = cabecalho.getQuantidadeParcelas();
			this.valorTotalImpostosRetidos = cabecalho.getValorTotalImpostosRetidos();
		});

		Optional.ofNullable(dto.getInfoCadastro()).ifPresent(info -> {
			this.dataInclusao = DateUtil.parseLocalDateTime(info.getDataInclusao(), info.getHoraInclusao());
			this.dataAlteracao = DateUtil.parseLocalDateTime(info.getDataAlteracao(), info.getHoraAlteracao());
			this.dataFaturamento = DateUtil.parseLocalDateTime(info.getDataFaturamento(), info.getHoraFaturamento());
			this.dataCancelamento = DateUtil.parseLocalDateTime(info.getDataCancelamento(), info.getHoraCancelamento());
			this.cancelada = info.isCancelada();
			this.faturada = info.isFaturada();
			this.origem = info.getOrigem();
		});

		Optional.ofNullable(dto.getInformacoesAdicionais()).ifPresent(info -> {
			this.numeroContrato = info.getNumeroContrato();
			this.cidadePrestacaoServico = info.getCidadePrestacaoServico();
			this.dadosAdicionaisNF = info.getDadosAdicionaisNF();
			this.contato = info.getContato();
		});

		Optional.ofNullable(dto.getObservacoes()).ifPresent(obs -> {
			this.observacoesOs = obs.getObservacao();
		});

		atualizarServicos(dto.getServicosPrestados());
		atualizarEmail(dto.getEmail());
		atualizarParcelas(dto.getParcelas());
		atualizarDepartamentos(dto.getDepartamentos(), departamentos);
	}

	public void atualizarServicos(List<ServicoPrestadoDTO> dtos) {
	    
	    this.servicos.clear();// Limpa a lista atual pra respeitar orphanRemoval = true

	    if (dtos != null) {
	        dtos.forEach(dto -> this.servicos.add(new ServicoPrestadoEntity(dto, this)));
	    }
	}
	
	public void atualizarEmail(EmailDTO emailDto) {
		if (emailDto != null) {
			if (this.email == null) {
				this.email = new OrdemServicoEmailEntity(emailDto, this);
			} else {
				this.email.atualizarDados(emailDto);
			}
		} else {
			this.email = null;
		}
	}

	public void atualizarParcelas(List<ParcelaDTO> dtos) {
		this.parcelas.clear();
		if (dtos != null) {
			dtos.forEach(dto -> this.parcelas.add(new OrdemServicoParcelaEntity(dto, this)));
		}
	}

	public void atualizarDepartamentos(List<DepartamentoOsDTO> dtos, List<DepartamentoEntity> deptoEntities) {
		this.departamentos.clear();
		if (dtos == null || deptoEntities == null)
			return;

		Map<String, DepartamentoEntity> deptoMap = deptoEntities.stream()
				.collect(Collectors.toMap(de -> de.getId().getCodigo(), Function.identity()));

		dtos.forEach(dto -> {
			DepartamentoEntity deptoEntity = deptoMap.get(dto.getCodigoDepartamento());
			if (deptoEntity != null) {
				this.departamentos.add(new OrdemServicoDepartamentoEntity(dto, this, deptoEntity));
			}
		});

	}

	@Embeddable
	@Getter
	@Setter
	@NoArgsConstructor
	public static class ImpostosEmbeddable {

		private BigDecimal aliquotaCofins;
		private BigDecimal aliquotaCsll;
		private BigDecimal aliquotaInss;
		private BigDecimal aliquotaIrrf;
		private BigDecimal aliquotaIss;
		private BigDecimal aliquotaPis;

		private BigDecimal valorCofins;
		private BigDecimal valorCsll;
		private BigDecimal valorInss;
		private BigDecimal valorIrrf;
		private BigDecimal valorIss;
		private BigDecimal valorPis;

		public ImpostosEmbeddable(ImpostosDTO dto) {
			Optional.ofNullable(dto).ifPresent(impostos -> {
				this.aliquotaCofins = impostos.getAliquotaCofins();
				this.aliquotaCsll = impostos.getAliquotaCsll();
				this.aliquotaInss = impostos.getAliquotaInss();
				this.aliquotaIrrf = impostos.getAliquotaIrrf();
				this.aliquotaIss = impostos.getAliquotaIss();
				this.aliquotaPis = impostos.getAliquotaPis();
				this.valorCofins = impostos.getValorCofins();
				this.valorCsll = impostos.getValorCsll();
				this.valorInss = impostos.getValorInss();
				this.valorIrrf = impostos.getValorIrrf();
				this.valorIss = impostos.getValorIss();
				this.valorPis = impostos.getValorPis();
			});
		}
	}

	@Entity
	@Table(name = "ordens_servico_itens_omie")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class ServicoPrestadoEntity {

		@EmbeddedId
		private ServicoPrestadoId id;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumns({
				@JoinColumn(name = "ordem_servico_id", referencedColumnName = "id", insertable = false, updatable = false),
				@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo", insertable = false, updatable = false) })
		private OrdemServicoEntity ordemServico;

		private String codigoCnae;

		private Long idItemOmie;
		private Long codigoServico;

		@Column(name = "codigo_servico_lc116")
		private String codigoServicoLC116;

		@Column(columnDefinition = "TEXT")
		private String descricao;
		private BigDecimal quantidade;
		private BigDecimal valorUnitario;
		private BigDecimal valorDesconto;

		@Embedded
		private ImpostosEmbeddable impostos;

		public ServicoPrestadoEntity(ServicoPrestadoDTO dto, OrdemServicoEntity ordemServico) {
			this.ordemServico = ordemServico;
			this.id = new ServicoPrestadoId(this.ordemServico.getId().getCodigoOs(),
					this.ordemServico.getId().getEmpresaCodigo(), dto.getSequenciaItem());
			this.atualizarDados(dto);
		}

		public void atualizarDados(ServicoPrestadoDTO dto) {
			this.idItemOmie = dto.getIdItem();
			this.codigoServico = dto.getCodigoServico();
			this.codigoServicoLC116 = dto.getCodigoServicoLC116();
			this.codigoCnae = dto.getCodigoCnae();
			this.descricao = dto.getDescricao();
			this.quantidade = dto.getQuantidade();
			this.valorUnitario = dto.getValorUnitario();
			this.valorDesconto = dto.getValorDesconto();
			this.impostos = new ImpostosEmbeddable(dto.getImpostos());
		}
	}

	@Entity
	@Table(name = "ordens_servico_parcelas_omie")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class OrdemServicoParcelaEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
				@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") })
		private OrdemServicoEntity ordemServico;

		private Integer numeroParcela;
		private LocalDate dataVencimento;
		private BigDecimal valor;
		private BigDecimal numeroPercentual;
		private boolean naoGerarBoleto;

		public OrdemServicoParcelaEntity(ParcelaDTO dto, OrdemServicoEntity ordemServico) {
			this.ordemServico = ordemServico;
			this.atualizarDados(dto);
		}

		public void atualizarDados(ParcelaDTO dto) {
			this.numeroParcela = dto.getNumeroParcela();
			this.dataVencimento = DateUtil.parseLocalDate(dto.getDataVencimento());
			this.valor = dto.getValor();
			this.numeroPercentual = dto.getNumeroPercentual();
			this.naoGerarBoleto = dto.isNaoGerarBoleto();
		}
	}

	@Entity
	@Table(name = "ordens_servico_email_omie")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class OrdemServicoEmailEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		// CORREÇÃO: Removido @MapsId. Agora é um relacionamento de chave estrangeira
		// puro.
		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
				@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") })
		private OrdemServicoEntity ordemServico;

		private boolean enviaBoleto;
		private boolean enviaLink;
		private boolean enviaPix;
		private boolean enviaRecibo;

		@Column(columnDefinition = "TEXT")
		private String enviarPara;

		public OrdemServicoEmailEntity(EmailDTO dto, OrdemServicoEntity ordemServico) {
			this.ordemServico = ordemServico;
			this.atualizarDados(dto);
		}

		public void atualizarDados(EmailDTO dto) {
			this.enviaBoleto = dto.isEnviaBoleto();
			this.enviaLink = dto.isEnviaLink();
			this.enviaPix = dto.isEnviaPix();
			this.enviaRecibo = dto.isEnviaRecibo();
			this.enviarPara = dto.getEnviarPara();
		}
	}

	@Entity
	@Table(name = "ordens_servico_departamentos_omie")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class OrdemServicoDepartamentoEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
				@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") })
		private OrdemServicoEntity ordemServico;

		@ManyToOne(fetch = FetchType.LAZY) // CORREÇÃO: Adicionada anotação @ManyToOne
		@JoinColumns({ @JoinColumn(name = "departamento_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "departamento_empresa_codigo", referencedColumnName = "empresa_codigo") })
		private DepartamentoEntity departamento;

		private BigDecimal percentual;
		private BigDecimal valor;
		private boolean valorFixo;

		// CORREÇÃO: Construtor agora recebe a DepartamentoEntity
		public OrdemServicoDepartamentoEntity(DepartamentoOsDTO dto, OrdemServicoEntity ordemServico,
				DepartamentoEntity departamento) {
			this.ordemServico = ordemServico;
			this.departamento = departamento; // Associa a entidade encontrada
			this.percentual = dto.getPercentual();
			this.valor = dto.getValor();
			this.valorFixo = dto.isValorFixo();
		}
	}

}

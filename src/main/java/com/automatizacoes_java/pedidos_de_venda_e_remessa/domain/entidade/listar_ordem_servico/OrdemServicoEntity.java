package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.VendedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.cliente_fornecedor.ClienteFornecedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_nfse.NotaFiscalServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.EmailDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ParcelaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	public void atualizarDados(OrdemServicoDTO dto, EmpresaEntity empresa, ClienteFornecedorEntity cliente,
			CategoriaEntity categoria, ContaCorrenteEntity contaCorrente, ProjetoEntity projeto,
			List<DepartamentoEntity> departamentos, VendedorEntity vendedor) {

		if (this.id == null) {
			this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
			this.empresa = empresa;
		}

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

		// RESTAURANDO A LÓGICA ANTERIOR AQUI
		atualizarEmail(dto.getEmail());
		atualizarParcelas(dto.getParcelas());
		atualizarDepartamentos(dto.getDepartamentos(), departamentos);
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
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ParcelaDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinColumns;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.MapsId;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "ordens_servico_omie")
//@Getter
//@Setter
//@NoArgsConstructor
//public class OrdemServicoEntity {
//
//	@EmbeddedId
//	private OrdemServicoId id;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@MapsId("empresaCodigo")
//	@JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo")
//	private EmpresaEntity empresa;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "cliente_empresa_codigo", referencedColumnName = "empresa_codigo") })
//	private ClienteEntity cliente;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "categoria_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "categoria_empresa_codigo", referencedColumnName = "empresa_codigo") })
//	private CategoriaEntity categoria;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "conta_corrente_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "conta_corrente_empresa_codigo", referencedColumnName = "empresa_codigo") })
//	private ContaCorrenteEntity contaCorrente;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "projeto_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "projeto_empresa_codigo", referencedColumnName = "empresa_codigo") })
//	private ProjetoEntity projeto;
//
//	private String numeroOs;
//	private String etapa;
//	private LocalDate dataPrevisao;
//	private BigDecimal valorTotal;
//	private String codigoIntegracaoOs;
//	private String codigoParcela;
//	private Integer quantidadeParcelas;
//	private BigDecimal valorTotalImpostosRetidos;
//	private LocalDateTime dataInclusao;
//	private LocalDateTime dataAlteracao;
//	private LocalDateTime dataFaturamento;
//	private LocalDateTime dataCancelamento;
//	private boolean cancelada;
//	private boolean faturada;
//	private String numeroContrato;
//	private String cidadePrestacaoServico;
//	@Column(columnDefinition = "TEXT")
//	private String dadosAdicionaisNF;
//	private String contato;
//
//	@OneToOne(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	private OrdemServicoEmailEntity email;
//
//	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	private List<ServicoPrestadoEntity> servicos = new ArrayList<>();
//
//	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	private List<OrdemServicoParcelaEntity> parcelas = new ArrayList<>();
//
//	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	private List<OrdemServicoDepartamentoEntity> departamentos = new ArrayList<>();
//
//	public void atualizarDados(OrdemServicoDTO dto, EmpresaEntity empresa, ClienteEntity cliente,
//			CategoriaEntity categoria, ContaCorrenteEntity contaCorrente, ProjetoEntity projeto,
//			List<DepartamentoEntity> departamentos) {
//
//		if (this.id == null) {
//			this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
//			this.empresa = empresa;
//		}
//
//		this.cliente = cliente;
//		this.categoria = categoria;
//		this.contaCorrente = contaCorrente;
//		this.projeto = projeto;
//
//		Optional.ofNullable(dto.getCabecalho()).ifPresent(cabecalho -> {
//			this.numeroOs = cabecalho.getNumeroOs();
//			this.etapa = cabecalho.getEtapa();
//			this.valorTotal = cabecalho.getValorTotal();
//			this.dataPrevisao = DateUtil.parseLocalDate(cabecalho.getDataPrevisao());
//			this.codigoIntegracaoOs = cabecalho.getCodigoIntegracaoOs();
//			this.codigoParcela = cabecalho.getCodigoParcela();
//			this.quantidadeParcelas = cabecalho.getQuantidadeParcelas();
//			this.valorTotalImpostosRetidos = cabecalho.getValorTotalImpostosRetidos();
//		});
//
//		Optional.ofNullable(dto.getInfoCadastro()).ifPresent(info -> {
//			this.dataInclusao = DateUtil.parseLocalDateTime(info.getDataInclusao(), info.getHoraInclusao());
//			this.dataAlteracao = DateUtil.parseLocalDateTime(info.getDataAlteracao(), info.getHoraAlteracao());
//			this.dataFaturamento = DateUtil.parseLocalDateTime(info.getDataFaturamento(), info.getHoraFaturamento());
//			this.dataCancelamento = DateUtil.parseLocalDateTime(info.getDataCancelamento(), info.getHoraCancelamento());
//			this.cancelada = info.isCancelada();
//			this.faturada = info.isFaturada();
//		});
//
//		Optional.ofNullable(dto.getInformacoesAdicionais()).ifPresent(info -> {
//			this.numeroContrato = info.getNumeroContrato();
//			this.cidadePrestacaoServico = info.getCidadePrestacaoServico();
//			this.dadosAdicionaisNF = info.getDadosAdicionaisNF();
//			this.contato = info.getContato();
//		});
//
////		atualizarEmail(dto.getEmail());
////		atualizarServicos(dto.getServicosPrestados());
////		atualizarParcelas(dto.getParcelas());
////		atualizarDepartamentos(dto.getDepartamentos(), departamentos);
//	}
//
//	private void atualizarEmail(
//			com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.EmailDTO emailDto) {
//		if (emailDto != null) {
//			if (this.email == null) {
//				this.email = new OrdemServicoEmailEntity(emailDto, this);
//			} else {
//				this.email.atualizarDados(emailDto);
//			}
//		} else {
//			this.email = null;
//		}
//	}
//
//	private void atualizarServicos(List<ServicoPrestadoDTO> dtos) {
//		this.servicos.clear();
//		if (dtos != null) {
//			dtos.forEach(dto -> this.servicos.add(new ServicoPrestadoEntity(dto, this)));
//		}
//	}
//
//	private void atualizarParcelas(List<ParcelaDTO> dtos) {
//		this.parcelas.clear();
//		if (dtos != null) {
//			dtos.forEach(dto -> this.parcelas.add(new OrdemServicoParcelaEntity(dto, this)));
//		}
//	}
//
//	private void atualizarDepartamentos(List<DepartamentoOsDTO> dtos, List<DepartamentoEntity> deptoEntities) {
//		this.departamentos.clear();
//		if (dtos == null || deptoEntities == null)
//			return;
//
//		Map<String, DepartamentoEntity> deptoMap = deptoEntities.stream()
//				.collect(Collectors.toMap(de -> de.getId().getCodigo(), Function.identity()));
//
//		dtos.forEach(dto -> {
//			DepartamentoEntity deptoEntity = deptoMap.get(dto.getCodigoDepartamento());
//			if (deptoEntity != null) {
//				this.departamentos.add(new OrdemServicoDepartamentoEntity(dto, this, deptoEntity));
//			}
//		});
//	}
//}

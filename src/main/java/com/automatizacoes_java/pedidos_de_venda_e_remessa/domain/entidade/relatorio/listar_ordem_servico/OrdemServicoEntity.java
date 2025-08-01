package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
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

	// --- RELACIONAMENTOS (CHAVES ESTRANGEIRAS) ---
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("empresaCodigo")
	@JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo")
	private EmpresaEntity empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "cliente_empresa_id", referencedColumnName = "empresa_codigo") })
	private ClienteEntity cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "categoria_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "categoria_empresa_id", referencedColumnName = "empresa_codigo") })
	private CategoriaEntity categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "conta_corrente_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "conta_corrente_empresa_id", referencedColumnName = "empresa_codigo") })
	private ContaCorrenteEntity contaCorrente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "projeto_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "projeto_empresa_id", referencedColumnName = "empresa_codigo") })
	private ProjetoEntity projeto;

	// --- CAMPOS DO CABECALHO ---
	private String numeroOs;
	private String etapa;
	private LocalDate dataPrevisao;
	private BigDecimal valorTotal;
	private String codigoIntegracaoOs;
	private String codigoParcela;
	private Integer quantidadeParcelas;
	private BigDecimal valorTotalImpostosRetidos;

	// --- CAMPOS DE INFORMACOES DE CADASTRO ---
	private LocalDateTime dataInclusao;
	private LocalDateTime dataAlteracao;
	private LocalDateTime dataFaturamento;
	private LocalDateTime dataCancelamento;
	private boolean cancelada;
	private boolean faturada;

	// --- CAMPOS DE INFORMACOES ADICIONAIS ---
	private String numeroContrato;
	private String cidadePrestacaoServico;
	@Column(columnDefinition = "TEXT")
	private String dadosAdicionaisNF;
	private String contato;

	// --- RELACIONAMENTOS COM TABELAS FILHAS ---
	@OneToOne(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private OrdemServicoEmailEntity email;

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ServicoPrestadoEntity> servicos = new ArrayList<>();

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrdemServicoParcelaEntity> parcelas = new ArrayList<>();

	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrdemServicoDepartamentoEntity> departamentos = new ArrayList<>();

	public OrdemServicoEntity(OrdemServicoDTO dto, EmpresaEntity empresa, ClienteEntity cliente,
			CategoriaEntity categoria, ContaCorrenteEntity contaCorrente, ProjetoEntity projeto) {
		this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
		this.empresa = empresa;
		this.cliente = cliente;
		this.categoria = categoria;
		this.contaCorrente = contaCorrente;
		this.projeto = projeto;
		this.atualizarDados(dto);
	}

	public void atualizarDados(OrdemServicoDTO dto) {
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
		});

		Optional.ofNullable(dto.getInformacoesAdicionais()).ifPresent(info -> {
			this.numeroContrato = info.getNumeroContrato();
			this.cidadePrestacaoServico = info.getCidadePrestacaoServico();
			this.dadosAdicionaisNF = info.getDadosAdicionaisNF();
			this.contato = info.getContato();
		});

		Optional.ofNullable(dto.getEmail()).ifPresent(emailDto -> {
			if (this.email == null) {
				this.email = new OrdemServicoEmailEntity(emailDto, this);
			} else {
				this.email.atualizarDados(emailDto);
			}
		});

		atualizarServicos(dto.getServicosPrestados());
		atualizarParcelas(dto.getParcelas());
		atualizarDepartamentos(dto.getDepartamentos());
	}

	private void atualizarServicos(
			List<com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO> dtos) {
		this.servicos.clear();
		if (dtos != null) {
			dtos.forEach(dto -> this.servicos.add(new ServicoPrestadoEntity(dto, this)));
		}
	}

	private void atualizarParcelas(
			List<com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ParcelaDTO> dtos) {
		this.parcelas.clear();
		if (dtos != null) {
			dtos.forEach(dto -> this.parcelas.add(new OrdemServicoParcelaEntity(dto, this)));
		}
	}

	private void atualizarDepartamentos(
			List<com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO> dtos) {
		this.departamentos.clear();
		if (dtos != null) {
			dtos.forEach(dto -> this.departamentos.add(new OrdemServicoDepartamentoEntity(dto, this)));
		}
	}
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinColumns;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.MapsId;
//import jakarta.persistence.OneToMany;
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
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("empresaCodigo") // CORREÇÃO: Mapeia o campo 'empresaCodigo' do @EmbeddedId
//    @JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo") // CORREÇÃO: Usa o nome padronizado da coluna
//    private EmpresaEntity empresa;
//
//	private String numeroOs;
//	private String etapa;
//	private LocalDate dataPrevisao;
//	private BigDecimal valorTotal;
//
//	private LocalDateTime dataInclusao;
//	private LocalDateTime dataAlteracao;
//	private LocalDateTime dataFaturamento;
//	private LocalDateTime dataCancelamento;
//	private boolean cancelada;
//	private boolean faturada;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "cliente_empresa_id", referencedColumnName = "empresa_codigo") // CORRIGIDO
//	})
//	private ClienteEntity cliente;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "categoria_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "categoria_empresa_id", referencedColumnName = "empresa_codigo") // CORRIGIDO
//	})
//	private CategoriaEntity categoria;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "conta_corrente_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "conta_corrente_empresa_id", referencedColumnName = "empresa_codigo") // CORRIGIDO
//	})
//	private ContaCorrenteEntity contaCorrente;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "projeto_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "projeto_empresa_id", referencedColumnName = "empresa_codigo") // CORRIGIDO
//	})
//	private ProjetoEntity projeto;
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
//	public OrdemServicoEntity(OrdemServicoDTO dto, EmpresaEntity empresa, ClienteEntity cliente,
//			CategoriaEntity categoria, ContaCorrenteEntity contaCorrente, ProjetoEntity projeto) {
//		this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
//		this.empresa = empresa;
//		this.cliente = cliente;
//		this.categoria = categoria;
//		this.contaCorrente = contaCorrente;
//		this.projeto = projeto;
//		this.atualizarDados(dto);
//	}
//
//	public void atualizarDados(OrdemServicoDTO dto) {
//		this.numeroOs = dto.getCabecalho().getNumeroOs();
//		this.etapa = dto.getCabecalho().getEtapa();
//		this.valorTotal = dto.getCabecalho().getValorTotal();
//		this.dataPrevisao = DateUtil.parseLocalDate(dto.getCabecalho().getDataPrevisao());
//		this.dataInclusao = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataInclusao(),
//				dto.getInfoCadastro().getHoraInclusao());
//		this.dataAlteracao = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataAlteracao(),
//				dto.getInfoCadastro().getHoraAlteracao());
//		this.dataFaturamento = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataFaturamento(),
//				dto.getInfoCadastro().getHoraFaturamento());
//		this.dataCancelamento = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataCancelamento(),
//				dto.getInfoCadastro().getHoraCancelamento());
//		this.cancelada = dto.getInfoCadastro().isCancelada();
//		this.faturada = dto.getInfoCadastro().isFaturada();
//
//		atualizarServicos(dto.getServicosPrestados());
//		atualizarParcelas(dto.getParcelas());
//		atualizarDepartamentos(dto.getDepartamentos());
//	}
//
//	private void atualizarServicos(
//			List<com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO> servicosDto) {
//		this.servicos.clear();
//		if (servicosDto != null) {
//			servicosDto.forEach(dto -> this.servicos.add(new ServicoPrestadoEntity(dto, this)));
//		}
//	}
//
//	private void atualizarParcelas(
//			List<com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ParcelaDTO> parcelasDto) {
//		this.parcelas.clear();
//		if (parcelasDto != null) {
//			parcelasDto.forEach(dto -> this.parcelas.add(new OrdemServicoParcelaEntity(dto, this)));
//		}
//	}
//
//	private void atualizarDepartamentos(
//			List<com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO> departamentosDto) {
//		this.departamentos.clear();
//		if (departamentosDto != null) {
//			departamentosDto.forEach(dto -> this.departamentos.add(new OrdemServicoDepartamentoEntity(dto, this)));
//		}
//	}
//}

//-----------------------------------------------------------------------------------------

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinColumns;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.MapsId;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
////@Entity
////@Table(name = "ordens_servico_omie")
//@Getter
//@Setter
//@NoArgsConstructor
//public class OrdemServicoEntity {
//
//	@EmbeddedId
//	private OrdemServicoId id;
//
//	private String numeroOs;
//	private String etapa;
//	private LocalDate dataPrevisao;
//	private BigDecimal valorTotal;
//
//	private LocalDateTime dataInclusao;
//	private LocalDateTime dataAlteracao;
//	private LocalDateTime dataFaturamento;
//	private LocalDateTime dataCancelamento;
//	private boolean cancelada;
//	private boolean faturada;
//
//	// --- RELACIONAMENTOS (CHAVES ESTRANGEIRAS) ---
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@MapsId("empresaId")
//	@JoinColumn(name = "empresa_id")
//	private EmpresaEntity empresa;
//
//	// CORREÇÃO: Usando @JoinColumns para a chave composta de ClienteEntity
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "cliente_empresa_id", referencedColumnName = "empresa_id") })
//	private ClienteEntity cliente;
//
//	// CORREÇÃO: Usando @JoinColumns para a chave composta de CategoriaEntity
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "categoria_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "categoria_empresa_id", referencedColumnName = "empresa_id") })
//	private CategoriaEntity categoria;
//
//	// CORREÇÃO: Usando @JoinColumns para a chave composta de ContaCorrenteEntity
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "conta_corrente_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "conta_corrente_empresa_id", referencedColumnName = "empresa_id") })
//	private ContaCorrenteEntity contaCorrente;
//
//	// CORREÇÃO: Usando @JoinColumns para a chave composta de ProjetoEntity
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "projeto_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "projeto_empresa_id", referencedColumnName = "empresa_id") })
//	private ProjetoEntity projeto;
//
//	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	private List<ServicoPrestadoEntity> servicos = new ArrayList<>();
//
//	public OrdemServicoEntity(OrdemServicoDTO dto, EmpresaEntity empresa, ClienteEntity cliente,
//			CategoriaEntity categoria, ContaCorrenteEntity contaCorrente, ProjetoEntity projeto) {
//		this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
//		this.empresa = empresa;
//		this.cliente = cliente;
//		this.categoria = categoria;
//		this.contaCorrente = contaCorrente;
//		this.projeto = projeto;
//		this.atualizarDados(dto);
//	}
//
//	public void atualizarDados(OrdemServicoDTO dto) {
//		this.numeroOs = dto.getCabecalho().getNumeroOs();
//		this.etapa = dto.getCabecalho().getEtapa();
//		this.valorTotal = dto.getCabecalho().getValorTotal();
//		this.dataPrevisao = DateUtil.parseLocalDate(dto.getCabecalho().getDataPrevisao());
//		this.dataInclusao = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataInclusao(),
//				dto.getInfoCadastro().getHoraInclusao());
//		this.dataAlteracao = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataAlteracao(),
//				dto.getInfoCadastro().getHoraAlteracao());
//		this.dataFaturamento = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataFaturamento(),
//				dto.getInfoCadastro().getHoraFaturamento());
//		this.dataCancelamento = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataCancelamento(),
//				dto.getInfoCadastro().getHoraCancelamento());
//		this.cancelada = dto.getInfoCadastro().isCancelada();
//		this.faturada = dto.getInfoCadastro().isFaturada();
//
//		this.servicos.clear();
//		if (dto.getServicosPrestados() != null) {
//			dto.getServicosPrestados().forEach(servicoDto -> {
//				this.servicos.add(new ServicoPrestadoEntity(servicoDto, this));
//			});
//		}
//	}
//}
//
////package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
////
////import java.math.BigDecimal;
////import java.time.LocalDate;
////import java.time.LocalDateTime;
////import java.util.ArrayList;
////import java.util.List;
////
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.OrdemServicoDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;
////
////import jakarta.persistence.CascadeType;
////import jakarta.persistence.EmbeddedId;
////import jakarta.persistence.Entity;
////import jakarta.persistence.FetchType;
////import jakarta.persistence.JoinColumn;
////import jakarta.persistence.ManyToOne;
////import jakarta.persistence.MapsId;
////import jakarta.persistence.OneToMany;
////import jakarta.persistence.Table;
////import lombok.Getter;
////import lombok.NoArgsConstructor;
////import lombok.Setter;
////
////@Entity
////@Table(name = "ordens_servico_omie")
////@Getter
////@Setter
////@NoArgsConstructor
////public class OrdemServicoEntity {
////
////	@EmbeddedId // 1. Usamos a chave primária composta
////	private OrdemServicoId id;
////
////	private String numeroOs;
////	private String etapa;
////	private LocalDate dataPrevisao;
////	private BigDecimal valorTotal;
////
////	private LocalDateTime dataInclusao;
////	private LocalDateTime dataAlteracao;
////	private LocalDateTime dataFaturamento;
////	private LocalDateTime dataCancelamento;
////	private boolean cancelada;
////	private boolean faturada;
////
////	// --- RELACIONAMENTOS (CHAVES ESTRANGEIRAS) ---
////
////	@ManyToOne(fetch = FetchType.LAZY)
////	@MapsId("empresaId") // 2. Mapeia a parte 'empresaId' da chave composta para esta relação
////	@JoinColumn(name = "empresa_id")
////	private EmpresaEntity empresa;
////
////	@ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "cliente_id")
////	private ClienteEntity cliente;
////
////	@ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "categoria_id")
////	private CategoriaEntity categoria;
////
////	@ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "conta_corrente_id")
////	private ContaCorrenteEntity contaCorrente;
////
////	@ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "projeto_id")
////	private ProjetoEntity projeto;
////
////	// --- RELACIONAMENTO UM-PARA-MUITOS ---
////
////	@OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
////	private List<ServicoPrestadoEntity> servicos = new ArrayList<>();
////
////	public OrdemServicoEntity(OrdemServicoDTO dto, EmpresaEntity empresa, ClienteEntity cliente,
////			CategoriaEntity categoria, ContaCorrenteEntity contaCorrente, ProjetoEntity projeto) {
////		// 3. Criamos a instância da chave composta no construtor
////		this.id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());
////		this.empresa = empresa;
////		this.cliente = cliente;
////		this.categoria = categoria;
////		this.contaCorrente = contaCorrente;
////		this.projeto = projeto;
////		this.atualizarDados(dto);
////	}
////
////	public void atualizarDados(OrdemServicoDTO dto) {
////		// ... (o corpo deste método permanece o mesmo)
////		this.numeroOs = dto.getCabecalho().getNumeroOs();
////		this.etapa = dto.getCabecalho().getEtapa();
////		this.valorTotal = dto.getCabecalho().getValorTotal();
////		this.dataPrevisao = DateUtil.parseLocalDate(dto.getCabecalho().getDataPrevisao());
////		this.dataInclusao = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataInclusao(),
////				dto.getInfoCadastro().getHoraInclusao());
////		this.dataAlteracao = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataAlteracao(),
////				dto.getInfoCadastro().getHoraAlteracao());
////		this.dataFaturamento = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataFaturamento(),
////				dto.getInfoCadastro().getHoraFaturamento());
////		this.dataCancelamento = DateUtil.parseLocalDateTime(dto.getInfoCadastro().getDataCancelamento(),
////				dto.getInfoCadastro().getHoraCancelamento());
////		this.cancelada = dto.getInfoCadastro().isCancelada();
////		this.faturada = dto.getInfoCadastro().isFaturada();
////		this.servicos.clear();
////		if (dto.getServicosPrestados() != null) {
////			dto.getServicosPrestados().forEach(servicoDto -> {
////				this.servicos.add(new ServicoPrestadoEntity(servicoDto, this));
////			});
////		}
////	}
////}

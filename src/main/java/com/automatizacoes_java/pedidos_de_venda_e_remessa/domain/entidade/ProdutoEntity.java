package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ProdutoDTO;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "produto")
@AttributeOverride(name = "nome", column = @Column(name = "descricao")) // espelha o padrão do cliente_fornecedor
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ProdutoEntity extends BaseComposedEntity<Long> implements Serializable, BaseAtualizarDados<ProdutoDTO> {

	private static final long serialVersionUID = 1L;

	/* ------------------ Campos simples ------------------ */

	// Observação: o 'codigo' (Long) herdado de BaseComposedEntity mapeia a coluna
	// "codigo"
	// (via id.codigo -> string) mas exposto em T_CODIGO=Long. O valor vem de
	// codigo_produto (OMIE).
	// O campo 'codigo_textual' guarda o "codigo" string do OMIE (ex.: "A-0002").
	@Column(name = "codigo_textual", length = 60)
	private String codigoTextual;

	@Column(name = "codigo_produto_integracao", length = 120)
	private String codigoProdutoIntegracao;

	@Column(name = "unidade", length = 10)
	private String unidade;

	@Column(name = "ncm", length = 20)
	private String ncm;

	@Column(name = "ean", length = 30)
	private String ean;

	@Column(name = "marca", length = 120)
	private String marca;

	@Column(name = "modelo", length = 120)
	private String modelo;

	@Column(name = "cfop", length = 10)
	private String cfop;

	@Column(name = "cst_icms", length = 10)
	private String cstIcms;

	@Column(name = "csosn_icms", length = 10)
	private String csosnIcms;

	@Column(name = "cst_pis", length = 10)
	private String cstPis;

	@Column(name = "cst_cofins", length = 10)
	private String cstCofins;

	@Column(name = "codigo_beneficio", length = 30)
	private String codigoBeneficio;

	@Column(name = "motivo_deson_icms", length = 255)
	private String motivoDesonIcms;

	@Column(name = "tipo_item", length = 5)
	private String tipoItem;

	@Column(name = "descricao_detalhada", columnDefinition = "TEXT")
	private String descricaoDetalhada;

	@Column(name = "descricao_familia", length = 255)
	private String descricaoFamilia;

	@Column(name = "codInt_familia", length = 80)
	private String codIntFamilia;

	private Long codigoFamilia;

	@Column(name = "cest", length = 20)
	private String cest;

	@Column(name = "obs_internas", columnDefinition = "TEXT")
	private String obsInternas;

	/* Valores numéricos */
	private BigDecimal valorUnitario;

	private BigDecimal aliquotaIcms;
	private BigDecimal aliquotaPis;
	private BigDecimal aliquotaCofins;
	private BigDecimal aliquotaIbpt;
	private BigDecimal perIcmsFcp;

	private BigDecimal redBaseIcms;
	private BigDecimal redBasePis;
	private BigDecimal redBaseCofins;

	private BigDecimal altura;
	private BigDecimal largura;
	private BigDecimal profundidade;
	private BigDecimal pesoBruto;
	private BigDecimal pesoLiq;

	private BigDecimal estoqueMinimo;
	private BigDecimal quantidadeEstoque;

	private Integer diasCrossdocking;
	private Integer diasGarantia;
	private Integer leadTime;

	/* Flags */
	private Boolean produtoLote;
	private Boolean produtoVariacao;
	private Boolean bloqueado;
	private Boolean bloquearExclusao;
	private Boolean exibirDescricaoNfe;
	private Boolean exibirDescricaoPedido;
	private Boolean importadoApi;

	// herdado de BaseComposedEntity: private Boolean inativo;

	/* ------------------ Subentidades 1:1 ------------------ */

	@OneToOne(mappedBy = "produto", fetch = FetchType.LAZY, cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	private InfoProdutoEntity info;

	@OneToOne(mappedBy = "produto", fetch = FetchType.LAZY, cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	private RecomendacoesFiscaisProdutoEntity recomendacoesFiscais;

	/* ------------------ Construtores & atualização ------------------ */

	public ProdutoEntity(ProdutoDTO dto, EmpresaEntity empresa) {
		// a chave composta usa: id.codigo = String (do Long OMIE), id.empresaCodigo =
		// empresa
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigoProduto()), empresa.getCodigo()));
		this.setEmpresa(empresa);
		this.setCodigo(dto.getCodigoProduto()); // campo fantasma tipado (Long) da base

		atualizarDados(dto);
	}

	@Override
	public void atualizarDados(ProdutoDTO dto) {
		// nome herdado = "descricao"
		this.setNome(dto.getDescricao());

		this.codigoTextual = dto.getCodigo();
		this.codigoProdutoIntegracao = dto.getCodigoProdutoIntegracao();

		this.unidade = dto.getUnidade();
		this.ncm = dto.getNcm();
		this.ean = dto.getEan();
		this.marca = dto.getMarca();
		this.modelo = dto.getModelo();

		this.cfop = dto.getCfop();
		this.cstIcms = dto.getCst_icms();
		this.csosnIcms = dto.getCsosn_icms();
		this.cstPis = dto.getCst_pis();
		this.cstCofins = dto.getCst_cofins();
		this.codigoBeneficio = dto.getCodigo_beneficio();
		this.motivoDesonIcms = dto.getMotivo_deson_icms();
		this.tipoItem = dto.getTipoItem();

		this.descricaoDetalhada = dto.getDescricaoDetalhada();
		this.descricaoFamilia = dto.getDescricaoFamilia();
		this.codigoFamilia = dto.getCodigoFamilia();
		this.codIntFamilia = dto.getCodIntFamilia();
		this.cest = dto.getCest();
		this.obsInternas = dto.getObsInternas();

		this.valorUnitario = dto.getValor_unitario();

		this.aliquotaIcms = dto.getAliquotaIcms();
		this.aliquotaPis = dto.getAliquotaPis();
		this.aliquotaCofins = dto.getAliquotaCofins();
		this.aliquotaIbpt = dto.getAliquotaIbpt();
		this.perIcmsFcp = dto.getPerIcmsFcp();

		this.redBaseIcms = dto.getRedBaseIcms();
		this.redBasePis = dto.getRedBasePis();
		this.redBaseCofins = dto.getRedBaseCofins();

		this.altura = dto.getAltura();
		this.largura = dto.getLargura();
		this.profundidade = dto.getProfundidade();
		this.pesoBruto = dto.getPesoBruto();
		this.pesoLiq = dto.getPesoLiq();

		this.estoqueMinimo = dto.getEstoqueMinimo();
		this.quantidadeEstoque = dto.getQuantidadeEstoque();

		this.diasCrossdocking = dto.getDiasCrossdocking();
		this.diasGarantia = dto.getDiasGarantia();
		this.leadTime = dto.getLeadTime();

		this.produtoLote = dto.getProdutoLote();
		this.produtoVariacao = dto.getProdutoVariacao();
		this.bloqueado = dto.getBloqueado();
		this.bloquearExclusao = dto.getBloquearExclusao();
		this.exibirDescricaoNfe = dto.getExibirDescricaoNfe();
		this.exibirDescricaoPedido = dto.getExibirDescricaoPedido();
		this.importadoApi = dto.getImportadoApi();
		this.setInativo(dto.getInativo());

		atualizarInfo(dto.getInfo());
		atualizarRecomendacoes(dto.getRecomendacoesFiscais());
	}

	private void atualizarInfo(ProdutoDTO.InfoProdutoOmieDTO i) {
		if (i == null)
			return;
		if (this.info == null) {
			this.info = new InfoProdutoEntity();
			this.info.setProduto(this);
		}
		this.info.setDAlt(i.getDAlt());
		this.info.setDInc(i.getDInc());
		this.info.setHAlt(i.getHAlt());
		this.info.setHInc(i.getHInc());
		this.info.setUAlt(i.getUAlt());
		this.info.setUInc(i.getUInc());
	}

	private void atualizarRecomendacoes(ProdutoDTO.RecomendacoesFiscaisProdutoOmieDTO r) {
		if (r == null)
			return;
		if (this.recomendacoesFiscais == null) {
			this.recomendacoesFiscais = new RecomendacoesFiscaisProdutoEntity();
			this.recomendacoesFiscais.setProduto(this);
		}
		this.recomendacoesFiscais.setCnpjFabricante(r.getCnpjFabricante());
		this.recomendacoesFiscais.setCupomFiscal(r.getCupomFiscal());
		this.recomendacoesFiscais.setIdCest(r.getIdCest());
		this.recomendacoesFiscais.setIdPrecoTabelado(r.getIdPrecoTabelado());
		this.recomendacoesFiscais.setIndicadorEscala(r.getIndicadorEscala());
		this.recomendacoesFiscais.setMarketPlace(r.getMarketPlace());
		this.recomendacoesFiscais.setOrigemMercadoria(r.getOrigemMercadoria());
	}

	/* ------------------ Subentidades mapeadas ------------------ */

	@Entity
	@Table(name = "produto_info")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class InfoProdutoEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private String dAlt;
		private String dInc;
		private String hAlt;
		private String hInc;
		private String uAlt;
		private String uInc;

		@OneToOne
		@JoinColumns({ @JoinColumn(name = "produto_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
		private ProdutoEntity produto;
	}

	@Entity
	@Table(name = "produto_recomendacoes_fiscais")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class RecomendacoesFiscaisProdutoEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(length = 20)
		private String cnpjFabricante;

		private Boolean cupomFiscal;

		@Column(length = 20)
		private String idCest;

		private Long idPrecoTabelado;

		@Column(length = 5)
		private String indicadorEscala;

		private Boolean marketPlace;

		@Column(length = 5)
		private String origemMercadoria;

		@OneToOne
		@JoinColumns({ @JoinColumn(name = "produto_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
		private ProdutoEntity produto;
	}

}

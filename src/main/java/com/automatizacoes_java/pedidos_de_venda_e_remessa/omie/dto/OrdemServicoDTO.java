package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO principal que representa uma única Ordem de Serviço (OS) vinda do OMIE.
 * Agrega todos os sub-objetos relacionados a uma OS.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdemServicoDTO {

	@JsonProperty("Cabecalho")
	private CabecalhoDTO cabecalho;

	@JsonProperty("Departamentos")
	private List<DepartamentoOsDTO> departamentos;

	@JsonProperty("Email")
	private EmailDTO email;

	@JsonProperty("InfoCadastro")
	private InfoCadastroDTO infoCadastro;

	@JsonProperty("InformacoesAdicionais")
	private InformacoesAdicionaisDTO informacoesAdicionais;

	@JsonProperty("Observacoes")
	private ObservacoesDTO observacoes;

	@JsonProperty("Parcelas")
	private List<ParcelaDTO> parcelas;

	@JsonProperty("ServicosPrestados")
	private List<ServicoPrestadoDTO> servicosPrestados;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CabecalhoDTO {

		@JsonProperty("nCodOS")
		private Long codigoOs;

		@JsonProperty("cNumOS")
		private String numeroOs;

		@JsonProperty("nCodCli")
		private Long codigoCliente;

		@JsonProperty("nCodVend")
		private Long codigoVendedor;

		@JsonProperty("dDtPrevisao")
		private String dataPrevisao;

		@JsonProperty("nValorTotal")
		private BigDecimal valorTotal;

		@JsonProperty("cEtapa")
		private String etapa;

		@JsonProperty("cCodIntOS")
		private String codigoIntegracaoOs;

		@JsonProperty("cCodParc")
		private String codigoParcela;

		@JsonProperty("nQtdeParc")
		private Integer quantidadeParcelas;

		@JsonProperty("nValorTotalImpRet")
		private BigDecimal valorTotalImpostosRetidos;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class DepartamentoOsDTO {

		@JsonProperty("cCodDepto")
		private String codigoDepartamento;

		@JsonProperty("nPerc")
		private BigDecimal percentual;

		@JsonProperty("nValor")
		private BigDecimal valor;

		private boolean valorFixo;

		@JsonProperty("nValorFixo")
		public void setValorFixo(String valorFixoStr) {
			this.valorFixo = "S".equalsIgnoreCase(valorFixoStr);
		}
	}

	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class EmailDTO {

		private boolean enviaBoleto;
		private boolean enviaLink;
		private boolean enviaPix;
		private boolean enviaRecibo;
		private boolean enviaViaUnica;

		@JsonProperty("cEnviarPara")
		private String enviarPara;

		// --- SETTERS CUSTOMIZADOS PARA CONVERSÃO ---

		@JsonProperty("cEnvBoleto")
		public void setEnviaBoleto(String enviaBoleto) {
			this.enviaBoleto = "S".equalsIgnoreCase(enviaBoleto);
		}

		@JsonProperty("cEnvLink")
		public void setEnviaLink(String enviaLink) {
			this.enviaLink = "S".equalsIgnoreCase(enviaLink);
		}

		@JsonProperty("cEnvPix")
		public void setEnviaPix(String enviaPix) {
			this.enviaPix = "S".equalsIgnoreCase(enviaPix);
		}

		@JsonProperty("cEnvRecibo")
		public void setEnviaRecibo(String enviaRecibo) {
			this.enviaRecibo = "S".equalsIgnoreCase(enviaRecibo);
		}

		@JsonProperty("cEnvViaUnica")
		public void setEnviaViaUnica(String enviaViaUnica) {
			this.enviaViaUnica = "S".equalsIgnoreCase(enviaViaUnica);
		}
	}

	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ImpostosDTO {

		private boolean fixarCofins, fixarCsll, fixarInss, fixarIrrf, fixarIss, fixarPis, retemCofins, retemCsll,
				retemInss, retemIrrf, retemPis;

		@JsonProperty("lDeduzISS")
		private boolean deduzIss;

		// Alíquotas
		@JsonProperty("nAliqCOFINS")
		private BigDecimal aliquotaCofins;
		@JsonProperty("nAliqCSLL")
		private BigDecimal aliquotaCsll;
		@JsonProperty("nAliqINSS")
		private BigDecimal aliquotaInss;
		@JsonProperty("nAliqIRRF")
		private BigDecimal aliquotaIrrf;
		@JsonProperty("nAliqISS")
		private BigDecimal aliquotaIss;
		@JsonProperty("nAliqPIS")
		private BigDecimal aliquotaPis;

		// Redução de Base
		@JsonProperty("nAliqRedBaseCOFINS")
		private BigDecimal aliquotaReducaoBaseCofins;
		@JsonProperty("nAliqRedBaseINSS")
		private BigDecimal aliquotaReducaoBaseInss;
		@JsonProperty("nAliqRedBasePIS")
		private BigDecimal aliquotaReducaoBasePis;

		// Bases e Valores
		@JsonProperty("nBaseISS")
		private BigDecimal baseIss;
		@JsonProperty("nTotDeducao")
		private BigDecimal totalDeducao;
		@JsonProperty("nValorCOFINS")
		private BigDecimal valorCofins;
		@JsonProperty("nValorCSLL")
		private BigDecimal valorCsll;
		@JsonProperty("nValorDeducao")
		private BigDecimal valorDeducao;
		@JsonProperty("nValorDeducaoIRRF")
		private BigDecimal valorDeducaoIrrf;
		@JsonProperty("nValorINSS")
		private BigDecimal valorInss;
		@JsonProperty("nValorIRRF")
		private BigDecimal valorIrrf;
		@JsonProperty("nValorISS")
		private BigDecimal valorIss;
		@JsonProperty("nValorPIS")
		private BigDecimal valorPis;
		// --- SETTERS CUSTOMIZADOS PARA CONVERSÃO ---

		@JsonProperty("cFixarCOFINS")
		public void setFixarCofins(String valor) {
			this.fixarCofins = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cFixarCSLL")
		public void setFixarCsll(String valor) {
			this.fixarCsll = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cFixarINSS")
		public void setFixarInss(String valor) {
			this.fixarInss = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cFixarIRRF")
		public void setFixarIrrf(String valor) {
			this.fixarIrrf = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cFixarISS")
		public void setFixarIss(String valor) {
			this.fixarIss = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cFixarPIS")
		public void setFixarPis(String valor) {
			this.fixarPis = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cRetemCOFINS")
		public void setRetemCofins(String valor) {
			this.retemCofins = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cRetemCSLL")
		public void setRetemCsll(String valor) {
			this.retemCsll = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cRetemINSS")
		public void setRetemInss(String valor) {
			this.retemInss = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cRetemIRRF")
		public void setRetemIrrf(String valor) {
			this.retemIrrf = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cRetemPIS")
		public void setRetemPis(String valor) {
			this.retemPis = "S".equalsIgnoreCase(valor);
		}
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class InfoCadastroDTO {

		@JsonProperty("dDtInc")
		private String dataInclusao;

		@JsonProperty("cHrInc")
		private String horaInclusao;

		@JsonProperty("dDtAlt")
		private String dataAlteracao;

		@JsonProperty("cHrAlt")
		private String horaAlteracao;

		@JsonProperty("dDtFat")
		private String dataFaturamento;

		@JsonProperty("cHrFat")
		private String horaFaturamento;

		@JsonProperty("dDtCanc")
		private String dataCancelamento;

		@JsonProperty("cHrCanc")
		private String horaCancelamento;

		@JsonProperty("cAmbiente")
		private String ambiente;

		@JsonProperty("cOrigem")
		private String origem;

		private boolean faturada, cancelada;

		@JsonProperty("cFaturada")
		public void setFaturada(String faturadaStr) {
			this.faturada = "S".equalsIgnoreCase(faturadaStr);
		}

		@JsonProperty("cCancelada")
		public void setCancelada(String canceladaStr) {
			this.cancelada = "S".equalsIgnoreCase(canceladaStr);
		}
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class InformacoesAdicionaisDTO {

		@JsonProperty("cCodCateg")
		private String codigoCategoria;

		@JsonProperty("nCodCC")
		private Long codigoContaCorrente;

		@JsonProperty("nCodProj")
		private Long codigoProjeto;

		@JsonProperty("cNumContrato")
		private String numeroContrato;

		@JsonProperty("cCidPrestServ")
		private String cidadePrestacaoServico;

		@JsonProperty("cDadosAdicNF")
		private String dadosAdicionaisNF;

		@JsonProperty("cContato")
		private String contato;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ObservacoesDTO {

		@JsonProperty("cObsOS")
		private String observacao;

	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ParcelaDTO {

		@JsonProperty("nParcela")
		private Integer numeroParcela;

		@JsonProperty("dDtVenc")
		private String dataVencimento;

		@JsonProperty("nDias")
		private String numeroDias;

		@JsonProperty("nValor")
		private BigDecimal valor;

		@JsonProperty("nPercentual")
		private BigDecimal numeroPercentual;

		private boolean naoGerarBoleto;

		@JsonProperty("nao_gerar_boleto")
		public void setNaoGerarBoleto(String naoGerarBoletoStr) {
			this.naoGerarBoleto = "S".equalsIgnoreCase(naoGerarBoletoStr);
		}
	}

	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ServicoPrestadoDTO {

		@JsonProperty("nIdItem")
		private Long idItem;

		@JsonProperty("nCodServico")
		private Long codigoServico;

		@JsonProperty("cDescServ")
		private String descricao;

		@JsonProperty("nQtde")
		private BigDecimal quantidade;

		@JsonProperty("nValUnit")
		private BigDecimal valorUnitario;

		@JsonProperty("nSeqItem")
		private Integer sequenciaItem;

		@JsonProperty("cCodCategItem")
		private String codigoCategoriaItem;

		@JsonProperty("cCodServLC116")
		private String codigoServicoLC116;

		private String codigoCnae;

		@JsonProperty("cCodServMun")
		public void setCodigoCnae(String codigoServicoMunicipalStr) {
			if (codigoServicoMunicipalStr.contains("/")) {
				this.codigoCnae = codigoServicoMunicipalStr.split("/")[0];
			} else {
				this.codigoCnae = codigoServicoMunicipalStr;
			}
		}

		@JsonProperty("cTpDesconto")
		private String tipoDesconto;

		@JsonProperty("cTribServ")
		private String tributacaoServico;

		@JsonProperty("nAliqDesconto")
		private BigDecimal aliquotaDesconto;

		@JsonProperty("nValorAcrescimos")
		private BigDecimal valorAcrescimos;

		@JsonProperty("nValorDesconto")
		private BigDecimal valorDesconto;

		@JsonProperty("nValorOutrasRetencoes")
		private BigDecimal valorOutrasRetencoes;

		private boolean naoGerarFinanceiro, reembolso, retemIss;

		@JsonProperty("impostos")
		private ImpostosDTO impostos;

		// --- SETTERS CUSTOMIZADOS PARA CONVERSÃO ---

		@JsonProperty("cNaoGerarFinanceiro")
		public void setNaoGerarFinanceiro(String valor) {
			this.naoGerarFinanceiro = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cReembolso")
		public void setReembolso(String valor) {
			this.reembolso = "S".equalsIgnoreCase(valor);
		}

		@JsonProperty("cRetemISS")
		public void setRetemIss(String valor) {
			this.retemIss = "S".equalsIgnoreCase(valor);
		}
	}
}

package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContratoServicoCadastroDTO {

	@JsonProperty("cabecalho")
	private CabecalhoContratoServicoDTO cabecalho;
	
	@JsonProperty("infAdic")
	private InfoAdicContratoServico infoAdic;
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CabecalhoContratoServicoDTO {

		@JsonProperty("nCodCtr")
		private Long codigoContrato;

		@JsonProperty("cNumCtr")
		private String numeroContrato;

		@JsonProperty("nCodCli")
		private Long codigoCliente;

		@JsonProperty("dVigInicial")
		private String dataVigenciaInicial;

		@JsonProperty("dVigFinal")
		private String dataVigenciaFinal;

		@JsonProperty("cTipoFat")
		private String tipoFaturamento;

		@JsonProperty("nDiaFat")
		private Integer diaFaturamento;
		
		@JsonProperty("cCodIntCtr")
		private String codigoIntegracao;
		
		@JsonProperty("nValTotMes")
		private BigDecimal valorTotalMes;
	}
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class InfoAdicContratoServico{
		@JsonProperty("cCidPrestServ")
		private String cidadePrestacaoServico;
		
		@JsonProperty("cCodCateg")
		private String codigoCategoria;
		
		@JsonProperty("cContato")
		private String contato;
		
		@JsonProperty("nCodCC")
		private Long codigoContaCorrente;
		
		@JsonProperty("nCodProj")
		private Long codigoProjeto;
		
		@JsonProperty("nCodVend")
		private Long codigoVendedor;
	}
	
}
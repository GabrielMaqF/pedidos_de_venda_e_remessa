package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "api.microsoft.graph.excel.id.comercial")
@Validated
@Getter
@Setter
public class GraphExcelProperties {

	private String siteShP;
	private String arquivoRelatorio;
	private String tabelaRelatorioOS = "TabelaOS";
	private String tabelaRelatorioPDV = "TabelaPDV";

	@PostConstruct
	public void init() {
		System.out.println("============ PROPERTIES ==========");
		System.out.println("siteShP: " + siteShP);
		System.out.println("arquivoRelatorio: " + arquivoRelatorio);
		System.out.println("tabelaRelatorioOS: " + tabelaRelatorioOS);
		System.out.println("tabelaRelatorioPDV: " + tabelaRelatorioPDV);
	}
}

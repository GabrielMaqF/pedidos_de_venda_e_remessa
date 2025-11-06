package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@Validated
@Getter
@Setter
public class GraphExcelProperties {

	@Value("${api.microsoft.graph.excel.id.drive.gabriel}")
	private String driveGabriel;
	@Value("$api.microsoft.graph.excel.id.relatorio.ordem-servico")
	private String arquivoRelatorioOS;
	@Value("${api.microsoft.graph.excel.id.relatorio.ordem-servico.table}")
	private String tabelaRelatorioOS;

//	@PostConstruct
//	public void init() {
//		System.out.println("============ PROPERTIES ==========");
//		System.out.println(driveGabriel);
//		System.out.println(arquivoRelatorioOS);
//		System.out.println(tabelaRelatorioOS);
//	}
}

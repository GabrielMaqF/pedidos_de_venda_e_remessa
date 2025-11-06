package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.excel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.RelatorioService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphExcelProperties;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.token.TokenMicrosoftGraph;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import lombok.RequiredArgsConstructor;

@Service
public class ExcelOrdemServicoService {

	@Autowired
	private static GraphExcelProperties prop;
	
	@Autowired
	private TokenMicrosoftGraph token;
	
	@Autowired
	private RelatorioService relatorioService;
	
	private static final String urlBaseOS = "https://graph.microsoft.com/v1.0/me/drives('"+prop.getDriveGabriel()+"')/items('"+prop.getArquivoRelatorioOS()+"')/workbook/tables("+prop.getTabelaRelatorioOS()+")/headerRowRange";
	private static final String urlRowOS = urlBaseOS + "/rows";
	private static final String urlHeaderOS = urlBaseOS + "/headerRowRange";
	
	
	private List<Map<String, String>> getRelatorioOrdemServico() {
		return relatorioService.listarTudoRelatorioOrdemServico();
	}
}

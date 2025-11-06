package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.controller.relatorio;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.RelatorioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioOSViewController {

	@Autowired
	private RelatorioService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, String>> findAllOs() {
		return service.listarTudoRelatorioOrdemServico();
	}
}

package com.automatizacoes_java.pedidos_de_venda_e_remessa.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.excel.service.ExcelOrdemServicoService;

import lombok.RequiredArgsConstructor;

@Profile("dev & !test")
@Component
@RequiredArgsConstructor
public class OrdemServicoScheduler {

	private static final Logger logger = LoggerFactory.getLogger(OrdemServicoScheduler.class);

	private final ExcelOrdemServicoService excelOrdemServicoService;

	/**
	 * Roda todo dia às 12:00 (meio-dia) no fuso America/Bahia
	 *
	 * Formato cron do Spring: second minute hour day-of-month month day-of-week 0 0
	 * 12 * * * => 12:00:00 todos os dias
	 */
	@Scheduled(cron = "0 0 12 * * *", zone = "America/Bahia")
	public void atualizarRelatorioDiario() {
		logger.info("Iniciando trabalho diário: atualizarTabelaOSComRelatorio()...");
		excelOrdemServicoService.atualizarTabelaOSComRelatorio();
		logger.info("Job diário finalizado: tabela OS atualizada no Excel.");
	}
}

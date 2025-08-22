package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OmieWebhookDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieWebhookService;

@RestController
@RequestMapping("/webhooks")
public class OmieWebhookController {

	private static final Logger logger = LoggerFactory.getLogger(OmieWebhookController.class);

	@Autowired
	private OmieWebhookService webhookService;

	@PostMapping("")
	public ResponseEntity<String> handleOmieWebhook(@RequestBody OmieWebhookDTO payload) {
		logger.info("Webhook da OMIE recebido para o tópico: {}", payload.getTopic());

		if (payload.getTopic() == null || payload.getAppKey() == null) {
			return ResponseEntity.badRequest().body("Payload invalido.");
		}

		webhookService.processar(payload);

		return ResponseEntity.ok("Evento recebido.");
	}

}

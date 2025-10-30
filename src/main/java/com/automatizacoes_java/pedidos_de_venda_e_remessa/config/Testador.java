package com.automatizacoes_java.pedidos_de_venda_e_remessa.config;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.ClienteFornecedorService;


@Configuration
@Profile("test")
public class Testador implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Testador.class);
    private final ClienteFornecedorService cfService;

    public Testador(ClienteFornecedorService cfService) {
        this.cfService = cfService;
    }

    @Override
    public void run(String... args) {
        logger.info("--- INICIADO ---");

        CompletableFuture<ResponseEntity<?>> fut = cfService.getAllOmieUpdateBDA();

        fut.whenComplete((res, ex) -> {
            if (ex != null) {
                logger.error("Falha no getAllOmieUpdateBDA: {}", ex.toString(), ex);
                return;
            }
            if (res != null) {
                logger.info("STATUS: {} | BODY: {}", res.getStatusCode(), res.getBody());
            } else {
                logger.warn("Future completou, mas o ResponseEntity veio null");
            }
        }).join(); // aguarda para garantir que o log apareça no startup
    }

}

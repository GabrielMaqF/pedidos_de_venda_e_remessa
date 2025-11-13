package com.automatizacoes_java.pedidos_de_venda_e_remessa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphExcelProperties;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphTokenProperties;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({ GraphTokenProperties.class, GraphExcelProperties.class })
@EnableScheduling
public class PedidosDeVendaERemessaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosDeVendaERemessaApplication.class, args);
	}

}

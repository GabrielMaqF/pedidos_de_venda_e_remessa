package com.automatizacoes_java.pedidos_de_venda_e_remessa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphProperties;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphTokenProperties;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({ GraphTokenProperties.class, GraphProperties.class })
public class PedidosDeVendaERemessaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosDeVendaERemessaApplication.class, args);
	}

}

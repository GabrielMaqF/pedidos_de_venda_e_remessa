package com.automatizacoes_java.pedidos_de_venda_e_remessa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service.SincronizacaoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.ClienteService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service.ClienteSharepointService;

@Configuration
@Profile("test")
public class Testador implements CommandLineRunner {

	@Autowired
	SincronizacaoService s;

	@Autowired
	ClienteService cS;

	@Autowired
	ClienteSharepointService cSS;

	@Override
	public void run(String... args) throws Exception {
		s.sincronizarTudo();
//		List<ClienteDTO> c = cSS.listarTodos().get();
//		c.forEach(e -> System.out.println(e));
//		System.out.println(c.size());
//		cS.findAll().forEach(e -> System.out.println(e));
	}
}

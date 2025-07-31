package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "api.microsoft.graph.id")
@Validated
@Getter @Setter
public class GraphProperties {

	private String siteOmie, listEmpresas, listClientes, listProjetos, listCategorias, listContasCorrentes,
			listVendedores, listDepartamentos;

}

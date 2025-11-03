package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.action.incluido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.CategoriaService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.CategoriaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookActionStrategy;

@Component
public class CategoriaIncluidoAction implements OmieWebhookActionStrategy<CategoriaDTO> {

	@Autowired
	private CategoriaService service;

	@Override
	public void processar(CategoriaDTO dto, EmpresaEntity empresa) {
		service.criarOuAtualizarPorOmie(dto, empresa);

	}

	@Override
	public String getAcao() {
		return "incluido";
	}

}

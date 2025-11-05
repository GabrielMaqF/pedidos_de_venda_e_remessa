package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.action.alterado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.DepartamentoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.DepartamentoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookActionStrategy;

@Component
public class DepartamentoAlteradoAction implements OmieWebhookActionStrategy<DepartamentoDTO> {

	@Autowired
	private DepartamentoService service;

	@Override
	public void processar(DepartamentoDTO dto, EmpresaEntity empresa) {
		service.criarOuAtualizarPorOmie(dto, empresa);

	}

	@Override
	public String getAcao() {
		return "alterado";
	}

}

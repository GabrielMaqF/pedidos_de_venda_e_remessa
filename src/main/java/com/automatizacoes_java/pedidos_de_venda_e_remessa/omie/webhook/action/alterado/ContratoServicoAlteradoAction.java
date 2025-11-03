package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.action.alterado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.ContratoServicoService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ContratoServicoCadastroDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookActionStrategy;

@Component
public class ContratoServicoAlteradoAction implements OmieWebhookActionStrategy<ContratoServicoCadastroDTO> {

	@Autowired
	private ContratoServicoService service;

	@Override
	public void processar(ContratoServicoCadastroDTO dto, EmpresaEntity empresa) {
		service.criarOuAtualizarPorOmie(dto, empresa);
	}

	@Override
	public String getAcao() {
		return "incluido";
	}

}

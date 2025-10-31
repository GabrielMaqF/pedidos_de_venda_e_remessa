package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.action.incluido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.VendedorService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.VendedorDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookActionStrategy;

@Component
public class VendedorIncluidoAction implements OmieWebhookActionStrategy<VendedorDTO> {

	@Autowired
	private VendedorService service;

	@Override
	public void processar(VendedorDTO dto, EmpresaEntity empresa) {
		service.criarOuAtualizarPorOmie(dto, empresa);
	}

	@Override
	public String getAcao() {
		return "incluido";
	}

}

package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ProjetoSharepointDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.ProjetoSharepointListResponse;

@Service
public class ProjetoSharepointService
		extends AbstractSharePointService<ProjetoSharepointDTO, ProjetoSharepointListResponse> {

	@Override
	protected String getListId() {
		return graphProperties.getListProjetos();
	}

	@Override
	protected Class<ProjetoSharepointListResponse> getResponseClass() {
		return ProjetoSharepointListResponse.class;
	}

	@Override
	protected Class<ProjetoSharepointDTO> getDtoClass() {
		return ProjetoSharepointDTO.class;
	}
}
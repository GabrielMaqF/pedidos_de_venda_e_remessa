package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ContaCorrenteSharepointDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.ContaCorrenteSharepointListResponse;

@Service
public class ContaCorrenteSharepointService
		extends AbstractSharePointService<ContaCorrenteSharepointDTO, ContaCorrenteSharepointListResponse> {

	@Override
	protected String getListId() {
		return graphProperties.getListContasCorrentes();
	}

	@Override
	protected Class<ContaCorrenteSharepointListResponse> getResponseClass() {
		return ContaCorrenteSharepointListResponse.class;
	}

	@Override
	protected Class<ContaCorrenteSharepointDTO> getDtoClass() {
		return ContaCorrenteSharepointDTO.class;
	}
}
package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.ClienteSharepointListResponse;

@Service
public class ClienteSharepointService extends AbstractSharePointService<ClienteDTO, ClienteSharepointListResponse> {

	@Override
	protected String getListId() {
		return graphProperties.getListClientes();
	}

	@Override
	protected Class<ClienteSharepointListResponse> getResponseClass() {
		return ClienteSharepointListResponse.class;
	}

	@Override
	protected Class<ClienteDTO> getDtoClass() {
		return ClienteDTO.class;
	}
}
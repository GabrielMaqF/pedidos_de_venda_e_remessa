package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteDTO;

public class ClienteSharepointListResponse extends SharePointListResponse<ClienteDTO> {

	@Override
	protected Class<ClienteDTO> getItemType() {
		return ClienteDTO.class;
	}
}
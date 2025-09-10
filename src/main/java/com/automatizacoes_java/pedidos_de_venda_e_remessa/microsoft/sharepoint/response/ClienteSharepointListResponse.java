package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteSharepointDTO;

public class ClienteSharepointListResponse extends SharePointListResponse<ClienteSharepointDTO> {

	@Override
	protected Class<ClienteSharepointDTO> getItemType() {
		return ClienteSharepointDTO.class;
	}
}
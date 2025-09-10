package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.VendedorSharepointDTO;

public class VendedorSharepointListResponse extends SharePointListResponse<VendedorSharepointDTO> {

	@Override
	protected Class<VendedorSharepointDTO> getItemType() {
		return VendedorSharepointDTO.class;
	}
}
package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.VendedorDTO;

public class VendedorSharepointListResponse extends SharePointListResponse<VendedorDTO> {

	@Override
	protected Class<VendedorDTO> getItemType() {
		return VendedorDTO.class;
	}
}
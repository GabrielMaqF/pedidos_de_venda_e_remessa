package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.CategoriaSharepointDTO;

public class CategoriaSharepointListResponse extends SharePointListResponse<CategoriaSharepointDTO> {

	@Override
	protected Class<CategoriaSharepointDTO> getItemType() {
		return CategoriaSharepointDTO.class;
	}
}
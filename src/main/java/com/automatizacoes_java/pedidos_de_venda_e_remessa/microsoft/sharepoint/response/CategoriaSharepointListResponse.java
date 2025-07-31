package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.CategoriaDTO;

public class CategoriaSharepointListResponse extends SharePointListResponse<CategoriaDTO> {

	@Override
	protected Class<CategoriaDTO> getItemType() {
		return CategoriaDTO.class;
	}
}
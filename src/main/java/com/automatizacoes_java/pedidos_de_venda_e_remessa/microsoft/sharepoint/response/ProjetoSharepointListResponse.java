package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ProjetoDTO;

public class ProjetoSharepointListResponse extends SharePointListResponse<ProjetoDTO> {

	@Override
	protected Class<ProjetoDTO> getItemType() {
		return ProjetoDTO.class;
	}
}
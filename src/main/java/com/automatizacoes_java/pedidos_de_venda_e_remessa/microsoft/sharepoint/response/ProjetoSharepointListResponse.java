package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ProjetoSharepointDTO;

public class ProjetoSharepointListResponse extends SharePointListResponse<ProjetoSharepointDTO> {

	@Override
	protected Class<ProjetoSharepointDTO> getItemType() {
		return ProjetoSharepointDTO.class;
	}
}
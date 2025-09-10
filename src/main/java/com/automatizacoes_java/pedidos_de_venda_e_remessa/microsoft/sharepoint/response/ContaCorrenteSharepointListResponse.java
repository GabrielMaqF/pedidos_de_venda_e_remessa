package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ContaCorrenteSharepointDTO;

public class ContaCorrenteSharepointListResponse extends SharePointListResponse<ContaCorrenteSharepointDTO> {

	@Override
	protected Class<ContaCorrenteSharepointDTO> getItemType() {
		return ContaCorrenteSharepointDTO.class;
	}
}
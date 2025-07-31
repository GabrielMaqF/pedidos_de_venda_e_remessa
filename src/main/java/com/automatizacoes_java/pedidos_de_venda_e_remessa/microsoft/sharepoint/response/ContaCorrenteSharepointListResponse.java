package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ContaCorrenteDTO;

public class ContaCorrenteSharepointListResponse extends SharePointListResponse<ContaCorrenteDTO> {

	@Override
	protected Class<ContaCorrenteDTO> getItemType() {
		return ContaCorrenteDTO.class;
	}
}
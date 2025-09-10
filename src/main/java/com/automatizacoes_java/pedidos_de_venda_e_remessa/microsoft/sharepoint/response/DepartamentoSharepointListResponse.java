package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.DepartamentoSharepointDTO;

public class DepartamentoSharepointListResponse extends SharePointListResponse<DepartamentoSharepointDTO> {

	@Override
	protected Class<DepartamentoSharepointDTO> getItemType() {
		return DepartamentoSharepointDTO.class;
	}
}
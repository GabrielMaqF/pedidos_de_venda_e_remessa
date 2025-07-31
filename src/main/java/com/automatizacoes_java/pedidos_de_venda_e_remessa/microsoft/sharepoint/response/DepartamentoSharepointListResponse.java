package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.DepartamentoDTO;

public class DepartamentoSharepointListResponse extends SharePointListResponse<DepartamentoDTO> {

	@Override
	protected Class<DepartamentoDTO> getItemType() {
		return DepartamentoDTO.class;
	}
}
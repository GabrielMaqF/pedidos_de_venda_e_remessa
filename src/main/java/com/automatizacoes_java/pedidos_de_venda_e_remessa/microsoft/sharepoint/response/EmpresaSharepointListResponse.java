package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaSharepointDTO;

public class EmpresaSharepointListResponse extends SharePointListResponse<EmpresaSharepointDTO> {

	@Override
	protected Class<EmpresaSharepointDTO> getItemType() {
		return EmpresaSharepointDTO.class;
	}
}
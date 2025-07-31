package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaDTO;

public class EmpresaSharepointListResponse extends SharePointListResponse<EmpresaDTO> {

	@Override
	protected Class<EmpresaDTO> getItemType() {
		return EmpresaDTO.class;
	}
}
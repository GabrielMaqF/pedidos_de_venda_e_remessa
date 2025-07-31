package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.EmpresaSharepointListResponse;

@Service
public class EmpresaSharepointService extends AbstractSharePointService<EmpresaDTO, EmpresaSharepointListResponse> {

	@Override
	protected String getListId() {
		return graphProperties.getListEmpresas();
	}

	@Override
	protected Class<EmpresaSharepointListResponse> getResponseClass() {
		return EmpresaSharepointListResponse.class;
	}

	@Override
	protected Class<EmpresaDTO> getDtoClass() {
		return EmpresaDTO.class;
	}
}
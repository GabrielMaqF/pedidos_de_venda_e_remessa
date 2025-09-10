package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.DepartamentoSharepointDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.DepartamentoSharepointListResponse;

@Service
public class DepartamentoSharepointService
		extends AbstractSharePointService<DepartamentoSharepointDTO, DepartamentoSharepointListResponse> {

	@Override
	protected String getListId() {
		return graphProperties.getListDepartamentos();
	}

	@Override
	protected Class<DepartamentoSharepointListResponse> getResponseClass() {
		return DepartamentoSharepointListResponse.class;
	}

	@Override
	protected Class<DepartamentoSharepointDTO> getDtoClass() {
		return DepartamentoSharepointDTO.class;
	}
}
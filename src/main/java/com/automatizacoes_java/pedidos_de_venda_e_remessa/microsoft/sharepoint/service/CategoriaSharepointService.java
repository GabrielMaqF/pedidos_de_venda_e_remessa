package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.CategoriaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.CategoriaSharepointListResponse;

@Service
public class CategoriaSharepointService
		extends AbstractSharePointService<CategoriaDTO, CategoriaSharepointListResponse> {

	@Override
	protected String getListId() {
		return graphProperties.getListCategorias();
	}

	@Override
	protected Class<CategoriaSharepointListResponse> getResponseClass() {
		return CategoriaSharepointListResponse.class;
	}

	@Override
	protected Class<CategoriaDTO> getDtoClass() {
		return CategoriaDTO.class;
	}
}
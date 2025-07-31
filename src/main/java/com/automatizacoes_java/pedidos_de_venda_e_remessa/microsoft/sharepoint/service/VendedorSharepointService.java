package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.VendedorDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.VendedorSharepointListResponse;

@Service
public class VendedorSharepointService extends AbstractSharePointService<VendedorDTO, VendedorSharepointListResponse> {

	@Override
	protected String getListId() {
		return graphProperties.getListVendedores();
	}

	@Override
	protected Class<VendedorSharepointListResponse> getResponseClass() {
		return VendedorSharepointListResponse.class;
	}

	@Override
	protected Class<VendedorDTO> getDtoClass() {
		return VendedorDTO.class;
	}
}
package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO principal que representa uma única Ordem de Serviço (OS) vinda do OMIE.
 * Agrega todos os sub-objetos relacionados a uma OS.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdemServicoDTO {

	@JsonProperty("Cabecalho")
	private CabecalhoDTO cabecalho;

	@JsonProperty("Departamentos")
	private List<DepartamentoOsDTO> departamentos;

	@JsonProperty("Email")
	private EmailDTO email;

	@JsonProperty("InfoCadastro")
	private InfoCadastroDTO infoCadastro;

	@JsonProperty("InformacoesAdicionais")
	private InformacoesAdicionaisDTO informacoesAdicionais;

	@JsonProperty("Parcelas")
	private List<ParcelaDTO> parcelas;

	@JsonProperty("ServicosPrestados")
	private List<ServicoPrestadoDTO> servicosPrestados;
}

package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ServicoPrestadoId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ordem_servico_id")
	private Long codigoOs;

	@Column(name = "ordem_servico_empresa_codigo")
	private Long empresaCodigo;

	@Column(name = "sequencia_item")
	private Integer sequenciaItem;
}
package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Column(name = "sharepoint_id", unique = true)
	private String sharepointId;

}
package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_cnae;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_cnae.CnaeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cnae_omie")
@Getter
@Setter
@NoArgsConstructor
public class CnaeEntity {

	@Id
	@Column(name = "codigo")
	private String codigo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "estrutura")
	private String estrutura;

	public CnaeEntity(CnaeDTO dto) {
		this.codigo = dto.getCodigo();
		this.descricao = dto.getDescricao();
		this.estrutura = dto.getEstrutura();
	}
}
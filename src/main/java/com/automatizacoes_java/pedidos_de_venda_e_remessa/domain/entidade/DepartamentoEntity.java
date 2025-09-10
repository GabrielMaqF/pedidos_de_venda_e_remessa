package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.DepartamentoSharepointDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "departamentos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DepartamentoEntity extends BaseComposedEntity<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String estrutura;

	public DepartamentoEntity(DepartamentoSharepointDTO dto, EmpresaEntity e) {
		this.setSharepointId(dto.getId());
		this.setCodigo(dto.getCodigo());
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);

		this.atualizarDados(dto);
	}

	public void atualizarDados(DepartamentoSharepointDTO dto) {
		this.setNome(dto.getDescricao());
		this.setInativo(dto.isInativo());
		this.estrutura = dto.getEstrutura();
	}
}
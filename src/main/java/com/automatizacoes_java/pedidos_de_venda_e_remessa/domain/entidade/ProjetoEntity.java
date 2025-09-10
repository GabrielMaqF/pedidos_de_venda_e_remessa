package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ProjetoSharepointDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.projeto.ProjetoOmieDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "projetos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjetoEntity extends BaseComposedEntity<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codInt;

	public ProjetoEntity(ProjetoSharepointDTO dto, EmpresaEntity e) {
		this.setSharepointId(dto.getId());
		this.setCodigo(dto.getCodigo());
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);

		this.atualizarDados(dto);
	}

	public ProjetoEntity(ProjetoOmieDTO dto, EmpresaEntity e) {
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setCodigo(dto.getCodigo());
		this.setEmpresa(e);
		this.setCodInt(dto.getCodInt());

		this.atualizarDados(dto);
	}

	public void atualizarDados(ProjetoOmieDTO dto) {
		this.setNome(dto.getNome());
		this.setInativo(dto.getInativo());
	}

	public void atualizarDados(ProjetoSharepointDTO dto) {
		this.setNome(dto.getNome());
		this.setInativo(dto.isInativo());
	}
}
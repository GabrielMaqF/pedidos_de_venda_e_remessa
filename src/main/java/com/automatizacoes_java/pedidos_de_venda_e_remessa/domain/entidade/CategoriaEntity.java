package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.CategoriaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoriaEntity extends BaseComposedEntity<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "totalizadora")
	private boolean totalizadora;

	@Column(name = "transferencia")
	private boolean transferencia;

	@Column(name = "despesa")
	private boolean despesa;

	@Column(name = "receita")
	private boolean receita;

	public CategoriaEntity(CategoriaDTO dto, EmpresaEntity e) {
		this.setSharepointId(dto.getId());
		this.setCodigo(dto.getCodigo());
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);

		this.atualizarDados(dto);
	}

	public void atualizarDados(CategoriaDTO dto) {
		this.setInativo(dto.isInativo());
		this.setNome(dto.getDescricao());
		this.setTotalizadora(dto.isTotalizadora());
		this.setTransferencia(dto.isTransferencia());
		this.setDespesa(dto.isDespesa());
		this.setReceita(dto.isReceita());
	}
}
package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.VendedorSharepointDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.vendedor.VendedorOmieDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "vendedores")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class VendedorEntity extends BaseComposedEntity<Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codInt, email;
	private Double comissao;
	private Boolean faturaPedido, visualizaPedido;
	
	public VendedorEntity(VendedorOmieDTO dto, EmpresaEntity e) {
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);
		
		this.atualizarDados(dto);
	}
	
	public void atualizarDados(VendedorOmieDTO dto) {
		this.setCodInt(dto.getCodInt());

		this.setNome(dto.getNome());
		this.setEmail(dto.getEmail());
		
		this.setComissao(dto.getComissao());
		
		this.setFaturaPedido(dto.getFaturaPedido());
		this.setVisualizaPedido(dto.getVisualizaPedido());
		this.setInativo(dto.getInativo());
	}
	
	public VendedorEntity(VendedorSharepointDTO dto, EmpresaEntity e) {
		this.setSharepointId(dto.getId());
		this.setCodigo(dto.getCodigo());
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);

		this.atualizarDados(dto);
	}

	public void atualizarDados(VendedorSharepointDTO dto) {
		this.setNome(dto.getNome());
		this.setInativo(dto.isInativo());
	}

}

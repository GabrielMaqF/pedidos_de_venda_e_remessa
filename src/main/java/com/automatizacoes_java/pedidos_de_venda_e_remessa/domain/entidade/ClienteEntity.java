package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteDTO;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clientes")
@AttributeOverride(name = "nome", column = @Column(name = "razao_social"))
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ClienteEntity extends BaseComposedEntity<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigoIntegracao, nomeFantasia, cnpj, cidade, bairro, estado, cep;

	public ClienteEntity(ClienteDTO dto, EmpresaEntity e) {
		this.setSharepointId(dto.getId());
		this.setCodigo(dto.getCodigo());
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);

		this.atualizarDados(dto);
	}

	public void atualizarDados(ClienteDTO dto) {
		this.codigoIntegracao = dto.getCodigoIntegracao();

		this.setNome(dto.getRazaoSocial());
		this.nomeFantasia = dto.getNomeFantasia();
		this.cnpj = dto.getCnpj();

		this.cidade = dto.getCidade();
		this.bairro = dto.getBairro();
		this.estado = dto.getEstado();
		this.cep = dto.getCep();

		this.setInativo(dto.isInativo());
	}
}
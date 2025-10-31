package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ContaCorrenteDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contas_correntes")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContaCorrenteEntity extends BaseComposedEntity<Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BigDecimal valorLimite;
	private String codigoBanco;
	
	public ContaCorrenteEntity(ContaCorrenteDTO dto, EmpresaEntity e) {
		this.setCodigo(dto.getCodigo());
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);

		this.atualizarDados(dto);
	}

	public void atualizarDados(ContaCorrenteDTO dto) {
		this.setNome(dto.getDescricao());
		this.setCodigoBanco(dto.getCodigoBanco());
		this.setValorLimite(dto.getValorLimite());
	}
}
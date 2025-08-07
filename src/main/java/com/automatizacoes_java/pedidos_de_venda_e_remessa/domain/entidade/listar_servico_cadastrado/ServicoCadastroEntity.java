package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_servico_cadastrado;

import java.math.BigDecimal;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_servicos_cadastrados.ServicoCadastroDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicos_cadastrados_omie")
@Getter
@Setter
@NoArgsConstructor
public class ServicoCadastroEntity extends BaseComposedEntity<Long> {

	private String cCodigo; // cCodigo
	private BigDecimal valorServico;

	public ServicoCadastroEntity(ServicoCadastroDTO dto, EmpresaEntity empresa) {
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getIntListar().getCodigoServico()), empresa.getCodigo()));
		this.setEmpresa(empresa);
		this.atualizarDados(dto);
	}

	public void atualizarDados(ServicoCadastroDTO dto) {
		if (dto.getCabecalho() != null) {
			this.setNome(dto.getCabecalho().getDescricao());
			this.cCodigo = dto.getCabecalho().getCodigo();
			this.valorServico = dto.getCabecalho().getValorServico();
		}
	}
}
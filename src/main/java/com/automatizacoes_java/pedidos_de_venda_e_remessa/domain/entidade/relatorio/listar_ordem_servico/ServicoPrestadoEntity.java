package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;

import java.math.BigDecimal;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ordens_servico_itens_omie")
@Getter
@Setter
@NoArgsConstructor
public class ServicoPrestadoEntity {

	@Id
	@Column(name = "id")
	private Long id; // Usaremos o nIdItem do OMIE como chave primária

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
			@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") // CORREÇÃO
	})
	private OrdemServicoEntity ordemServico;

	@Column(columnDefinition = "TEXT")
	private String descricao;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;

	public ServicoPrestadoEntity(ServicoPrestadoDTO dto, OrdemServicoEntity ordemServico) {
		this.id = dto.getIdItem();
		this.ordemServico = ordemServico;
		this.descricao = dto.getDescricao();
		this.quantidade = dto.getQuantidade();
		this.valorUnitario = dto.getValorUnitario();
	}
}

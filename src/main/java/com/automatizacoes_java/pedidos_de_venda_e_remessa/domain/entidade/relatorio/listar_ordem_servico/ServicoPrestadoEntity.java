package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;

import java.math.BigDecimal;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.ServicoPrestadoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@EmbeddedId
	private ServicoPrestadoId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "ordem_servico_id", referencedColumnName = "id", insertable = false, updatable = false),
			@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo", insertable = false, updatable = false) })
	private OrdemServicoEntity ordemServico;

	private Long idItemOmie;
	private Long codigoServico;

	@Column(name = "codigo_servico_lc116")
	private String codigoServicoLC116;

	@Column(columnDefinition = "TEXT")
	private String descricao;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorDesconto;

	@Embedded
	private ImpostosEmbeddable impostos;

	public ServicoPrestadoEntity(ServicoPrestadoDTO dto, OrdemServicoEntity ordemServico) {
		this.id = new ServicoPrestadoId(ordemServico.getId().getCodigoOs(), ordemServico.getId().getEmpresaCodigo(),
				dto.getSequenciaItem());
		this.ordemServico = ordemServico;
		this.atualizarDados(dto);
	}

	public void atualizarDados(ServicoPrestadoDTO dto) {
		this.idItemOmie = dto.getIdItem();
		this.codigoServico = dto.getCodigoServico();
		this.codigoServicoLC116 = dto.getCodigoServicoLC116();
		this.descricao = dto.getDescricao();
		this.quantidade = dto.getQuantidade();
		this.valorUnitario = dto.getValorUnitario();
		this.valorDesconto = dto.getValorDesconto();
		this.impostos = new ImpostosEmbeddable(dto.getImpostos());
	}
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import java.math.BigDecimal;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embedded;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinColumns;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "ordens_servico_itens_omie")
//@Getter
//@Setter
//@NoArgsConstructor
//public class ServicoPrestadoEntity {
//
//	@Id
//	private Long id; // nIdItem
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
//			@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") })
//	private OrdemServicoEntity ordemServico;
//
//	private Long codigoServico;
//	@Column(columnDefinition = "TEXT")
//	private String descricao;
//	private BigDecimal quantidade;
//	private BigDecimal valorUnitario;
//	private BigDecimal valorDesconto;
//
//	@Embedded
//	private ImpostosEmbeddable impostos;
//
//	public ServicoPrestadoEntity(ServicoPrestadoDTO dto, OrdemServicoEntity ordemServico) {
//		this.id = dto.getIdItem();
//		this.ordemServico = ordemServico;
//		this.atualizarDados(dto);
//	}
//
//	public void atualizarDados(ServicoPrestadoDTO dto) {
//		this.codigoServico = dto.getCodigoServico();
//		this.descricao = dto.getDescricao();
//		this.quantidade = dto.getQuantidade();
//		this.valorUnitario = dto.getValorUnitario();
//		this.valorDesconto = dto.getValorDesconto();
//		this.impostos = new ImpostosEmbeddable(dto.getImpostos());
//	}
//}

//------------------------------------------------------------------------------------------

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import java.math.BigDecimal;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ServicoPrestadoDTO;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinColumns;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "ordens_servico_itens_omie")
//@Getter
//@Setter
//@NoArgsConstructor
//public class ServicoPrestadoEntity {
//
//	@Id
//	@Column(name = "id")
//	private Long id; // Usaremos o nIdItem do OMIE como chave primária
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
//			@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") // CORREÇÃO
//	})
//	private OrdemServicoEntity ordemServico;
//
//	@Column(columnDefinition = "TEXT")
//	private String descricao;
//	private BigDecimal quantidade;
//	private BigDecimal valorUnitario;
//
//	public ServicoPrestadoEntity(ServicoPrestadoDTO dto, OrdemServicoEntity ordemServico) {
//		this.id = dto.getIdItem();
//		this.ordemServico = ordemServico;
//		this.descricao = dto.getDescricao();
//		this.quantidade = dto.getQuantidade();
//		this.valorUnitario = dto.getValorUnitario();
//	}
//}

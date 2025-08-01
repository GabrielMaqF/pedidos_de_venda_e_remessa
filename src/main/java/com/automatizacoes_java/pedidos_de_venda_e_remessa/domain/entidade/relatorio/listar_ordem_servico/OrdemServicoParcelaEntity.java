package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ParcelaDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ordens_servico_parcelas_omie")
@Getter
@Setter
@NoArgsConstructor
public class OrdemServicoParcelaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
			@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") })
	private OrdemServicoEntity ordemServico;

	private Integer numeroParcela;
	private LocalDate dataVencimento;
	private BigDecimal valor;
	private BigDecimal numeroPercentual;
	private boolean naoGerarBoleto;

	public OrdemServicoParcelaEntity(ParcelaDTO dto, OrdemServicoEntity ordemServico) {
		this.ordemServico = ordemServico;
		this.atualizarDados(dto);
	}

	public void atualizarDados(ParcelaDTO dto) {
		this.numeroParcela = dto.getNumeroParcela();
		this.dataVencimento = DateUtil.parseLocalDate(dto.getDataVencimento());
		this.valor = dto.getValor();
		this.numeroPercentual = dto.getNumeroPercentual();
		this.naoGerarBoleto = dto.isNaoGerarBoleto();
	}
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ParcelaDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
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
//@Table(name = "ordens_servico_parcelas_omie")
//@Getter
//@Setter
//@NoArgsConstructor
//public class OrdemServicoParcelaEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
//			@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") // CORREÇÃO
//	})
//	private OrdemServicoEntity ordemServico;
//
//	private Integer numeroParcela;
//	private LocalDate dataVencimento;
//	private BigDecimal valor;
//
//	public OrdemServicoParcelaEntity(ParcelaDTO dto, OrdemServicoEntity ordemServico) {
//		this.ordemServico = ordemServico;
//		this.numeroParcela = dto.getNumeroParcela();
//		this.dataVencimento = DateUtil.parseLocalDate(dto.getDataVencimento());
//		this.valor = dto.getValor();
//	}
//}

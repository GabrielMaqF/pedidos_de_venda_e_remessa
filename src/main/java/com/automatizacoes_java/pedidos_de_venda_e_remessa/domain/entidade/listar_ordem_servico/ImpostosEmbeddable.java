package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico;

import java.math.BigDecimal;
import java.util.Optional;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.ImpostosDTO;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ImpostosEmbeddable {

	private BigDecimal aliquotaCofins;
	private BigDecimal aliquotaCsll;
	private BigDecimal aliquotaInss;
	private BigDecimal aliquotaIrrf;
	private BigDecimal aliquotaIss;
	private BigDecimal aliquotaPis;

	private BigDecimal valorCofins;
	private BigDecimal valorCsll;
	private BigDecimal valorInss;
	private BigDecimal valorIrrf;
	private BigDecimal valorIss;
	private BigDecimal valorPis;

	public ImpostosEmbeddable(ImpostosDTO dto) {
		Optional.ofNullable(dto).ifPresent(impostos -> {
			this.aliquotaCofins = impostos.getAliquotaCofins();
			this.aliquotaCsll = impostos.getAliquotaCsll();
			this.aliquotaInss = impostos.getAliquotaInss();
			this.aliquotaIrrf = impostos.getAliquotaIrrf();
			this.aliquotaIss = impostos.getAliquotaIss();
			this.aliquotaPis = impostos.getAliquotaPis();
			this.valorCofins = impostos.getValorCofins();
			this.valorCsll = impostos.getValorCsll();
			this.valorInss = impostos.getValorInss();
			this.valorIrrf = impostos.getValorIrrf();
			this.valorIss = impostos.getValorIss();
			this.valorPis = impostos.getValorPis();
		});
	}
}
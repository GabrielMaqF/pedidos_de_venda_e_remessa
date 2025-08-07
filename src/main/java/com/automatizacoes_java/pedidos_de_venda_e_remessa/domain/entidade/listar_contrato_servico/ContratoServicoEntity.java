package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_contrato_servico;

import java.time.LocalDate;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_contrato_servico.ContratoServicoCadastroDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.DateUtil;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Table(name = "contratos_servico_omie")

@Getter

@Setter

@NoArgsConstructor

public class ContratoServicoEntity {

	@EmbeddedId

	private EntidadeCompostaId id;

	@ManyToOne(fetch = FetchType.LAZY)

	@MapsId("empresaCodigo")

	@JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo")

	private EmpresaEntity empresa;

	@Column(name = "numero_contrato")

	private String numeroContrato;

	@Column(name = "tipo_faturamento")

	private String tipoFaturamento;

	@Column(name = "dia_faturamento")

	private Integer diaFaturamento;

	@Column(name = "data_vigencia_inicial")

	private LocalDate dataVigenciaInicial;

	@Column(name = "data_vigencia_final")

	private LocalDate dataVigenciaFinal;

	public ContratoServicoEntity(ContratoServicoCadastroDTO dto, EmpresaEntity empresa) {

		this.id = new EntidadeCompostaId(String.valueOf(dto.getCabecalho().getCodigoContrato()), empresa.getCodigo());

		this.empresa = empresa;

		this.atualizarDados(dto);

	}

	public void atualizarDados(ContratoServicoCadastroDTO dto) {

		if (dto.getCabecalho() != null) {

			this.numeroContrato = dto.getCabecalho().getNumeroContrato();

			this.tipoFaturamento = dto.getCabecalho().getTipoFaturamento();

			this.diaFaturamento = dto.getCabecalho().getDiaFaturamento();

			this.dataVigenciaInicial = DateUtil.parseLocalDate(dto.getCabecalho().getDataVigenciaInicial());

			this.dataVigenciaFinal = DateUtil.parseLocalDate(dto.getCabecalho().getDataVigenciaFinal());

		}

	}

}
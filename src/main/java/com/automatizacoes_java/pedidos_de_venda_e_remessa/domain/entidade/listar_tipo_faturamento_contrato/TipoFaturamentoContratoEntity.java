package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_tipo_faturamento_contrato;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_tipo_faturamento_contrato.TipoFaturamentoContratoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipos_faturamento_contrato")
@Getter
@Setter
@NoArgsConstructor
public class TipoFaturamentoContratoEntity {

    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descricao")
    private String descricao;

    public TipoFaturamentoContratoEntity(TipoFaturamentoContratoDTO dto) {
        this.codigo = dto.getCodigo();
        this.descricao = dto.getDescricao();
    }
}
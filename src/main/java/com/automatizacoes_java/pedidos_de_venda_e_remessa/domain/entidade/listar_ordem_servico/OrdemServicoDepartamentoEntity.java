package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico;

import java.math.BigDecimal;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO;

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
@Table(name = "ordens_servico_departamentos_omie")
@Getter
@Setter
@NoArgsConstructor
public class OrdemServicoDepartamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
        @JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo")
    })
    private OrdemServicoEntity ordemServico;

    @ManyToOne(fetch = FetchType.LAZY) // CORREÇÃO: Adicionada anotação @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "departamento_codigo", referencedColumnName = "codigo"),
        @JoinColumn(name = "departamento_empresa_codigo", referencedColumnName = "empresa_codigo")
    })
    private DepartamentoEntity departamento;

    private BigDecimal percentual;
    private BigDecimal valor;
    private boolean valorFixo;

    // CORREÇÃO: Construtor agora recebe a DepartamentoEntity
    public OrdemServicoDepartamentoEntity(DepartamentoOsDTO dto, OrdemServicoEntity ordemServico, DepartamentoEntity departamento) {
        this.ordemServico = ordemServico;
        this.departamento = departamento; // Associa a entidade encontrada
        this.percentual = dto.getPercentual();
        this.valor = dto.getValor();
        this.valorFixo = dto.isValorFixo();
    }
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import java.math.BigDecimal;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.DepartamentoOsDTO;
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
//@Table(name = "ordens_servico_departamentos_omie")
//@Getter
//@Setter
//@NoArgsConstructor
//public class OrdemServicoDepartamentoEntity {
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
//	@JoinColumns({ @JoinColumn(name = "departamento_codigo", referencedColumnName = "codigo"),
//			@JoinColumn(name = "departamento_empresa_id", referencedColumnName = "empresa_codigo") // Correto
//	})
//	private DepartamentoEntity departamento;
//
//	private BigDecimal percentual;
//	private BigDecimal valor;
//	private boolean valorFixo;
//
//	public OrdemServicoDepartamentoEntity(DepartamentoOsDTO dto, OrdemServicoEntity ordemServico) {
//		this.ordemServico = ordemServico;
//		this.percentual = dto.getPercentual();
//		this.valor = dto.getValor();
//		this.valorFixo = dto.isValorFixo();
//		// A lógica para buscar e setar a 'DepartamentoEntity' ficará no
//		// SincronizacaoService
//	}
//}

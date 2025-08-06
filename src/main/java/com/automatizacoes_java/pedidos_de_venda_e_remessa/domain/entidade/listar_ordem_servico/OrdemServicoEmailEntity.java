package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.EmailDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ordens_servico_email_omie")
@Getter
@Setter
@NoArgsConstructor
public class OrdemServicoEmailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// CORREÇÃO: Removido @MapsId. Agora é um relacionamento de chave estrangeira
	// puro.
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
			@JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo") })
	private OrdemServicoEntity ordemServico;

	private boolean enviaBoleto;
	private boolean enviaLink;
	private boolean enviaPix;
	private boolean enviaRecibo;

	@Column(columnDefinition = "TEXT")
	private String enviarPara;

	public OrdemServicoEmailEntity(EmailDTO dto, OrdemServicoEntity ordemServico) {
		this.ordemServico = ordemServico;
		this.atualizarDados(dto);
	}

	public void atualizarDados(EmailDTO dto) {
		this.enviaBoleto = dto.isEnviaBoleto();
		this.enviaLink = dto.isEnviaLink();
		this.enviaPix = dto.isEnviaPix();
		this.enviaRecibo = dto.isEnviaRecibo();
		this.enviarPara = dto.getEnviarPara();
	}
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico.EmailDTO;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "ordens_servico_email_omie")
//@Getter
//@Setter
//@NoArgsConstructor
//public class OrdemServicoEmailEntity {
//
//    @Id
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumns({
//        @JoinColumn(name = "ordem_servico_id", referencedColumnName = "id"),
//        @JoinColumn(name = "ordem_servico_empresa_codigo", referencedColumnName = "empresa_codigo")
//    })
//    private OrdemServicoEntity ordemServico;
//
//    private boolean enviaBoleto;
//    private boolean enviaLink;
//    private boolean enviaPix;
//    private boolean enviaRecibo;
//    
//    @Column(columnDefinition = "TEXT")
//    private String enviarPara;
//
//    public OrdemServicoEmailEntity(EmailDTO dto, OrdemServicoEntity ordemServico) {
//        this.ordemServico = ordemServico;
//        this.atualizarDados(dto);
//    }
//
//    public void atualizarDados(EmailDTO dto) {
//        this.enviaBoleto = dto.isEnviaBoleto();
//        this.enviaLink = dto.isEnviaLink();
//        this.enviaPix = dto.isEnviaPix();
//        this.enviaRecibo = dto.isEnviaRecibo();
//        this.enviarPara = dto.getEnviarPara();
//    }
//}
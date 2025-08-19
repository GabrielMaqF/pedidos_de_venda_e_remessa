package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_nfse;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico.OrdemServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_nfse.NfseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notas_fiscais_servico_omie")
@Getter
@Setter
@NoArgsConstructor
public class NotaFiscalServicoEntity {

	@EmbeddedId
	private EntidadeCompostaId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("empresaCodigo") // Mapeia a parte da chave composta
	@JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo")
	private EmpresaEntity empresa;

//	// Relacionamento One-to-One com a Ordem de Serviço
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "codigo_os", referencedColumnName = "id"),
//			@JoinColumn(name = "empresa_codigo_os", referencedColumnName = "empresa_codigo") })
//	private OrdemServicoEntity ordemServico;

	// --- CORREÇÃO: Mude de @OneToOne para @ManyToOne ---
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "codigo_os", referencedColumnName = "id"),
			@JoinColumn(name = "empresa_codigo_os", referencedColumnName = "empresa_codigo") })
	private OrdemServicoEntity ordemServico;
	// --- FIM DA CORREÇÃO ---

	@Column(name = "numero_nfse")
	private String numeroNfse;

	public NotaFiscalServicoEntity(NfseDTO dto, EmpresaEntity empresa, OrdemServicoEntity os) {
		// A chave primária é o código da NF + código da empresa
		this.id = new EntidadeCompostaId(String.valueOf(dto.getCabecalho().getCodigoNf()), empresa.getCodigo());
		this.empresa = empresa;
		this.ordemServico = os;
		this.atualizarDados(dto);
	}

	public void atualizarDados(NfseDTO dto) {
		if (dto.getCabecalho() != null) {
			this.numeroNfse = dto.getCabecalho().getNumeroNfse();
		}
	}
}
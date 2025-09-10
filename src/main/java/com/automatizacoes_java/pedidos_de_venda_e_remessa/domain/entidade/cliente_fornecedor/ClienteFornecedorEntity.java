package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.cliente_fornecedor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.BaseAtualizarDados;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.ClienteSharepointDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.ClienteOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.DadosBancariosClienteOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.EnderecoEntregaClienteOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.InfoClienteOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.RecomendacoesClienteOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.TagClienteOmieDTO;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cliente_fornecedor")
@AttributeOverride(name = "nome", column = @Column(name = "razao_social"))
@Getter
@Setter
@NoArgsConstructor
//@ToString(callSuper = true)
@ToString(callSuper = true, exclude = { "tags", "dadosBancarios", "info", "recomendacoes", "enderecoEntrega" })
public class ClienteFornecedorEntity extends BaseComposedEntity<Long> implements Serializable, BaseAtualizarDados<ClienteOmieDTO> {
	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private DadosBancariosClienteEntity dadosBancarios;

	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private InfoClienteEntity info;

	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private RecomendacoesClienteEntity recomendacoes;

	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private EnderecoEntregaClienteEntity enderecoEntrega;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<TagClienteEntity> tags = new HashSet<>();

	@Column(name = "email", length = 500)
	private String email;

	@Column(name = "endereco_completo")
	private String endereco;

	@Column(name = "pessoa_fisica")
	private Boolean pessoaFisica;

	@Column(name = "bloquear_faturamento")
	private Boolean bloquearFaturamento;

	private String codigoIntegracao, nomeFantasia, cnpj, cidade, bairro, estado, cep, telefone, enderecoNumero,
			complemento, inscricaoEstadual, inscricaoMunicipal, cnae;

	public ClienteFornecedorEntity(ClienteSharepointDTO dto, EmpresaEntity e) {
		this.setSharepointId(dto.getId());
		this.setCodigo(dto.getCodigo());
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigo()), e.getCodigo()));
		this.setEmpresa(e);

		this.codigoIntegracao = dto.getCodigoIntegracao();

		this.atualizarDados(dto);
	}

	public ClienteFornecedorEntity(ClienteOmieDTO dto, EmpresaEntity e) {
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigoClienteOmie()), e.getCodigo()));
		this.setEmpresa(e);
		this.setCodigo(dto.getCodigoClienteOmie());
		this.setCodigoIntegracao(dto.getCodigoClienteIntegracao());

		this.atualizarDados(dto);
	}

	public void atualizarDados(ClienteSharepointDTO dto) {

		this.setNome(dto.getRazaoSocial());
		this.nomeFantasia = dto.getNomeFantasia();
		this.cnpj = dto.getCnpj();

		this.cidade = dto.getCidade();
		this.bairro = dto.getBairro();
		this.estado = dto.getEstado();
		this.cep = dto.getCep();

		this.telefone = dto.getTelefone();

		this.setInativo(dto.isInativo());
	}

	@Override
	public void atualizarDados(ClienteOmieDTO dto) {
		// Mapeamento dos campos simples
		this.setNome(dto.getRazaoSocial());
		this.nomeFantasia = dto.getNomeFantasia();
		this.cnpj = dto.getCnpjCpf(); // Ajuste no nome do campo
		this.email = dto.getEmail();
		this.telefone = dto.getTelefone1Numero();
		this.endereco = dto.getEndereco();
		this.enderecoNumero = dto.getEnderecoNumero();
		this.bairro = dto.getBairro();
		this.complemento = dto.getComplemento();
		this.estado = dto.getEstado();
		this.cidade = dto.getCidade();
		this.cep = dto.getCep();
		this.inscricaoEstadual = dto.getInscricaoEstadual();
		this.inscricaoMunicipal = dto.getInscricaoMunicipal();
		this.pessoaFisica = dto.getPessoaFisica();
		this.bloquearFaturamento = dto.getBloquearFaturamento();
		this.cnae = dto.getCnae();
		this.setInativo(dto.getInativo());

		// Mapeamento das entidades relacionadas
		atualizarDadosBancarios(dto.getDadosBancarios());
		atualizarInfo(dto.getInfo());
		atualizarRecomendacoes(dto.getRecomendacoes());
		atualizarEnderecoEntrega(dto.getEnderecoEntrega());
		atualizarTags(dto.getTags());
	}

	private void atualizarDadosBancarios(DadosBancariosClienteOmieDTO dto) {
		if (dto != null) {
			if (this.dadosBancarios == null) {
				this.dadosBancarios = new DadosBancariosClienteEntity();
				this.dadosBancarios.setCliente(this);
			}
			this.dadosBancarios.setAgencia(dto.getAgencia());
			this.dadosBancarios.setChavePix(dto.getChavePix());
			this.dadosBancarios.setCodigoBanco(dto.getCodigoBanco());
			this.dadosBancarios.setContaCorrente(dto.getContaCorrente());
			this.dadosBancarios.setDocTitular(dto.getDocTitular());
			this.dadosBancarios.setNomeTitular(dto.getNomeTitular());
			this.dadosBancarios.setTransfPadrao(dto.getTransfPadrao());
		}
	}

	private void atualizarInfo(InfoClienteOmieDTO dto) {
		if (dto != null) {
			if (this.info == null) {
				this.info = new InfoClienteEntity();
				this.info.setCliente(this);
			}
			this.info.setCImpAPI(dto.getCImpAPI());
			this.info.setDAlt(dto.getDAlt());
			this.info.setDInc(dto.getDInc());
			this.info.setHAlt(dto.getHAlt());
			this.info.setHInc(dto.getHInc());
			this.info.setUAlt(dto.getUAlt());
			this.info.setUInc(dto.getUInc());
		}
	}

	private void atualizarRecomendacoes(RecomendacoesClienteOmieDTO dto) {
		if (dto != null) {
			if (this.recomendacoes == null) {
				this.recomendacoes = new RecomendacoesClienteEntity();
				this.recomendacoes.setCliente(this);
			}
			this.recomendacoes.setGerarBoletos(dto.getGerarBoletos());
			this.recomendacoes.setTipoAssinante(dto.getTipoAssinante());
			this.recomendacoes.setEmailFatura(dto.getEmailFatura());
			this.recomendacoes.setNumeroParcelas(dto.getNumeroParcelas());
		}
	}

	private void atualizarEnderecoEntrega(EnderecoEntregaClienteOmieDTO dto) {
		if (dto != null) {
			if (this.enderecoEntrega == null) {
				this.enderecoEntrega = new EnderecoEntregaClienteEntity();
				this.enderecoEntrega.setCliente(this);
			}
			this.enderecoEntrega.setEntBairro(dto.getEntBairro());
			this.enderecoEntrega.setEntCEP(dto.getEntCEP());
			this.enderecoEntrega.setEntCidade(dto.getEntCidade());
			this.enderecoEntrega.setEntCnpjCpf(dto.getEntCnpjCpf());
			this.enderecoEntrega.setEntComplemento(dto.getEntComplemento());
			this.enderecoEntrega.setEntEndereco(dto.getEntEndereco());
			this.enderecoEntrega.setEntEstado(dto.getEntEstado());
			this.enderecoEntrega.setEntNumero(dto.getEntNumero());
			this.enderecoEntrega.setEntRazaoSocial(dto.getEntRazaoSocial());
		}
	}

	private void atualizarTags(List<TagClienteOmieDTO> tagsDto) {
		if (tagsDto != null) {
			this.tags.clear(); // Limpa a lista antiga

			Set<TagClienteEntity> novasTags = tagsDto.stream()
					.map(tagDto -> new TagClienteEntity(tagDto.getTag(), this)).collect(Collectors.toSet());

			this.tags.addAll(novasTags); // Adiciona as novas tags
		}
	}

}
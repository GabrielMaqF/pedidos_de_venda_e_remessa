package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ClienteFornecedorDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ClienteFornecedorDTO.DadosBancariosClienteFornecedorOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ClienteFornecedorDTO.EnderecoEntregaClienteFornecedorOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ClienteFornecedorDTO.InfoClienteFornecedorOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ClienteFornecedorDTO.RecomendacoesClienteFornecedorOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ClienteFornecedorDTO.TagClienteFornecedorOmieDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
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
public class ClienteFornecedorEntity extends BaseComposedEntity<Long>
		implements Serializable, BaseAtualizarDados<ClienteFornecedorDTO> {
	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "clienteFornecedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private DadosBancariosClienteFornecedorEntity dadosBancarios;

	@OneToOne(mappedBy = "clienteFornecedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private InfoClienteFornecedorEntity info;

	@OneToOne(mappedBy = "clienteFornecedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private RecomendacoesClienteFornecedorEntity recomendacoes;

	@OneToOne(mappedBy = "clienteFornecedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private EnderecoEntregaClienteFornecedorEntity enderecoEntrega;

	@OneToMany(mappedBy = "clienteFornecedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<TagClienteFornecedorEntity> tags = new HashSet<>();

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

	public ClienteFornecedorEntity(ClienteFornecedorDTO dto, EmpresaEntity e) {
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigoClienteOmie()), e.getCodigo()));
		this.setEmpresa(e);
		this.setCodigo(dto.getCodigoClienteOmie());
		this.setCodigoIntegracao(dto.getCodigoClienteIntegracao());

		this.atualizarDados(dto);
	}

	@Override
	public void atualizarDados(ClienteFornecedorDTO dto) {
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

	private void atualizarDadosBancarios(DadosBancariosClienteFornecedorOmieDTO dto) {
		if (dto != null) {
			if (this.dadosBancarios == null) {
				this.dadosBancarios = new DadosBancariosClienteFornecedorEntity();
				this.dadosBancarios.setClienteFornecedor(this);
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

	private void atualizarInfo(InfoClienteFornecedorOmieDTO dto) {
		if (dto != null) {
			if (this.info == null) {
				this.info = new InfoClienteFornecedorEntity();
				this.info.setClienteFornecedor(this);
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

	private void atualizarRecomendacoes(RecomendacoesClienteFornecedorOmieDTO dto) {
		if (dto != null) {
			if (this.recomendacoes == null) {
				this.recomendacoes = new RecomendacoesClienteFornecedorEntity();
				this.recomendacoes.setClienteFornecedor(this);
			}
			this.recomendacoes.setGerarBoletos(dto.getGerarBoletos());
			this.recomendacoes.setTipoAssinante(dto.getTipoAssinante());
			this.recomendacoes.setEmailFatura(dto.getEmailFatura());
			this.recomendacoes.setNumeroParcelas(dto.getNumeroParcelas());
		}
	}

	private void atualizarEnderecoEntrega(EnderecoEntregaClienteFornecedorOmieDTO dto) {
		if (dto != null) {
			if (this.enderecoEntrega == null) {
				this.enderecoEntrega = new EnderecoEntregaClienteFornecedorEntity();
				this.enderecoEntrega.setClienteFornecedor(this);
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

	private void atualizarTags(List<TagClienteFornecedorOmieDTO> tagsDto) {
		if (tagsDto != null) {
			this.tags.clear(); // Limpa a lista antiga

			Set<TagClienteFornecedorEntity> novasTags = tagsDto.stream()
					.map(tagDto -> new TagClienteFornecedorEntity(tagDto.getTag(), this)).collect(Collectors.toSet());

			this.tags.addAll(novasTags); // Adiciona as novas tags
		}
	}

	@Entity
	@Table(name = "cliente_fornecedor_dados_bancarios")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class DadosBancariosClienteFornecedorEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private String agencia;
		private String chavePix;
		private String codigoBanco;
		private String contaCorrente;
		private String docTitular;
		private String nomeTitular;
		private Boolean transfPadrao;

		@OneToOne
		@JoinColumns({ @JoinColumn(name = "cliente_fornecedor_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
		private ClienteFornecedorEntity clienteFornecedor;

	}

	@Entity
	@Table(name = "cliente_fornecedor_endereco_entrega")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class EnderecoEntregaClienteFornecedorEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private String entBairro;
		private String entCEP;
		private String entCidade;
		private String entCnpjCpf;
		private String entComplemento;
		private String entEndereco;
		private String entEstado;
		private String entNumero;
		private String entRazaoSocial;

		@OneToOne
		@JoinColumns({ @JoinColumn(name = "cliente_fornecedor_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
		private ClienteFornecedorEntity clienteFornecedor;
	}

	@Entity
	@Table(name = "cliente_fornecedor_info")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class InfoClienteFornecedorEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private Boolean cImpAPI;
		private String dAlt;
		private String dInc;
		private String hAlt;
		private String hInc;
		private String uAlt;
		private String uInc;

		@OneToOne
		@JoinColumns({ @JoinColumn(name = "cliente_fornecedor_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
		private ClienteFornecedorEntity clienteFornecedor;
	}

	@Entity
	@Table(name = "cliente_fornecedor_recomendacoes")
	@Getter
	@Setter
	@NoArgsConstructor
	public static class RecomendacoesClienteFornecedorEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private Boolean gerarBoletos;
		private String tipoAssinante;
		private String emailFatura;
		private String numeroParcelas;

		@OneToOne
		@JoinColumns({ @JoinColumn(name = "cliente_fornecedor_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
		private ClienteFornecedorEntity clienteFornecedor;
	}

	@Entity
	@Table(name = "cliente_fornecedor_tags") // Tabela específica para as tags de clientes
	@Getter
	@Setter
	@NoArgsConstructor
	public static class TagClienteFornecedorEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(nullable = false)
		private String nome;

		// Relacionamento de volta para o cliente (lado "Muitos" da relação)
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumns({ @JoinColumn(name = "cliente_fornecedor_codigo", referencedColumnName = "codigo"),
				@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
		@JsonIgnore // Evita loops infinitos na serialização JSON
		private ClienteFornecedorEntity clienteFornecedor;

		// Construtor para facilitar a criação
		public TagClienteFornecedorEntity(String nome, ClienteFornecedorEntity clienteFornecedor) {
			this.nome = nome;
			this.clienteFornecedor = clienteFornecedor;
		}

		// Implementação robusta de equals e hashCode baseada no ID
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			TagClienteFornecedorEntity that = (TagClienteFornecedorEntity) o;
			// Se o ID for nulo, as entidades não podem ser consideradas iguais.
			if (this.id == null || that.id == null)
				return false;
			return Objects.equals(id, that.id);
		}

		@Override
		public int hashCode() {
			// Usa a classe para o hashcode, garantindo que objetos nulos não quebrem a
			// aplicação.
			return getClass().hashCode();
		}
	}

}
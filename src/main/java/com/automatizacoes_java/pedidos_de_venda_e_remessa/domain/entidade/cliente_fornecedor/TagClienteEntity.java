package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.cliente_fornecedor;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
@Table(name = "tags_cliente") // Tabela específica para as tags de clientes
@Getter
@Setter
@NoArgsConstructor
public class TagClienteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	// Relacionamento de volta para o cliente (lado "Muitos" da relação)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
	@JsonIgnore // Evita loops infinitos na serialização JSON
	private ClienteFornecedorEntity cliente;

	// Construtor para facilitar a criação
	public TagClienteEntity(String nome, ClienteFornecedorEntity cliente) {
		this.nome = nome;
		this.cliente = cliente;
	}

	// Implementação robusta de equals e hashCode baseada no ID
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TagClienteEntity that = (TagClienteEntity) o;
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
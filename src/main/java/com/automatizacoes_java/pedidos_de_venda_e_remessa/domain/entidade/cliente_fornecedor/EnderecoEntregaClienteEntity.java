package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.cliente_fornecedor;

import java.io.Serializable;

import jakarta.persistence.Entity;
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
@Table(name = "cliente_endereco_entrega")
@Getter
@Setter
@NoArgsConstructor
public class EnderecoEntregaClienteEntity implements Serializable {
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
	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
	private ClienteFornecedorEntity cliente;
}
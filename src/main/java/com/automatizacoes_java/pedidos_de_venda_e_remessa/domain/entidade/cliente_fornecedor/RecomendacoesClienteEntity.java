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
@Table(name = "cliente_recomendacoes")
@Getter
@Setter
@NoArgsConstructor
public class RecomendacoesClienteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean gerarBoletos;
	private String tipoAssinante;
	private String emailFatura;
	private String numeroParcelas;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo"),
			@JoinColumn(name = "empresa_codigo", referencedColumnName = "empresa_codigo") })
	private ClienteFornecedorEntity cliente;
}
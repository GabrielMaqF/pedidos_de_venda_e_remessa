package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString(exclude = "empresa")
public abstract class BaseComposedEntity<T_CODIGO> extends BaseEntity {

	@EmbeddedId
	private EntidadeCompostaId id;

	/**
	 * Campo "fantasma" apenas para leitura (read-only) do código. Facilita o uso do
	 * 'codigo' no código Java de forma fortemente tipada. O valor vem da chave
	 * composta 'id.codigo'.
	 */
	@Column(name = "codigo", insertable = false, updatable = false)
	private T_CODIGO codigo;

	@Column(name = "nome")
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("empresaId") // Mapeia a parte 'empresaId' da nossa chave composta (@EmbeddedId)
	@JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo")
	private EmpresaEntity empresa;

	@Column(name = "inativo")
	private boolean inativo;
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.MappedSuperclass;
//import jakarta.persistence.MapsId;
//import lombok.Getter;
//import lombok.Setter;
//
//@MappedSuperclass
//@Getter
//@Setter
//public abstract class BaseComposedEntity<T_CODIGO> extends BaseEntity {
//
//	@EmbeddedId
//	EntidadeCompostaId id;
//
//	/**
//	 * Este campo armazena o código com seu tipo de dado original (Long, String,
//	 * etc.). As anotações 'insertable=false, updatable=false' são cruciais. Elas
//	 * dizem ao JPA: "Esta propriedade 'codigo' mapeia para a coluna 'codigo' do
//	 * banco, mas APENAS para leitura. Não tente escrever nesta coluna usando este
//	 * campo." A escrita será feita pelo campo 'codigo' dentro da
//	 * EntidadeCompostaId.
//	 */
//	@Column(name = "codigo", insertable = false, updatable = false)
//	private T_CODIGO codigo;
//
//	@Column(name = "nome")
//	private String nome;
//
//	@ManyToOne(fetch = FetchType.EAGER	)
//	@MapsId("empresaId") // Mapeia a parte 'empresaId' da chave composta para esta relação
//	@JoinColumn(name = "empresa_id", referencedColumnName = "id")
//	private EmpresaEntity empresa;
//
//	@Column(name = "inativo")
//	private boolean inativo;
//}

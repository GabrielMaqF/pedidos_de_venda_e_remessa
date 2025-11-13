package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import java.io.Serializable;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.LocalEstoqueDTO;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ID composto: (empresa_codigo, codigo_local_estoque). - id.codigo -> armazena
 * o codigo_local_estoque (como String) para compor a PK - id.empresaCodigo ->
 * empresa - codigo (da Base) -> espelha codigo_local_estoque (read-only) -
 * codigoTextual -> código textual para o usuário (coluna 'codigo') - nome (da
 * Base) -> descrição do local (dto.getDescricao())
 */
@Entity
@Table(name = "locais_estoque")
@AttributeOverrides({
		// chave composta usando codigo_local_estoque + empresa_codigo
		@AttributeOverride(name = "id.codigo", column = @Column(name = "codigo_local_estoque")),
		@AttributeOverride(name = "id.empresaCodigo", column = @Column(name = "empresa_codigo")),

		// campo "codigo" herdado da BaseComposedEntity passa a espelhar
		// codigo_local_estoque
		@AttributeOverride(name = "codigo", column = @Column(name = "codigo_local_estoque", insertable = false, updatable = false)) })
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LocalEstoqueEntity extends BaseComposedEntity<Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	// código textual para o usuário (ex.: "PADRAO", "0001")
	@Column(name = "codigo")
	private String codigoTextual;

	@Column(name = "codigo_cliente")
	private Long codigoCliente;

	@Column(name = "disp_consumo_op")
	private Boolean dispConsumoOP;

	@Column(name = "disp_ordem_producao")
	private Boolean dispOrdemProducao;

	@Column(name = "disp_remessa")
	private Boolean dispRemessa;

	@Column(name = "disp_venda")
	private Boolean dispVenda;

	@Column(name = "padrao")
	private Boolean padrao;

	// OMIE envia "1/2/3" como texto
	@Column(name = "tipo", length = 10)
	private String tipo;

	// Mantidos como String (mesmo formato do payload)
	@Column(name = "data_inclusao", length = 10)
	private String dInc;

	@Column(name = "hora_inclusao", length = 8)
	private String hInc;

	@Column(name = "data_alteracao", length = 10)
	private String dAlt;

	@Column(name = "hora_alteracao", length = 8)
	private String hAlt;

	@Column(name = "usuario_inclusao", length = 50)
	private String uInc;

	@Column(name = "usuario_alteracao", length = 50)
	private String uAlt;

	/** Constrói a entity a partir do DTO e da empresa. */
	public LocalEstoqueEntity(LocalEstoqueDTO dto, EmpresaEntity empresa) {
		// PK: codigo_local_estoque + empresa_codigo
		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigoLocalEstoque()), empresa.getCodigo()));
		this.setEmpresa(empresa);

		this.atualizarDados(dto);
	}

	/** Atualiza os campos de negócio a partir do DTO. */
	public void atualizarDados(LocalEstoqueDTO dto) {
		this.setInativo(dto.getInativo());
		this.setNome(dto.getDescricao());
		this.setCodigoTextual(dto.getCodigo());

		this.setCodigoCliente(dto.getCodigoCliente());
		this.setDispConsumoOP(dto.getDispConsumoOP());
		this.setDispOrdemProducao(dto.getDispOrdemProducao());
		this.setDispRemessa(dto.getDispRemessa());
		this.setDispVenda(dto.getDispVenda());
		this.setPadrao(dto.getPadrao());
		this.setTipo(dto.getTipo());

		this.setDInc(dto.getDInc());
		this.setHInc(dto.getHInc());
		this.setDAlt(dto.getDAlt());
		this.setHAlt(dto.getHAlt());
		this.setUInc(dto.getUInc());
		this.setUAlt(dto.getUAlt());
	}
}

//// src/main/java/com/automatizacoes_java/pedidos_de_venda_e_remessa/domain/entidade/LocalEstoqueEntity.java
//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;
//
//import java.io.Serializable;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.LocalEstoqueDTO;
//
//import jakarta.persistence.AttributeOverride;
//import jakarta.persistence.AttributeOverrides;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
///**
// * PK composta: (empresa_codigo, codigo_local_estoque).
// * - id.codigo        -> coluna "codigo_local_estoque" (numérico, mas armazenado como String no ID)
// * - id.empresaCodigo -> coluna "empresa_codigo"
// * - codigo (Base)    -> código textual para o usuário (coluna "codigo", READ-ONLY pela Base)
// * - nome   (Base)    -> "descricao"
// */
//@Entity
//@Table(name = "locais_estoque")
//@Getter
//@Setter
//@NoArgsConstructor
//@ToString
//@AttributeOverrides({
//	@AttributeOverride(name = "id.codigo", column = @Column(name = "codigo_local_estoque", nullable = false, length = 50)),
//	@AttributeOverride(name = "id.empresaCodigo", column = @Column(name = "empresa_codigo", nullable = false)),
//	@AttributeOverride(name = "nome", column = @Column(name = "descricao"))
//})
//public class LocalEstoqueEntity extends BaseComposedEntity<String> implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	// espelho do identificador técnico da OMIE
//	@Column(name = "codigo_local_estoque", nullable = false)
//	private Long codigoLocalEstoque;
//
//	@Column(name = "codigo_cliente")
//	private Long codigoCliente;
//
//	@Column(name = "disp_consumo_op")
//	private Boolean dispConsumoOP;
//
//	@Column(name = "disp_ordem_producao")
//	private Boolean dispOrdemProducao;
//
//	@Column(name = "disp_remessa")
//	private Boolean dispRemessa;
//
//	@Column(name = "disp_venda")
//	private Boolean dispVenda;
//
//	@Column(name = "padrao")
//	private Boolean padrao;
//
//	@Column(name = "tipo", length = 10)
//	private String tipo;
//
//	// Mantidos como String (mesmo formato do payload)
//	@Column(name = "data_inclusao", length = 10)
//	private String dInc;
//
//	@Column(name = "hora_inclusao", length = 8)
//	private String hInc;
//
//	@Column(name = "data_alteracao", length = 10)
//	private String dAlt;
//
//	@Column(name = "hora_alteracao", length = 8)
//	private String hAlt;
//
//	@Column(name = "usuario_inclusao", length = 50)
//	private String uInc;
//
//	@Column(name = "usuario_alteracao", length = 50)
//	private String uAlt;
//
//	/** Constrói a entity a partir do DTO e da empresa (padrão CategoriaEntity). */
//	public LocalEstoqueEntity(LocalEstoqueDTO dto, EmpresaEntity empresa) {
//		// código textual (campo "codigo" da Base é read-only; usável para leitura/queries)
//		this.setCodigo(dto.getCodigo());
//
//		// ID composto usa o codigo_local_estoque + empresa
//		this.setCodigoLocalEstoque(dto.getCodigoLocalEstoque());
//		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigoLocalEstoque()), empresa.getCodigo()));
//		this.setEmpresa(empresa);
//
//		this.atualizarDados(dto);
//	}
//
//	public void atualizarDados(LocalEstoqueDTO dto) {
//		this.setInativo(dto.getInativo());
//		this.setNome(dto.getDescricao());
//
//		this.setCodigoCliente(dto.getCodigoCliente());
//		this.setDispConsumoOP(dto.getDispConsumoOP());
//		this.setDispOrdemProducao(dto.getDispOrdemProducao());
//		this.setDispRemessa(dto.getDispRemessa());
//		this.setDispVenda(dto.getDispVenda());
//		this.setPadrao(dto.getPadrao());
//		this.setTipo(dto.getTipo());
//
//		this.setDInc(dto.getDInc());
//		this.setHInc(dto.getHInc());
//		this.setDAlt(dto.getDAlt());
//		this.setHAlt(dto.getHAlt());
//		this.setUInc(dto.getUInc());
//		this.setUAlt(dto.getUAlt());
//	}
//}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;
//
//import java.io.Serializable;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseComposedEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.LocalEstoqueDTO;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
///**
// * ID composto: (empresa_codigo, codigo_local_estoque). - id.codigo -> armazena
// * o codigo_local_estoque (como String) para compor a PK - id.empresaCodigo ->
// * empresa - codigo (da Base) -> código textual para o usuário (ex.: "PADRAO",
// * "0001") - nome (da Base) -> descrição do local (dto.getDescricao())
// */
//@Entity
//@Table(name = "locais_estoque")
//@Getter
//@Setter
//@NoArgsConstructor
//@ToString
//public class LocalEstoqueEntity extends BaseComposedEntity<Long> implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	private String codigoTextual;
//	private Long codigoCliente;
//	private Boolean dispConsumoOP;
//	private Boolean dispOrdemProducao;
//	private Boolean dispRemessa;
//	private Boolean dispVenda;
//	private Boolean padrao;
//	private String tipo;
//	private String dInc;
//	private String hInc;
//	private String dAlt;
//	private String hAlt;
//	private String uInc;
//	private String uAlt;
//
//	/** Constrói a entity a partir do DTO e da empresa. */
//	public LocalEstoqueEntity(LocalEstoqueDTO dto, EmpresaEntity empresa) {
//		this.setCodigo(dto.getCodigoLocalEstoque());
//		this.setId(new EntidadeCompostaId(String.valueOf(dto.getCodigoLocalEstoque()), empresa.getCodigo()));
//		this.setEmpresa(empresa);
//
//		this.atualizarDados(dto);
//	}
//
//	/** Atualiza os campos de negócio a partir do DTO. */
//	public void atualizarDados(LocalEstoqueDTO dto) {
//		this.setInativo(dto.getInativo());
//		this.setNome(dto.getDescricao());
//		this.setCodigoTextual(dto.getCodigo());
//
//		this.setCodigoCliente(dto.getCodigoCliente());
//		this.setDispConsumoOP(dto.getDispConsumoOP());
//		this.setDispOrdemProducao(dto.getDispOrdemProducao());
//		this.setDispRemessa(dto.getDispRemessa());
//		this.setDispVenda(dto.getDispVenda());
//		this.setPadrao(dto.getPadrao());
//		this.setTipo(dto.getTipo());
//
//		this.setDInc(dto.getDInc());
//		this.setHInc(dto.getHInc());
//		this.setDAlt(dto.getDAlt());
//		this.setHAlt(dto.getHAlt());
//		this.setUInc(dto.getUInc());
//		this.setUAlt(dto.getUAlt());
//	}
//}

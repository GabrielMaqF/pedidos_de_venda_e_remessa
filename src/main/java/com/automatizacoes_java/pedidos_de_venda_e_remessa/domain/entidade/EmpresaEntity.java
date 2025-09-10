package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaSharepointDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class EmpresaEntity extends BaseEntity {

	@Id
	@Column(name = "codigo")
	private Long codigo;

	private String appKey;
	private String appSecret;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;

	public EmpresaEntity(EmpresaSharepointDTO dto) {
		this.setSharepointId(dto.getId());
		this.atualizarDados(dto);
	}

	public void atualizarDados(EmpresaSharepointDTO dto) {
		this.codigo = dto.getCodigoEmpresa();
		this.appKey = dto.getAppKey();
		this.appSecret = dto.getAppSecret();
		this.razaoSocial = dto.getRazaoSocial();
		this.nomeFantasia = dto.getNomeFantasia();
		this.cnpj = dto.getCnpj();
	}
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade;
//
//import java.io.Serializable;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.base.BaseEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.EmpresaDTO;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "empresas")
//@Getter
//@Setter
//@NoArgsConstructor
//public class EmpresaEntity extends BaseEntity implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@Column(name = "id")
//	private Long codigo;
//
//	private String appKey, appSecret, razaoSocial, nomeFantasia, cnpj;
//
//	public EmpresaEntity(EmpresaDTO dto) {
//		this.setSharepointId(dto.getId());
//		this.atualizarDados(dto);
//	}
//
//	public void atualizarDados(EmpresaDTO dto) {
//		this.codigo = dto.getCodigoEmpresa();
//		this.appKey = dto.getAppKey();
//		this.appSecret = dto.getAppSecret();
//		this.razaoSocial = dto.getRazaoSocial();
//		this.nomeFantasia = dto.getNomeFantasia();
//		this.cnpj = dto.getCnpj();
//	}
//}

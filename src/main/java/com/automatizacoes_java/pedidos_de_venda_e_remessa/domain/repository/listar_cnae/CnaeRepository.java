package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_cnae;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_cnae.CnaeEntity;

@Repository
public interface CnaeRepository extends JpaRepository<CnaeEntity, String> {
}
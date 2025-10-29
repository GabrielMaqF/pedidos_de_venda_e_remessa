package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.TipoFaturamentoContratoEntity;

@Repository
public interface TipoFaturamentoContratoRepository extends JpaRepository<TipoFaturamentoContratoEntity, String> {
}
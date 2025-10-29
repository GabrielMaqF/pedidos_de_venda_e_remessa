package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContratoServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;

@Repository
public interface ContratoServicoRepository extends JpaRepository<ContratoServicoEntity, EntidadeCompostaId> {
}
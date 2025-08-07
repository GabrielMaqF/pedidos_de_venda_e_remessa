package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_contrato_servico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_contrato_servico.ContratoServicoEntity;

@Repository
public interface ContratoServicoRepository extends JpaRepository<ContratoServicoEntity, EntidadeCompostaId> {
}
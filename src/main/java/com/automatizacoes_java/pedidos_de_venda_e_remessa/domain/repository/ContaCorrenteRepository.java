package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository;

import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;

@Repository
public interface ContaCorrenteRepository extends BaseRepository<ContaCorrenteEntity, EntidadeCompostaId, Long> {

}

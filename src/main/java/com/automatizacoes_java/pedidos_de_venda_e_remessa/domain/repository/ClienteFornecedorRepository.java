package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository;

import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.cliente_fornecedor.ClienteFornecedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;

@Repository
public interface ClienteFornecedorRepository extends BaseRepository<ClienteFornecedorEntity, EntidadeCompostaId, Long> {

}

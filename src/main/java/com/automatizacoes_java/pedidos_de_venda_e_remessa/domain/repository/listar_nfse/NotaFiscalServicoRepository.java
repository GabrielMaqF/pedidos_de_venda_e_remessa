package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_nfse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_nfse.NotaFiscalServicoEntity;

@Repository
public interface NotaFiscalServicoRepository extends JpaRepository<NotaFiscalServicoEntity, EntidadeCompostaId> {
}
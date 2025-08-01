package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.relatorio;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.OrdemServicoParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoParcelaRepository extends JpaRepository<OrdemServicoParcelaEntity, Long> {
}
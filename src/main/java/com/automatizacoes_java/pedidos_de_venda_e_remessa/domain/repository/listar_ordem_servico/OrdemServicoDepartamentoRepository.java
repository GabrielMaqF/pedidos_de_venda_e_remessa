package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.listar_ordem_servico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.listar_ordem_servico.OrdemServicoDepartamentoEntity;

@Repository
public interface OrdemServicoDepartamentoRepository extends JpaRepository<OrdemServicoDepartamentoEntity, Long> {
}
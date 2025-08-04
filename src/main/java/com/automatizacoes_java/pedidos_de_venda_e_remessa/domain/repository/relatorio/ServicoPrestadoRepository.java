package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.relatorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.ServicoPrestadoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.relatorio.listar_ordem_servico.ServicoPrestadoEntity;

@Repository
public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestadoEntity, ServicoPrestadoId> {
}

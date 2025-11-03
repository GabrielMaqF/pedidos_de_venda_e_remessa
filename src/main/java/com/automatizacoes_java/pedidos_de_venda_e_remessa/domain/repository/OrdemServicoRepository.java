package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.OrdemServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.OrdemServicoEntity.OrdemServicoDepartamentoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.OrdemServicoEntity.OrdemServicoParcelaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.OrdemServicoEntity.ServicoPrestadoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.ServicoPrestadoId;

@Repository
public interface OrdemServicoRepository extends BaseRepository<OrdemServicoEntity, OrdemServicoId, Long> {

	@Repository
	public interface OrdemServicoDepartamentoRepository extends JpaRepository<OrdemServicoDepartamentoEntity, Long> {
	}

	@Repository
	public interface OrdemServicoParcelaRepository extends JpaRepository<OrdemServicoParcelaEntity, Long> {
	}

	@Repository
	public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestadoEntity, ServicoPrestadoId> {
	}

}
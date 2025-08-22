package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;

@Repository
public interface EmpresaRepository extends BaseRepository<EmpresaEntity, Long, Long> {

	List<EmpresaEntity> findByNomeFantasiaIn(Set<String> nomes);

	Optional<EmpresaEntity> findByAppKey(String appKey);
}

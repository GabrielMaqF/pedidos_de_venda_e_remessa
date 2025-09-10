package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ProjetoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.projeto.ProjetoOmieDTO;

import jakarta.transaction.Transactional;

@Service
public class ProjetoService extends BaseService<ProjetoEntity, EntidadeCompostaId, Long> {
	@Autowired
	ProjetoRepository repository;

	@Transactional
	public ProjetoEntity criarOuAtualizarPorOmie(ProjetoOmieDTO dto, EmpresaEntity empresa) {
		EntidadeCompostaId id = new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo());

		// Procura o cliente, se não existir, cria um novo
		ProjetoEntity entidade = repository.findById(id).orElse(new ProjetoEntity(dto, empresa));

		// Atualiza os dados com as informações do DTO
		entidade.atualizarDados(dto);

		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}
}

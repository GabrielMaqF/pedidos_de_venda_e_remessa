package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.VendedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.VendedorRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.vendedor.VendedorOmieDTO;

import jakarta.transaction.Transactional;

@Service
public class VendedorService extends BaseService<VendedorEntity, EntidadeCompostaId, Long> {

	/**
	 * O Spring injetará o ClienteRepository aqui automaticamente. Nós então o
	 * passamos para o construtor da classe mãe (BaseService) para que os métodos
	 * genéricos (findAll, findById) possam usá-lo.
	 *
	 * @param clienteRepository O repositório específico para ClienteEntity.
	 */
	@Autowired
	VendedorRepository repository;

	@Transactional
	public VendedorEntity criarOuAtualizarPorOmie(VendedorOmieDTO dto, EmpresaEntity empresa) {
		EntidadeCompostaId id = new EntidadeCompostaId(String.valueOf(dto.getCodigo()), empresa.getCodigo());

		// Procura o cliente, se não existir, cria um novo
		VendedorEntity entidade = repository.findById(id).orElse(new VendedorEntity(dto, empresa));

		// Atualiza os dados com as informações do DTO
		entidade.atualizarDados(dto);

		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}
}

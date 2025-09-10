package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.cliente_fornecedor.ClienteFornecedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ClienteFornecedorRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.ClienteOmieDTO;

import jakarta.transaction.Transactional;

@Service
public class ClienteFornecedorService extends BaseService<ClienteFornecedorEntity, EntidadeCompostaId, Long> {

	/**
	 * O Spring injetará o ClienteRepository aqui automaticamente. Nós então o
	 * passamos para o construtor da classe mãe (BaseService) para que os métodos
	 * genéricos (findAll, findById) possam usá-lo.
	 *
	 * @param clienteRepository O repositório específico para ClienteEntity.
	 */
	@Autowired
	ClienteFornecedorRepository repository;

	@Transactional
	public ClienteFornecedorEntity criarOuAtualizarPorOmie(ClienteOmieDTO dto, EmpresaEntity empresa) {
		EntidadeCompostaId id = new EntidadeCompostaId(String.valueOf(dto.getCodigoClienteOmie()), empresa.getCodigo());

		// Procura o cliente, se não existir, cria um novo
		ClienteFornecedorEntity entidade = repository.findById(id).orElse(new ClienteFornecedorEntity(dto, empresa));

		// Atualiza os dados com as informações do DTO
		entidade.atualizarDados(dto);

		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}
}

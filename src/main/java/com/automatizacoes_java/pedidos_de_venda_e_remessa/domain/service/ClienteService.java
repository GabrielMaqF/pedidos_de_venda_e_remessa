package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.ClienteRepository;

@Service
public class ClienteService extends BaseService<ClienteEntity, EntidadeCompostaId, Long> {

	/**
	 * O Spring injetará o ClienteRepository aqui automaticamente. Nós então o
	 * passamos para o construtor da classe mãe (BaseService) para que os métodos
	 * genéricos (findAll, findById) possam usá-lo.
	 *
	 * @param clienteRepository O repositório específico para ClienteEntity.
	 */
	@Autowired
	ClienteRepository repository;
//	public ClienteService(ClienteRepository repository) {
//		super(repository);
//	}

	// A partir daqui, você pode adicionar métodos que são ESPECÍFICOS para
	// Clientes.
	// Por exemplo:
	/*
	 * public List<ClienteEntity> encontrarClientesPorEstado(String estado) { //
	 * Para isso, você precisaria adicionar o método findByEstado(estado) // na sua
	 * interface ClienteRepository. // return
	 * clienteRepository.findByEstado(estado); }
	 */
}

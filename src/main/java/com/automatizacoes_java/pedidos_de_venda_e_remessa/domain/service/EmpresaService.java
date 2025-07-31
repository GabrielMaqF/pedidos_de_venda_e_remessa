package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;

@Service
public class EmpresaService extends BaseService<EmpresaEntity, Long, Long> {

	/**
	 * O Spring injetará o ClienteRepository aqui automaticamente. Nós então o
	 * passamos para o construtor da classe mãe (BaseService) para que os métodos
	 * genéricos (findAll, findById) possam usá-lo.
	 *
	 * @param clienteRepository O repositório específico para ClienteEntity.
	 */
	@Autowired
	EmpresaRepository repository;
//	public EmpresaService(EmpresaRepository repository) {
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

package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.BaseRepository;

/**
 * Uma classe de serviço base e genérica que fornece operações CRUD comuns.
 * Utiliza injeção de dependência via construtor para receber o repositório
 * específico.
 *
 * @param <T>  O tipo da Entidade (ex: ClienteEntity)
 * @param <ID> O tipo da Chave Primária da Entidade (ex: String)
 * @param <C>  O tipo do campo 'codigo' da Entidade (ex: Long)
 */
public abstract class BaseService<T, ID, C> {

	// O repositório agora é do nosso tipo BaseRepository, que contém o
	// findByCodigo.
	@Autowired
	BaseRepository<T, ID, C> repository;

	/**
	 * Construtor que força as classes filhas a fornecerem seu próprio
	 * BaseRepository.
	 *
	 * @param repository O repositório específico para a entidade T.
	 */
//	public BaseService(BaseRepository<T, ID, C> repository) {
//		this.repository = repository;
//	}

	/**
	 * Retorna todas as entidades do tipo T.
	 *
	 * @return uma Lista de entidades.
	 */
	public List<T> findAll() {
		return repository.findAll();
	}

	/**
	 * Busca uma entidade pelo seu ID (Chave Primária).
	 *
	 * @param id A chave primária da entidade.
	 * @return um Optional contendo a entidade se encontrada.
	 */
	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}

	public Optional<T> findByCodigo(C codigo) {
		return repository.findByCodigo(codigo);
	}

	public T save(T obj) {
		return repository.save(obj);
	}

}

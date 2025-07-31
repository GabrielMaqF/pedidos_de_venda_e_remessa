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

	/**
	 * Busca uma entidade pelo seu campo 'codigo'.
	 *
	 * @param codigo O código da entidade a ser buscado.
	 * @return um Optional contendo a entidade se encontrada.
	 */
	public Optional<T> findByCodigo(C codigo) {
		return repository.findByCodigo(codigo);
	}

}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
///**
// * Uma classe de serviço base e genérica que fornece operações CRUD comuns.
// * Utiliza injeção de dependência via construtor para receber o repositório
// * específico.
// *
// * @param <T>  O tipo da Entidade (ex: ClienteEntity)
// * @param <ID> O tipo da Chave Primária da Entidade (ex: String)
// */
//public abstract class BaseService<T, ID> {
//
//	// O repositório genérico. Será a instância específica (ex: ClienteRepository)
//	// fornecida pela classe filha.
//	private final JpaRepository<T, ID> repository;
//
//	/**
//	 * Construtor que força as classes filhas a fornecerem seu próprio repositório.
//	 *
//	 * @param repository O repositório específico para a entidade T.
//	 */
//	public BaseService(JpaRepository<T, ID> repository) {
//		this.repository = repository;
//	}
//
//	/**
//	 * Retorna todas as entidades do tipo T.
//	 *
//	 * @return uma Lista de entidades.
//	 */
//	public List<T> findAll() {
//		return repository.findAll();
//	}
//
//	/**
//	 * Busca uma entidade pelo seu ID.
//	 *
//	 * @param id A chave primária da entidade.
//	 * @return um Optional contendo a entidade se encontrada, ou vazio caso
//	 *         contrário.
//	 */
//	public Optional<T> findById(ID id) {
//		return repository.findById(id);
//	}
//
//	// Você pode adicionar outros métodos comuns aqui no futuro, por exemplo:
//	/*
//	 * public T save(T entity) { return repository.save(entity); }
//	 *
//	 * public void deleteById(ID id) { repository.deleteById(id); }
//	 */
//}

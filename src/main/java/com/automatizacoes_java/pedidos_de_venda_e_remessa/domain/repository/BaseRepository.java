package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface de repositório base para fornecer métodos comuns a todas as nossas
 * entidades que possuem um campo 'codigo'.
 *
 * @param <T>  O tipo da Entidade (ex: ClienteEntity)
 * @param <ID> O tipo da Chave Primária da Entidade (ex: String)
 * @param <C>  O tipo do campo 'codigo' da Entidade (ex: Long ou String)
 */
@NoRepositoryBean // Essencial: Diz ao Spring para não criar uma implementação para esta
					// interface.
public interface BaseRepository<T, ID, C> extends JpaRepository<T, ID> {

	/**
	 * Busca uma entidade pelo seu campo 'codigo'. O Spring Data JPA criará a
	 * implementação automaticamente baseado no nome do método.
	 *
	 * @param codigo O código a ser buscado.
	 * @return um Optional contendo a entidade se encontrada.
	 */
	Optional<T> findByCodigo(C codigo);
	
	Optional<T> findById(ID id);

}

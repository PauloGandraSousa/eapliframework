/**
 *
 */
package eapli.framework.infrastructure.repositories.impl.springdata;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * base interface for spring Data repositories.
 *
 * we decided to use Repository instead of CrudRepository to have findById
 * return an Optional.
 *
 * @author Paulo Gandra Sousa
 *
 */
@NoRepositoryBean
public interface SpringDataBaseRepository<T, K extends Serializable> extends Repository<T, K> {

    long count();

    <S extends T> S save(S entity);

    void delete(T entity);

    void deleteById(K id);

    Optional<T> findById(K id);

    Iterable<T> findAll();
}

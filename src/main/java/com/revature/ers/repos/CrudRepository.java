package com.revature.ers.repos;

import java.util.Optional;
import java.util.Set;

/**
 * CREATE READ UPDATE DELETE Repository.
 * @param <T>
 */
public interface CrudRepository<T> {

    /**
     * CREATE
     * @param t
     * @return
     */
    Optional<T> save(T t);

    /**
     * READ
     * @return
     */
    Set<T> findAll();
//    <T> findById(Integer id);

    /**
     * UPDATE
     * @param t
     * @return
     */
    boolean update(T t);

    /**
     * DELETE
     * @param id
     * @return
     */
    boolean deleteById(Integer id);


}

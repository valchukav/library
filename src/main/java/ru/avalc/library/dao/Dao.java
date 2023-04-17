package ru.avalc.library.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Alexei Valchuk, 11.04.2023, email: a.valchukav@gmail.com
 */

public interface Dao<T> {

    List<T> getAll();

    List<T> getAll(Sort sort);

    Page<T> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection);

    T get(long id);

    T save(T obj);

    void delete(T obj);

    List<T> search(String... searchString);

    Page<T> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString);
}

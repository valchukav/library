package ru.avalc.library.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avalc.library.dao.AuthorDao;
import ru.avalc.library.domain.Author;
import ru.avalc.library.repository.AuthorRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

@Service
@Transactional
public class AuthorService implements AuthorDao, Serializable {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> getAll(Sort sort) {
        return authorRepository.findAll(sort);
    }

    @Override
    public Page<Author> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return authorRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }

    @Override
    public Author get(long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author save(Author obj) {
        return authorRepository.save(obj);
    }

    @Override
    public void delete(Author obj) {
        authorRepository.delete(obj);
    }

    @Override
    public List<Author> search(String... searchString) {
        return authorRepository.findByFioContainingIgnoreCaseOrderByFio(searchString[0]);
    }

    @Override
    public Page<Author> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return authorRepository.findByFioContainingIgnoreCaseOrderByFio(searchString[0],
                PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }
}

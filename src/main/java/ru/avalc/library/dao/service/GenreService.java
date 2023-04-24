package ru.avalc.library.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avalc.library.dao.GenreDao;
import ru.avalc.library.domain.Genre;
import ru.avalc.library.repository.GenreRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

@Service
@Transactional
public class GenreService implements GenreDao, Serializable {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> getAll(Sort sort) {
        return genreRepository.findAll(sort);
    }

    @Override
    public Page<Genre> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return genreRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }

    @Override
    public Genre get(long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public Genre save(Genre obj) {
        return genreRepository.save(obj);
    }

    @Override
    public void delete(Genre obj) {
        genreRepository.delete(obj);
    }

    @Override
    public List<Genre> search(String... searchString) {
        return genreRepository.findByNameContainingIgnoreCaseOrderByName(searchString[0]);
    }

    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return genreRepository.findByNameContainingIgnoreCaseOrderByName(searchString[0],
                PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }
}

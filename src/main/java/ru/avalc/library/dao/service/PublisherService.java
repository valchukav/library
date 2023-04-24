package ru.avalc.library.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avalc.library.dao.PublisherDao;
import ru.avalc.library.domain.Publisher;
import ru.avalc.library.repository.PublisherRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

@Service
@Transactional
public class PublisherService implements PublisherDao, Serializable {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    @Override
    public List<Publisher> getAll(Sort sort) {
        return publisherRepository.findAll(sort);
    }

    @Override
    public Page<Publisher> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return publisherRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }

    @Override
    public Publisher get(long id) {
        return publisherRepository.findById(id).orElse(null);
    }

    @Override
    public Publisher save(Publisher obj) {
        return publisherRepository.save(obj);
    }

    @Override
    public void delete(Publisher obj) {
        publisherRepository.delete(obj);
    }

    @Override
    public List<Publisher> search(String... searchString) {
        return publisherRepository.findByNameContainingIgnoreCaseOrderByName(searchString[0]);
    }

    @Override
    public Page<Publisher> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return publisherRepository.findByNameContainingIgnoreCaseOrderByName(searchString[0],
                PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }
}

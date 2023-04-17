package ru.avalc.library.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avalc.library.dao.BookDao;
import ru.avalc.library.domain.Book;
import ru.avalc.library.repository.BookRepository;

import java.util.List;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

@Service
@Transactional
public class BookService implements BookDao {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findTopBooks(int limit) {
        return bookRepository.findTopBooks(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "viewCount")));
    }

    @Override
    public byte[] getContent(long id) {
        return bookRepository.getContent(id);
    }

    @Override
    public Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, Long genreId) {
        return bookRepository.findByGenre(genreId, PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }

    @Override
    public void updateViewCount(long viewCount, long id) {
        bookRepository.updateViewCount(viewCount, id);
    }

    @Override
    public void updateRating(long totalRating, long totalVoteCount, int avgRating, long id) {
        bookRepository.updateRating(totalRating, totalVoteCount, avgRating, id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    @Override
    public Page<Book> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return bookRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }

    @Override
    public Book get(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book save(Book obj) {
        bookRepository.save(obj);
        if (obj.getContent() != null) {
            bookRepository.updateContent(obj.getContent(), obj.getId());
        }
        return obj;
    }

    @Override
    public void delete(Book obj) {
        bookRepository.delete(obj);
    }

    @Override
    public List<Book> search(String... searchString) {
        return null;
    }

    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(searchString[0], searchString[0],
                PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(sortDirection, sortField))));
    }
}

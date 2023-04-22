package ru.avalc.library.dao.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.domain.Author;
import ru.avalc.library.domain.Book;
import ru.avalc.library.domain.Genre;
import ru.avalc.library.domain.Publisher;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexei Valchuk, 13.04.2023, email: a.valchukav@gmail.com
 */

class BookServiceTest extends AbstractServiceTest<BookService>{

    @Autowired
    public BookServiceTest(BookService service) {
        super(service);
    }

    @Test
    void findTopBooks() {
        int limit = 3;
        List<Book> books = service.findTopBooks(limit);

        assertNotNull(books);
        assertEquals(limit, books.size());
        assertEquals(1107, books.get(0).getId());
    }

    @Test
    @Disabled
    void getContent() {

    }

    @Test
    void findByGenre() {
        int pageSize = 3;
        Page<Book> books = service.findByGenre(0, pageSize, "name", Sort.Direction.ASC, 124L);

        assertNotNull(books);
        assertEquals(pageSize, books.getNumberOfElements());
        assertEquals("Азазель", books.stream().findFirst().get().getName());
    }

    @Test
    void updateViewCount() {
        long id = 1101;
        int initialViewCount = 10;
        int updatedViewCount = 1120;
        service.updateViewCount(updatedViewCount, id);

        assertEquals(updatedViewCount, service.get(id).getViewCount());

        service.updateViewCount(initialViewCount, id);

        assertEquals(initialViewCount, service.get(id).getViewCount());
    }

    @Test
    void updateRating() {
        long id = 1107;
        long totalRating = 0;
        long totalVoteCount = 0;
        int avgRating = 0;

        long updatedTotalRating = 1;
        long updatedTotalVoteCount = 2;
        int updatedAvgRating = 3;

        service.updateRating(updatedTotalRating, updatedTotalVoteCount, updatedAvgRating, id);

        Book updatedBook = service.get(id);

        assertEquals(updatedTotalRating, updatedBook.getTotalRating());
        assertEquals(updatedTotalVoteCount, updatedBook.getTotalVoteCount());
        assertEquals(updatedAvgRating, updatedBook.getAvgRating());

        service.updateRating(totalRating, totalVoteCount, avgRating, id);

        Book returnedBook = service.get(id);

        assertEquals(totalRating, returnedBook.getTotalRating());
        assertEquals(totalVoteCount, returnedBook.getTotalVoteCount());
        assertEquals(avgRating, returnedBook.getAvgRating());
    }

    @Test
    void getAll() {
        List<Book> books = service.getAll();

        assertNotNull(books);
        assertEquals(8, books.size());
    }

    @Test
    void getAllSort() {
        List<Book> all = service.getAll(Sort.by(Sort.Direction.ASC, "name"));

        assertNotNull(all);
        assertEquals(8, all.size());
        assertEquals("Азазель", all.get(0).getName());
    }

    @Test
    void getAllSortAndPageable() {
        int pageSize = 8;
        Page<Book> all = service.getAll(0, pageSize, "name", Sort.Direction.DESC);

        assertNotNull(all);
        assertEquals(pageSize, all.getNumberOfElements());
        assertEquals("Собачья смерть", all.get().findFirst().get().getName());
    }

    @Override
    @Test
    void get() {
        Book book = service.get(1105);
        assertEquals("Азазель", book.getName());
    }

    @Test
    void saveAndDelete() {
        Book book = new Book("Name", 121, "11",
                new Genre(120L, "NEW", Collections.emptyList()),
                new Author(101L, "NEW", new Date(), Collections.emptyList()),
                new Publisher(150L, "NEW", Collections.emptyList()),
                2020);
        service.save(book);

        try {
            assertEquals(book.getName(), service.get(book.getId()).getName());
        } finally {
            service.delete(book);
            assertNull(service.get(book.getId()));
        }
    }

    @Test
    void searchWithSortAndPaging() {
        int pageSize = 3;
        Page<Book> books = service.search(0, pageSize, "name", Sort.Direction.DESC, "а");

        assertNotNull(books);
        assertTrue(books.getNumberOfElements() <= pageSize);
        assertEquals(8, books.getTotalElements());
    }
}
package ru.avalc.library.dao.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.domain.Author;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

class AuthorServiceTest extends AbstractServiceTest<AuthorService> {

    @Autowired
    public AuthorServiceTest(AuthorService service) {
        super(service);
    }

    @Test
    void getAll() {
        List<Author> all = service.getAll();
        assertNotNull(all);
        assertEquals(19, all.size());
    }

    @Test
    void getAllSort() {
        List<Author> all = service.getAll(Sort.by(Sort.Direction.DESC, "fio"));

        assertNotNull(all);
        assertEquals(19, all.size());
        assertEquals("Эрих Мария Ремарк", all.get(0).getFio());
    }

    @Test
    void getAllSortAndPageable() {
        int pageSize = 10;
        Page<Author> all = service.getAll(0, pageSize, "fio", Sort.Direction.ASC);

        assertNotNull(all);
        assertEquals(pageSize, all.getNumberOfElements());
        assertEquals("Борис Акунин", all.get().findFirst().get().getFio());
    }

    @Override
    @Test
    void get() {
        Author author = service.get(101);
        assertEquals("Пауло Коэльо", author.getFio());
    }

    @Test
    void saveAndDelete() {
        Author author = new Author("FIO", new Date());
        service.save(author);

        try {
            assertEquals(author.getFio(), service.get(author.getId()).getFio());
        } finally {
            service.delete(author);
            assertNull(service.get(author.getId()));
        }
    }

    @Test
    void search() {
        List<Author> authors = service.search("дж");

        assertNotNull(authors);
        assertEquals(2, authors.size());
    }

    @Test
    void searchWithSortAndPaging() {
        int pageSize = 10;
        Page<Author> authors = service.search(1, pageSize, "fio", Sort.Direction.DESC, "а");

        assertNotNull(authors);
        assertTrue(authors.getNumberOfElements() <= pageSize);
        assertEquals(17, authors.getTotalElements());
    }
}
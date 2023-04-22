package ru.avalc.library.dao.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexei Valchuk, 17.04.2023, email: a.valchukav@gmail.com
 */

class GenreServiceTest extends AbstractServiceTest<GenreService>{

    @Autowired
    public GenreServiceTest(GenreService service) {
        super(service);
    }

    @Test
    void getAll() {
        List<Genre> all = service.getAll();
        assertNotNull(all);
        assertEquals(21, all.size());
    }

    @Test
    void getAllSort() {
        List<Genre> all = service.getAll(Sort.by(Sort.Direction.DESC, "name"));

        assertNotNull(all);
        assertEquals(21, all.size());
        assertEquals("Юмор", all.get(0).getName());
    }

    @Test
    void getAllSortAndPageable() {
        int pageSize = 10;
        Page<Genre> all = service.getAll(0, pageSize, "name", Sort.Direction.ASC);

        assertNotNull(all);
        assertEquals(pageSize, all.getNumberOfElements());
        assertEquals("Бизнес", all.get().findFirst().get().getName());
    }

    @Override
    @Test
    void get() {
        Genre genre = service.get(121);
        assertEquals("Мистика", genre.getName());
    }

    @Test
    void saveAndDelete() {
        Genre genre = new Genre("NAME");
        service.save(genre);

        try {
            assertEquals(genre.getName(), service.get(genre.getId()).getName());
        } finally {
            service.delete(genre);
            assertNull(service.get(genre.getId()));
        }
    }

    @Test
    void search() {
        List<Genre> genres = service.search("ск");

        assertNotNull(genres);
        assertEquals(3, genres.size());
    }

    @Test
    void searchWithSortAndPaging() {
        int pageSize = 10;
        Page<Genre> genres = service.search(1, pageSize, "name", Sort.Direction.DESC, " ");

        assertNotNull(genres);
        assertTrue(genres.getNumberOfElements() <= pageSize);
        assertEquals(1, genres.getTotalElements());
    }
}
package ru.avalc.library.dao.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.domain.Publisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexei Valchuk, 17.04.2023, email: a.valchukav@gmail.com
 */

class PublisherServiceTest extends AbstractServiceTest<PublisherService> {

    @Autowired
    public PublisherServiceTest(PublisherService service) {
        super(service);
    }

    @Test
    void getAll() {
        List<Publisher> all = service.getAll();
        assertNotNull(all);
        assertEquals(4, all.size());
    }

    @Test
    void getAllSort() {
        List<Publisher> all = service.getAll(Sort.by(Sort.Direction.DESC, "name"));

        assertNotNull(all);
        assertEquals(4, all.size());
        assertEquals("Эксмо", all.get(0).getName());
    }

    @Test
    void getAllSortAndPageable() {
        int pageSize = 3;
        Page<Publisher> all = service.getAll(0, pageSize, "name", Sort.Direction.ASC);

        assertNotNull(all);
        assertEquals(pageSize, all.getNumberOfElements());
        assertEquals("Весь", all.get().findFirst().get().getName());
    }

    @Override
    @Test
    void get() {
        Publisher publisher = service.get(151);
        assertEquals("Эксмо", publisher.getName());
    }

    @Test
    void saveAndDelete() {
        Publisher publisher = new Publisher("NAME");
        service.save(publisher);

        try {
            assertEquals(publisher.getName(), service.get(publisher.getId()).getName());
        } finally {
            service.delete(publisher);
            assertNull(service.get(publisher.getId()));
        }
    }

    @Test
    void search() {
        List<Publisher> publishers = service.search("о");

        assertNotNull(publishers);
        assertEquals(2, publishers.size());
    }

    @Test
    void searchWithSortAndPaging() {
        int pageSize = 3;
        Page<Publisher> publishers = service.search(1, pageSize, "name", Sort.Direction.DESC, " ");

        assertNotNull(publishers);
        assertTrue(publishers.getNumberOfElements() <= pageSize);
        assertEquals(1, publishers.getTotalElements());
    }
}
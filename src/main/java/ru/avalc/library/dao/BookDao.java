package ru.avalc.library.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.domain.Book;

import java.util.List;

/**
 * @author Alexei Valchuk, 11.04.2023, email: a.valchukav@gmail.com
 */

public interface BookDao extends Dao<Book> {

    List<Book> findTopBooks(int limit);

    byte[] getContent(long id);

    Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, Long genreId);

    void updateViewCount(long viewCount, long id);

    void updateRating(long totalRating, long totalVoteCount, int avgRating, long id);
}

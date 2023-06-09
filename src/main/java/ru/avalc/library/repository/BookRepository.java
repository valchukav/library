package ru.avalc.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avalc.library.domain.Book;

import java.util.List;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(String name, String fio, Pageable pageable);

    @Query("select new ru.avalc.library.domain.Book(b.id, b.imagePath) from Book b")
    List<Book> findTopBooks(Pageable pageable);

    @Query("select new ru.avalc.library.domain.Book(b.id, b.name, b.contentPath, b.pageCount, b.isbn, b.genre, b.author, b.publisher, " +
            "b.publishYear, b.imagePath, b.descr, b.viewCount, b.totalRating, b.totalVoteCount, b.avgRating) from Book b where b.genre.id=:genreId")
    Page<Book> findByGenre(@Param("genreId") long genreId, Pageable pageable);

    @Modifying
    @Query("update Book b set b.viewCount=:viewCount where b.id=:id")
    void updateViewCount(@Param("viewCount") long viewCount, @Param("id") long id);

    @Modifying
    @Query("update Book b set b.totalRating=:totalRating, b.totalVoteCount=:totalVoteCount, b.avgRating=:avgRating where b.id=:id")
    void updateRating(@Param("totalRating") long totalRating, @Param("totalVoteCount") long totalVoteCount,
                      @Param("avgRating") int avgRating, @Param("id") long id);
}

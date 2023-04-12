package ru.avalc.library.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Alexei Valchuk, 11.04.2023, email: a.valchukav@gmail.com
 */

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@ToString(includeFieldNames = false)
public class Book {

    public Book(Long id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    public Book(Long id, @NonNull String name, @NonNull Integer pageCount, @NonNull String isbn,
                @NonNull Genre genre, @NonNull Author author, @NonNull Publisher publisher,
                @NonNull Integer publishYear, byte[] image, String descr, long viewCount, long totalRating,
                long totalVoteCount, int avgRating) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.image = image;
        this.descr = descr;
        this.viewCount = viewCount;
        this.totalRating = totalRating;
        this.totalVoteCount = totalVoteCount;
        this.avgRating = avgRating;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Lob
    @Column(updatable = false)
    @ToString.Exclude
    private byte[] content;

    @Column(name = "page_count", nullable = false)
    @NonNull
    private Integer pageCount;

    @Column(nullable = false)
    @NonNull
    private String isbn;

    @ManyToOne
    @JoinColumn
    @NonNull
    @ToString.Exclude
    private Genre genre;

    @ManyToOne
    @JoinColumn
    @NonNull
    @ToString.Exclude
    private Author author;

    @ManyToOne
    @JoinColumn
    @NonNull
    @ToString.Exclude
    private Publisher publisher;

    @Column(name = "publish_year", nullable = false)
    @NonNull
    private Integer publishYear;

    @ToString.Exclude
    private byte[] image;

    @ToString.Exclude
    private String descr;

    @Column(name = "view_count")
    private long viewCount;

    @Column(name = "total_rating")
    private long totalRating;

    @Column(name = "total_vote_count")
    private long totalVoteCount;

    @Column(name = "avg_rating")
    private int avgRating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

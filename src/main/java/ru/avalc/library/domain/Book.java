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

    public Book(Long id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(name = "content_path")
    private String contentPath;

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

    @Column(name = "image_path")
    private String imagePath;

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

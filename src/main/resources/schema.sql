DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS publisher CASCADE;
DROP TABLE IF EXISTS vote CASCADE;
DROP TABLE IF EXISTS book CASCADE;

CREATE TABLE author
(
    id       serial PRIMARY KEY,
    fio      varchar(256) NOT NULL,
    birthday date         NOT NULL
);

CREATE TABLE genre
(
    id   serial PRIMARY KEY,
    name varchar(128) NOT NULL
);

CREATE TABLE publisher
(
    id   serial PRIMARY KEY,
    name varchar(128) NOT NULL
);

CREATE TABLE book
(
    id               serial PRIMARY KEY,
    name             varchar(256)       NOT NULL,
    content_path          varchar(512),
    page_count       int                NOT NULL,
    isbn             varchar(64) UNIQUE NOT NULL,
    genre_id         bigint             NOT NULL,
    author_id        bigint             NOT NULL,
    publish_year     int                NOT NULL,
    publisher_id     bigint             NOT NULL,
    image_path       varchar(512) DEFAULT 'images/covers/no-cover.jpg',
    avg_rating       int          DEFAULT 0,
    total_vote_count bigint       DEFAULT 0,
    total_rating     bigint       DEFAULT 0,
    view_count       bigint       DEFAULT 0,
    descr            text         DEFAULT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author (id) ON UPDATE CASCADE,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genre (id) ON UPDATE CASCADE,
    CONSTRAINT fk_publisher FOREIGN KEY (publisher_id) REFERENCES publisher (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE vote
(
    id       serial PRIMARY KEY,
    value    int DEFAULT 0,
    book_id  bigint       NOT NULL,
    username varchar(128) NOT NULL,
    CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE ON UPDATE NO ACTION
);
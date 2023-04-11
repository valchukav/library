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
    content          bytea,
    page_count       int                NOT NULL,
    isbn             varchar(64) UNIQUE NOT NULL,
    genre_id         bigint             NOT NULL,
    author_id        bigint             NOT NULL,
    publish_year     int                NOT NULL,
    publisher_id     bigint             NOT NULL,
    image            bytea,
    avg_rating       int    DEFAULT 0,
    total_vote_count bigint DEFAULT 0,
    total_rating     bigint DEFAULT 0,
    view_count       bigint DEFAULT 0,
    descr            text   DEFAULT NULL,
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

INSERT INTO author (fio, birthday)
VALUES ('Пауло Коэльо', '1947-02-11'),
       ('Дарья Донцова', '1952-06-07'),
       ('Джоан Роулинг', '1965-07-31'),
       ('Чайлд Ли', '1954-10-29'),
       ('Татьяна Устинова', '1968-04-21'),
       ('Эрих Мария Ремарк', '1898-06-22'),
       ('Владимир Набоков', '1899-04-22'),
       ('Памела Трэверс', '1899-08-09'),
       ('Тестовый автор', '1786-02-03'),
       ('Михаил Юрьевич Лермонтов', '1814-10-16'),
       ('Пушкин Александр Сергеевич', '1799-06-06'),
       ('Борис Акунин', '1863-06-25'),
       ('Теофиль Готье', '1865-03-20'),
       ('Дэвид Вайз', '1956-03-01'),
       ('Джефри Янг', '1962-04-02'),
       ('Чарльз Диккенс', '1867-03-06'),
       ('Николай Лесков', '1895-04-04'),
       ('Новый автор', '1111-11-11'),
       ('Еще автор', '1111-11-11');

INSERT INTO genre (name)
VALUES ('Детектив'),
       ('Мистика'),
       ('Юмор'),
       ('Исторический'),
       ('Роман'),
       ('Сказка'),
       ('Приключения'),
       ('Бизнес'),
       ('Боевик'),
       ('Религия'),
       ('Компьютеры'),
       ('Семья'),
       ('Психология'),
       ('Биография'),
       ('Вестерн'),
       ('Драма'),
       ('Фэнтези'),
       ('Мемуары'),
       ('Рассказ'),
       ('Пьеса'),
       ('Новый жанр');

INSERT INTO publisher (name)
VALUES ('Весь'),
       ('Эксмо'),
       ('Литер'),
       ('Тот кого нельзя издавать');

INSERT INTO book (name, page_count, isbn, genre_id, author_id, publish_year, publisher_id, descr)
VALUES ('Капитанская дочка', 233, '978-5-91982-527-2', 5, 11, 1836, 1,
        'Книга ориентирована на любознательных учеников, их родителей и всех, кто интересуется великой русской литературой. А с книгой КАПИТАНСКАЯ ДОЧКА Вы точно полюбите классические произведения, которые изучаются в школе. Вы спросите почему? Ответ прост – свежий взгляд на известный текст поможет понять и полюбить такую знакомую и одновременно загадочную КЛАССИКУ. Ведь в книге помимо полного текста романа с классическими иллюстрациями Павла Соколова, впервые напечатанными в 1891 года, Вы найдете живые оригинальные статьи, в которых читатель найдет глубокий анализ жанра и сюжета, яркие подробные характеристики персонажей и исторические комментарии.');

INSERT INTO vote (value, book_id, username)
VALUES (5, 1, 'admin');
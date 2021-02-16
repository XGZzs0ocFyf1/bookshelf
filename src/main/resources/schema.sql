DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;

CREATE TABLE IF NOT EXISTS AUTHORS
(
    id         serial PRIMARY KEY,
    name       varchar(50),
    birth_date date
);


CREATE TABLE IF NOT EXISTS GENRES
(
    id  serial primary key,
    name varchar(50)

);


CREATE TABLE IF NOT EXISTS BOOKS
(
    id               serial primary key,
    author_id        int,
    genre_id         int,
    title            varchar(200),
    publication_date date,
    isbn10           varchar(20),
    isbn13           varchar(20)

)

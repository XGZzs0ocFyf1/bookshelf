package ru.xgzzs0ocfyf1.bookshelf.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.xgzzs0ocfyf1.bookshelf.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookJdbcDao implements DAO<Book> {

    private final static Logger log = LoggerFactory.getLogger(BookJdbcDao.class);
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Book> rowMapper;


    public BookJdbcDao(JdbcTemplate jdbcTemplate, RowMapper<Book> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Book> list() {
        var sql = "SELECT  id, author_id, genre_id, title, publication_date, isbn10, isbn13 from books";
        return jdbcTemplate.query(sql, rowMapper);
    }


    @Override
    public void create(Book book) {
        var sql = "insert into books(id, author_id, genre_id, title, publication_date, isbn10, isbn13) values " +
                "(?, ?, ?, ?, ?, ?, ?)";
        var insert = jdbcTemplate.update(sql, book.getId(), book.getAuthorId(), book.getGenreId(), book.getTitle(),
                book.getPublicationDate(), book.getIsbn10(), book.getIsbn13());
        if (insert == 1) {
            log.info("New book created " + book.getTitle());
        }
    }

    @Override
    public Optional<Book> get(long id) {
        var sql = "select id, author_id, genre_id, title, publication_date, isbn10, isbn13 from books where id=?";
        Book book = null;
        try {
            book = jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException e) {
            log.info("Book not found: {}", id);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void update(Book book, long id) {
        var sql = "update books set title = ?, publication_date = ?, isbn10 = ?,  isbn13 = ? where id = ?";
        int update = jdbcTemplate.update(sql, book.getTitle(), book.getPublicationDate(), book.getIsbn10(), book.getIsbn10());
        if (update == 1) {
            log.info("book updated: {} ", book.getTitle());
        }
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from books where id = ?", id);
    }

}

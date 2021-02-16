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


    //todo: в я посмотрел в одном из примеров и вынес этот маппинг в отдельный класс для других двух  Entity классов
    //правильно ли это?
    RowMapper<Book> rowMapper = (rs, rowNumber) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setIsbn10(rs.getString("isbn10"));
        book.setIsbn13(rs.getString("isbn13"));
        return book;
    };

    public BookJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> list() {
        var sql = "SELECT  id, author_id, genre_id, title, publication_date, isbn10, isbn13 from books";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Book book) {
        var sql = "insert into books(title, publication_date, isbn10, isbn13) values (?,?,?,?)";
        var insert = jdbcTemplate.update(sql, book.getTitle(), book.getPublicationDate(), book.getIsbn10(),
                book.getIsbn13());
        if (insert == 1) {
            log.info("New book created " + book.getTitle());
        }
    }

    @Override
    public Optional<Book> get(int id) {
        var sql = "select id, title, publication_date, isbn10, isbn13 from books where id=?";
        Book book = null;
        try {
            //fixme: use public <T> List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args) instead
            book = jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException e) {
            log.info("Book not found: {}", id);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void update(Book book, int id) {
        var sql = "update books set title = ?, publication_date = ?, isbn10 = ?,  isbn13 = ? where id = ?";
        int update = jdbcTemplate.update(sql, book.getTitle(), book.getPublicationDate(), book.getIsbn10(), book.getIsbn10());
        if (update == 1) {
            log.info("book updated: {} ", book.getTitle());
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from books where id = ?", id);
    }

}

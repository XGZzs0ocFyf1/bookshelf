package ru.xgzzs0ocfyf1.bookshelf.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.xgzzs0ocfyf1.bookshelf.dao.DAO;
import ru.xgzzs0ocfyf1.bookshelf.dao.mapper.AuthorMapper;
import ru.xgzzs0ocfyf1.bookshelf.model.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorJdbcDao implements DAO<Author> {

    private JdbcTemplate jdbcTemplate;
    private AuthorMapper mapper;
    private Logger log = LoggerFactory.getLogger(AuthorJdbcDao.class);

    public AuthorJdbcDao(JdbcTemplate jdbcTemplate, AuthorMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<Author> list() {
        var sql = "select id, name, birth_date from authors";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public void create(Author author) {
        var sql = "insert into authors (id, name, birth_date) values (?, ?,?)";
        var rowAffected = jdbcTemplate.update(sql, author.getId(), author.getName(), author.getBirthDate());
        if (rowAffected == 1) {
            log.info("New author created: {} ", author.getName());
        }
    }

    @Override
    public Optional<Author> get(long id) {
        var sql = "select id, name, birth_date from authors where id = ?";
        Author author = null;
        try {
            author = jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (DataAccessException e) {
            log.info("Author with id {} not found", id);
        }
        return Optional.ofNullable(author);
    }

    @Override
    public void update(Author author, long id) {
        var sql = "update authors set name = ?, birth_date = ? where id = ?";
        var numberOfRowAffected = jdbcTemplate.update(sql, author.getName(), author.getBirthDate(), id);
        if (numberOfRowAffected == 1) {
            log.info("author updated: {}", author.getName());
        }
    }

    @Override
    public void delete(long id) {
        var numberOfRowAffected = jdbcTemplate.update("delete from books where id = ?", id);
        if (numberOfRowAffected == 1) {
            log.info("author with id = {} deleted", id);
        }
    }
}

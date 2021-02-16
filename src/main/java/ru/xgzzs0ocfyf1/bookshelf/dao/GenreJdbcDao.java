package ru.xgzzs0ocfyf1.bookshelf.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.xgzzs0ocfyf1.bookshelf.dao.DAO;
import ru.xgzzs0ocfyf1.bookshelf.dao.mapper.GenreMapper;
import ru.xgzzs0ocfyf1.bookshelf.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreJdbcDao implements DAO<Genre> {

    private JdbcTemplate jdbcTemplate;
    private GenreMapper mapper;
    private Logger log = LoggerFactory.getLogger(GenreJdbcDao.class);

    public GenreJdbcDao(JdbcTemplate jdbcTemplate, GenreMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<Genre> list() {
        var sql = "select id, name from genres";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public void create(Genre genre) {
        var sql  = "insert into genres(name) values (?) ";
        var numberOfRowAffected = jdbcTemplate.update(sql, genre.getName());
        if (numberOfRowAffected == 1){
            log.info("New genre created: {}", genre.getName());
        }
    }

    @Override
    public Optional<Genre> get(long id) {
        var sql = "select id, name from genres where id = ?";
        Genre genre = null;
        try {
            genre = jdbcTemplate.queryForObject(sql, mapper, id);
        }catch (DataAccessException e){
            log.info("Genre with id = {} not found", id);
        }

        return Optional.ofNullable(genre);
    }

    @Override
    public void update(Genre genre, long id) {
        var sql = "update genres set name = ? where id = ?";
        var numberOfRowAffected = jdbcTemplate.update(sql, genre.getName(), id);
        if (numberOfRowAffected == 1){
            log.info("Genre was updated: {}", genre.getName());
        }
    }

    @Override
    public void delete(long id) {
        var sql = "delete from genres where id = ?";
        var numberOfRowAffected  = jdbcTemplate.update(sql ,id);
        if (numberOfRowAffected == 1){
            log.info("Genre with index {} deleted", id);
        }
    }
}

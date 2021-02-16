package ru.xgzzs0ocfyf1.bookshelf.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.xgzzs0ocfyf1.bookshelf.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setIsbn10(rs.getString("isbn10"));
        book.setIsbn13(rs.getString("isbn13"));
        book.setAuthorId(rs.getLong("author_id"));
        book.setAuthorId(rs.getLong("genre_id"));



        return book;
    }
}

package ru.xgzzs0ocfyf1.bookshelf.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.xgzzs0ocfyf1.bookshelf.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setName(rs.getString("name"));
        if (rs.getDate("birth_date") != null){
            author.setBirthDate(getLocalDateFromJavaUtilDate(rs.getDate("birth_date")));
        }
        return author;
    }

    private LocalDate getLocalDateFromJavaUtilDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

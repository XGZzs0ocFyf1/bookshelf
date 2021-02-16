package ru.xgzzs0ocfyf1.bookshelf.clr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.xgzzs0ocfyf1.bookshelf.dao.BookJdbcDao;
import ru.xgzzs0ocfyf1.bookshelf.dao.AuthorJdbcDao;
import ru.xgzzs0ocfyf1.bookshelf.dao.GenreJdbcDao;
import ru.xgzzs0ocfyf1.bookshelf.model.Author;
import ru.xgzzs0ocfyf1.bookshelf.model.Book;
import ru.xgzzs0ocfyf1.bookshelf.model.Genre;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/***
 * консольный вывод чтобы потрогать как это все работает.
 */
@Component
public class Run implements CommandLineRunner {

    private BookJdbcDao bookJdbcDao;
    private AuthorJdbcDao authorJdbcDao;
    private GenreJdbcDao genreJdbcDao;
    private final Logger log = LoggerFactory.getLogger(CommandLineRunner.class);

    public Run(BookJdbcDao bookJdbcDao, AuthorJdbcDao authorJdbcDao, GenreJdbcDao genreJdbcDao) {
        this.bookJdbcDao = bookJdbcDao;
        this.authorJdbcDao = authorJdbcDao;
        this.genreJdbcDao = genreJdbcDao;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("All books -----------------------------");
        bookJdbcDao.list().forEach(x->log.info("{}",x));

        Book book = new Book();
        book.setTitle("A Space Odyssey");
        book.setPublicationDate(LocalDate.parse("01.01.1968", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        bookJdbcDao.create(book);

        log.info("One book -------------------------");
        Optional<Book> firstBook = bookJdbcDao.get(1);
        log.info("{}", firstBook.get());

        bookJdbcDao.delete(2);


        log.info("Adding new book");
        log.info("{}", book);
        log.info("All books ------------------------------------");
        bookJdbcDao.list().forEach(x -> log.info("{}", x));


        log.info("All authors ------------------------------------");
        authorJdbcDao.list().forEach(author -> log.info("{}", author));

        authorJdbcDao.create(new Author("Slider Alex"));

        authorJdbcDao.delete(1);

        authorJdbcDao.update(new Author("Updated"), 2);

        log.info("Author list after all changes __________________________");
        authorJdbcDao.list().stream().sorted((x, y) -> Long.compare(x.getId(), y.getId())).forEach(x -> log.info("{}", x));

        log.info("All genres before actions -------------------------------");
        genreJdbcDao.list().forEach(g -> log.info("{}", g));

        genreJdbcDao.create(new Genre("Magic"));

        log.info(" update action: {}",genreJdbcDao.get(4));

        genreJdbcDao.delete(1);

        genreJdbcDao.update(new Genre("updated genre "), 2);


        log.info("All genres after actions -------------------------------");
        genreJdbcDao.list().stream().sorted((x, y) -> Long.compare(x.getId(), y.getId())).forEach(x -> log.info("{}", x));



    }
}

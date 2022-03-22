package pl.sztuczkap.bookstore;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztuczkap.bookstore.catalog.application.CatalogController;
import pl.sztuczkap.bookstore.catalog.domain.Book;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;
    private final String title;
    private final Long limit;
    private final String author;

    public ApplicationStartup(
            CatalogController catalogController,
            @Value("${bookstore.catalog.query}") String title,
            @Value("${bookstore.catalog.limit}") Long limit,
            @Value("${bookstore.catalog.author}") String author
    ) {
        this.catalogController = catalogController;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) {
        List<Book> books = catalogController.findByTitle(title);
        books.forEach(System.out::println);

        System.out.println("---Praca domowa:");
        List<Book> authors = catalogController.findByAuthor(author);
        authors.forEach(System.out::println);

    }
}

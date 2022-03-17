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

    public ApplicationStartup(
            CatalogController catalogController,
            @Value("${bookstore.catalog.query}") String title,
            @Value("${bookstore.catalog.limit}") Long limit
    ) {
        this.catalogController = catalogController;
        this.title = title;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        List<Book> books = catalogController.findByTitle(title);
        books.forEach(System.out::println);
    }
}

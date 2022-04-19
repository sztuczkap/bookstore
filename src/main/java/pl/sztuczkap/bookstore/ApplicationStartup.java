package pl.sztuczkap.bookstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztuczkap.bookstore.catalog.application.port.CatalogUseCase;
import pl.sztuczkap.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.sztuczkap.bookstore.catalog.domain.Book;

import java.util.List;

import static pl.sztuczkap.bookstore.catalog.application.port.CatalogUseCase.*;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;

    public ApplicationStartup(
            CatalogUseCase catalog,
            @Value("${bookstore.catalog.query}") String title
    ) {
        this.catalog = catalog;
        this.title = title;
    }

    @Override
    public void run(String... args) {
        initData();
        findByTitle();
        findAndUpdate();
        findByTitle();
    }


    private void initData() {
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834));
        catalog.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1884));
        catalog.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1899));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book...");
        catalog.findOneByTitleAndAutor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Pan Tadeusz, czyli Ostatni Zajazd na Litwie")
                            .build();
                    UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }

}

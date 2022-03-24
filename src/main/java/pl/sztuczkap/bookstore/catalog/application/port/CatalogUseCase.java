package pl.sztuczkap.bookstore.catalog.application.port;

import pl.sztuczkap.bookstore.catalog.domain.Book;

import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {
    List<Book> findByTitle(String title);

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAutor(String title, String author);

    void addBook();

    void removeById(Long id);

    void updateBook();
}

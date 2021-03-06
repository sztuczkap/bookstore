package pl.sztuczkap.bookstore.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.sztuczkap.bookstore.catalog.domain.Book;
import pl.sztuczkap.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong ID_NEXT_VALUE = new AtomicLong(0L);

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null) {
            storage.put(book.getId(), book);
        } else {
            long nextId = newxtId();
            book.setId(nextId);
            storage.put(nextId, book);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void removeById(Long id) {
        storage.remove(id);
    }

    private long newxtId() {
        return ID_NEXT_VALUE.getAndIncrement();
    }
}

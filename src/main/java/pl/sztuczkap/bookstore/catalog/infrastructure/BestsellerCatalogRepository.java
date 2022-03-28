package pl.sztuczkap.bookstore.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.sztuczkap.bookstore.catalog.domain.Book;
import pl.sztuczkap.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

@Repository
class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong ID_NEXT_VALUE = new AtomicLong(0L);

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Book book) {
        long nextId = newxtId();
        book.setId(nextId);
        storage.put(nextId, book);
    }

    private long newxtId() {
        return ID_NEXT_VALUE.getAndIncrement();
    }
}

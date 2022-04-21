package pl.sztuczkap.bookstore.order.domain;

import lombok.Value;
import pl.sztuczkap.bookstore.catalog.domain.Book;

@Value
public class OrderItem {
    Book book;
    int quantity;

}
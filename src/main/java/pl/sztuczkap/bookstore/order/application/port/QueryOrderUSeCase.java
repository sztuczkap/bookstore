package pl.sztuczkap.bookstore.order.application.port;

import pl.sztuczkap.bookstore.order.domain.Order;

import java.util.List;

public interface QueryOrderUSeCase {
    List<Order> findAll();
}

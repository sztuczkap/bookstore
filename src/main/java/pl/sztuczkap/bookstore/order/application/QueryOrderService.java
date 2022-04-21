package pl.sztuczkap.bookstore.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztuczkap.bookstore.order.application.port.QueryOrderUSeCase;
import pl.sztuczkap.bookstore.order.domain.Order;
import pl.sztuczkap.bookstore.order.domain.OrderRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUSeCase {
    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return null;
    }
}

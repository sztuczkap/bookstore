package pl.sztuczkap.bookstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztuczkap.bookstore.catalog.application.port.CatalogUseCase;
import pl.sztuczkap.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.sztuczkap.bookstore.catalog.domain.Book;
import pl.sztuczkap.bookstore.order.application.port.PlaceOrderUSeCase;
import pl.sztuczkap.bookstore.order.application.port.PlaceOrderUSeCase.PlaceOrderCommand;
import pl.sztuczkap.bookstore.order.application.port.QueryOrderUSeCase;
import pl.sztuczkap.bookstore.order.domain.OrderItem;
import pl.sztuczkap.bookstore.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

import static pl.sztuczkap.bookstore.catalog.application.port.CatalogUseCase.*;
import static pl.sztuczkap.bookstore.order.application.port.PlaceOrderUSeCase.*;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final PlaceOrderUSeCase placeOrder;
    private final QueryOrderUSeCase queryOrder;
    private final String title;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            PlaceOrderUSeCase placeOrder,
            QueryOrderUSeCase queryOrder,
            @Value("Pan") String title,
            @Value("1") Long limit
    ) {
        this.catalog = catalog;
        this.title = title;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        //find pan tadeusz
        Book panTadeusz = catalog.findOneByTitle("Pan Tadeusz")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        //find chlopi
        Book chlopi = catalog.findOneByTitle("Chłopi")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        //create recipient
        Recipient recipient = Recipient
                .builder()
                .name("Jan Kowalski")
                .phone("123-456-789")
                .street("Armi Krajowej 31")
                .city("Krakow")
                .zipCode("30-150")
                .email("jan@example.org")
                .build();

        //place order command
        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(panTadeusz, 16))
                .item(new OrderItem(chlopi, 7))
                .build();

        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());

        //list all orders
        queryOrder.findAll()
                .forEach(order -> {
                    System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS: " + order);
                });
    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }


    private void initData() {
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834, new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1884, new BigDecimal("29.90")));
        catalog.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904, new BigDecimal("11.90")));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1899, new BigDecimal("14.90")));
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

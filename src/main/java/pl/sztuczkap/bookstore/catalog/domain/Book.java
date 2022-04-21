package pl.sztuczkap.bookstore.catalog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private BigDecimal price;

    public Book(String title, String author, Integer year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
}

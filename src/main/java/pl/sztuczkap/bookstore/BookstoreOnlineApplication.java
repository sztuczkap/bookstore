package pl.sztuczkap.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookstoreOnlineApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreOnlineApplication.class, args);
	}

	private final CatalogService catalogService;

	public BookstoreOnlineApplication(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@Override
	public void run(String... args) {
//		CatalogService service = new CatalogService();
		List<Book> books = catalogService.findByTitle("Pan Tadeusz");
		books.forEach(System.out::println);
	}
}

package eu.rimbaud.bookshelf.api;

import eu.rimbaud.bookshelf.api.model.Book;
import eu.rimbaud.bookshelf.api.repository.BookRepository;
import eu.rimbaud.bookshelf.api.repository.CustomBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
@SpringBootApplication
public class BookshelfApiApplication implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomBookRepository customRepo;

    public static void main(String[] args) {
        SpringApplication.run(BookshelfApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-----CREATE BOOKS-----\n");

        createBooks();

        System.out.println("\n-----SHOW ALL BOOKS-----\n");

        showAllBooks();

        System.out.println("\n-----GET BOOK BY NAME-----\n");

        getBookByTitle("Les fourmis");

        System.out.println("\n-----GET BOOKS BY CATEGORY-----\n");

        getBooksByAuthor("David Eddings");

        System.out.println("\n-----UPDATE COLLECTION OF POCKET COLLECTION-----\n");

        updateCollection("Pocket");

        System.out.println("\n-----DELETE A BOOK-----\n");

        deleteBook("ID1");

        System.out.println("\n-----FINAL COUNT OF BOOKS-----\n");

        findCountOfBooks();

        System.out.println("\n-----THANK YOU-----");

        System.out.println("\n-----UPDATE COLLECTION OF A BOOK-----\n");

        updateBookCollection("Les fourmis", "J'ai lu");
    }

    // Print details in readable form

    public String getBookDetails(Book book) {
        System.out.println(
                "Book Author: " + book.getAuthor() +
                        ", \nTitle: " + book.getTitle() +
                        ", \nSerie: " + book.getSerie() +
                        ", \nCollection: " + book.getCollection()
        );
        return "";
    }

    // READ
    // 1. Show all the data
    public void showAllBooks() {
        bookRepository.findAll().forEach(book -> System.out.println(getBookDetails(book)));
    }

    // 2. Get book by title
    public void getBookByTitle(String title) {
        System.out.println("Getting book by title: " + title);
        Book book = bookRepository.findItemByTitle(title);
        System.out.println(getBookDetails(book));
    }

    // 3. Get name and quantity of a all items of a particular category
    public void getBooksByAuthor(String author) {
        System.out.println("Getting books by the author " + author);
        List<Book> list = bookRepository.findAllByAuthor(author);

        list.forEach(book -> System.out.println("Author: " + book.getAuthor() + ", Title: " + book.getTitle()));
    }

    // 4. Get count of documents in the collection
    public void findCountOfBooks() {
        long count = bookRepository.count();
        System.out.println("Number of documents in the collection: " + count);
    }

    //CREATE
    void createBooks() {
        System.out.println("Data creation started...");
        bookRepository.save(new Book("ID1", "David Eddings", "le tome 1", "La Belgariade", "Pocket"));
        bookRepository.save(new Book("ID2", "David Eddings", "le tome 2", "La Belgariade", "Pocket"));
        bookRepository.save(new Book("ID3", "David Eddings", "le tome 3", "La Belgariade", "Pocket"));
        bookRepository.save(new Book("ID4", "Bernard Werber", "Les fourmis", "Les fourmis", "Pocket"));
        bookRepository.save(new Book("ID5", "Bernard Werber", "La révolution des fourmis", "Les fourmis", "Pocket"));
        System.out.println("Data creation complete...");
    }

    //UPDATE
    public void updateCollection(String collection) {
        // Change to this new value
        String newCollection = "Folio";

        // Find all the books with the collection pocket
        List<Book> list = bookRepository.findAllByCollection(collection);

        list.forEach(item -> {
            // Update the collection in each document
            item.setCollection(newCollection);
        });

        // Save all the books in database
        List<Book> itemsUpdated = bookRepository.saveAll(list);

        System.out.println("Successfully updated " + itemsUpdated.size() + " books.");
    }

    // UPDATE
    public void updateBookCollection(String title, String newCollection) {
        System.out.println("Updating collection for " + title);
        customRepo.updateBookCollection(title, newCollection);
    }

    // DELETE
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
        System.out.println("Book with id " + id + " deleted...");
    }
}

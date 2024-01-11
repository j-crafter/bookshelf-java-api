package eu.rimbaud.bookshelf.api.repository;

import eu.rimbaud.bookshelf.api.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{title:'?0'}")
    Book findItemByTitle(String title);

    @Query(value="{author:'?0'}", fields="{'author' : 1, 'title' : 1}")
    List<Book> findAllByAuthor(String author);

    long count();

    @Query(value="{collection:'?0'}")
    List<Book> findAllByCollection(String collection);
}

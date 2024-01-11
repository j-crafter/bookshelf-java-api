package eu.rimbaud.bookshelf.api.repository;

import com.mongodb.client.result.UpdateResult;
import eu.rimbaud.bookshelf.api.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CustomBookRepositoryImpl implements CustomBookRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void updateBookCollection(String title, String collection) {
        Query query = new Query(Criteria.where("title").is(title));
        Update update = new Update();
        update.set("collection", collection);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Book.class);

        if (result.getModifiedCount() == 0)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");
    }
}

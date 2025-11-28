package microservice.bookservice.repository;

import microservice.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
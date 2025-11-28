package microservice.bookservice.service;

import microservice.bookservice.entity.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);
    Book getById(Long id);
    List<Book> getAll();
}

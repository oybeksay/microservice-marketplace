package microservice.bookservice.service.impls;

import lombok.RequiredArgsConstructor;
import microservice.bookservice.entity.Book;
import microservice.bookservice.repository.BookRepository;
import microservice.bookservice.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}

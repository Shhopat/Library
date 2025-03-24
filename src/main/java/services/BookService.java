package services;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void addAuthor(int id, Book book1) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("книга с id " + id + " не найдена"));
        book.setAuthor(book1.getAuthor());
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book book1 = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("the book with id " + id + " no find"));
        book1.setId(book.getId());
        book1.setYearBook(book.getYearBook());
        book1.setAuthor(book.getAuthor());
        book1.setName(book.getName());
        book1.setNameAuthor(book.getNameAuthor());
        bookRepository.save(book1);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void deleteAuthor(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("книга с id " + id + " не найдена"));
        book.setAuthor(null);
        bookRepository.save(book);
    }


}

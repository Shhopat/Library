import model.Author;
import model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.BookRepository;
import services.AuthorService;
import services.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookDaoTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private Optional<Book> optionalBook;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    BookService bookService;


    @Test
    void shouldGetAllBooks() {
        List<Book> list = Arrays.asList(new Book("Книга", "Fdnjh", 12));
        Mockito.when(bookRepository.findAll()).thenReturn(list);
        List<Book> result = bookService.findAll();
        Assertions.assertEquals(list.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(list.get(0).getNameAuthor(), result.get(0).getNameAuthor());
        Assertions.assertEquals(list.size(), result.size());
    }

    @Test
    void shouldSaveBook() {
        Book book = new Book("Книга", "Саша", 12);
        bookService.save(book);
        Mockito.verify(bookRepository).save(book);


    }

    @Test
    void shouldBookById() {
        int id = 1;
        Book book = new Book("Книга", "Саша", 12);
        optionalBook = Optional.of(book);
        Mockito.when(bookRepository.findById(id)).thenReturn(optionalBook);

        Book result = bookService.findById(id);

        Assertions.assertEquals(book.getNameAuthor(), result.getNameAuthor());
        Assertions.assertEquals(book.getName(), result.getName());

        Mockito.verify(bookRepository).findById(id);


    }

    @Test
    void shouldAddAuthor() {
        int id = 1;
        Author author = new Author("Ибрагим", 1997);
        Book old = new Book("Книга", "Саша", 12);
        Book newBook = new Book("Книга", "Саша", 12);
        newBook.setAuthor(author);

        optionalBook = Optional.of(old);
        Mockito.when(bookRepository.findById(id)).thenReturn(optionalBook);
        bookService.addAuthor(id, newBook);

        Assertions.assertEquals(old.getAuthor().getFullname(), newBook.getAuthor().getFullname());

        Mockito.verify(bookRepository).save(old);


    }

    @Test
    void shouldUpdate() {
        int id = 1;
        Author author = new Author("Ибрагим", 1997);
        Book old = new Book("Книга", "Саша", 12);
        Book newBook = new Book("Книга", "Саша", 12);
        newBook.setAuthor(author);

        optionalBook = Optional.of(old);
        Mockito.when(bookRepository.findById(id)).thenReturn(optionalBook);

        bookService.update(id, newBook);

        Mockito.verify(bookRepository).findById(id);
        Mockito.verify(bookRepository).save(old);


    }

    @Test
    void shouldRemoveById() {
        int id = 1;
        Book book = new Book("Книг", "Саш", 1);
        book.setId(id);
        bookService.delete(id);
        Mockito.verify(bookRepository).deleteById(id);


    }

    @Test
    void shouldRemoveAuthor() {
        int id = 1;
        Author author = new Author("Ибрагим", 1997);
        Book book = new Book("Книг", "Саш", 1);
        book.setAuthor(author);
        optionalBook = Optional.of(book);

        Mockito.when(bookRepository.findById(id)).thenReturn(optionalBook);
        bookService.deleteAuthor(id);

        Assertions.assertNull(book.getAuthor());

        Mockito.verify(bookRepository).save(book);


    }

    @Test
    void shouldGetBookByLikeName() {
        String like = "Философия";
        List<Book> list = Arrays.asList(new Book(like, "Иван", 1999));
        Mockito.when(bookRepository.findByNameStartingWith(like)).thenReturn(list);
        List<Book> result = bookService.findByNameStartingWith(like);

        Assertions.assertEquals(list.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(list.get(0).getNameAuthor(), result.get(0).getNameAuthor());
        Assertions.assertEquals(list.get(0).getYearBook(), result.get(0).getYearBook());

        Mockito.verify(bookRepository).findByNameStartingWith(like);


    }


}

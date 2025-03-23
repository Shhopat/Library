import dao.AuthorDao;
import dao.BookDao;
import model.Author;
import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookDaoTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Query<Book> query;
    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private BookDao bookDao;


    @BeforeEach
    void setUp() {
        Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldGetAllBooks() {
        List<Book> list = Arrays.asList(new Book("Книга", "Fdnjh", 12));
        Mockito.when(session.createQuery("FROM Book", Book.class)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);

        List<Book> result = bookDao.getAllBooks();

        Assertions.assertEquals(list.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(list.get(0).getYearBook(), result.get(0).getYearBook());
        Assertions.assertEquals(list.get(0).getNameAuthor(), result.get(0).getNameAuthor());

        Mockito.verify(session).createQuery("FROM Book", Book.class);
        Mockito.verify(query).getResultList();


    }

    @Test
    void shouldSaveBook() {
        Book book = new Book("Книга", "Саша", 12);
        bookDao.save(book);
        Mockito.verify(session).save(book);

    }

    @Test
    void shouldBookById() {
        int id = 1;
        Book book = new Book("Книга", "Саша", 12);
        Mockito.when(session.get(Book.class, id)).thenReturn(book);

        Book result = bookDao.getBookById(id);

        Assertions.assertEquals(book.getNameAuthor(), result.getNameAuthor());
        Assertions.assertEquals(book.getName(), result.getName());

        Mockito.verify(session).get(Book.class, id);
    }

    @Test
    void shouldAddAuthor() {
        int id = 1;
        Author author = new Author("Ибрагим", 1997);
        author.setId(id);
        Book book = new Book("Книга", "Саша", 12);
        Book book1 = new Book("Книга", "Саша", 12);
        book1.setAuthor(author);

        Mockito.when(session.get(Book.class, id)).thenReturn(book);
        Mockito.when(authorDao.getAuthorById(id)).thenReturn(author);

        bookDao.addAuthor(id, book1);

        Assertions.assertEquals(book.getAuthor().getFullname(), book1.getAuthor().getFullname());

        Mockito.verify(session).get(Book.class, id);
        Mockito.verify(session).update(book);


    }

    @Test
    void shouldUpdate() {
        int id = 1;
        Author author = new Author("Ибрагим", 1997);
        author.setId(id);
        Book book = new Book("Книг", "Саш", 1);
        Book book1 = new Book("Книга", "Саша", 12);
        book1.setAuthor(author);

        Mockito.when(session.get(Book.class, id)).thenReturn(book);

        bookDao.update(id, book1);

        Assertions.assertEquals(book.getName(), book1.getName());
        Assertions.assertEquals(book.getYearBook(), book1.getYearBook());
        Assertions.assertEquals(book.getNameAuthor(), book1.getNameAuthor());
        Assertions.assertEquals(book.getAuthor().getFullname(), book1.getAuthor().getFullname());

        Mockito.verify(session).get(Book.class, id);
        Mockito.verify(session).update(book);


    }

    @Test
    void shouldRemoveById() {
        int id = 1;
        Book book = new Book("Книг", "Саш", 1);

        Mockito.when(session.get(Book.class, id)).thenReturn(book);

        bookDao.remove(id);

        Mockito.verify(session).get(Book.class, id);
        Mockito.verify(session).remove(book);


    }

    @Test
    void shouldRemoveAuthor() {
        int id = 1;
        Author author = new Author("Ибрагим", 1997);
        Book book = new Book("Книг", "Саш", 1);
        book.setAuthor(author);

        Mockito.when(session.get(Book.class, id)).thenReturn(book);

        bookDao.removeAuthorBook(id);

        Assertions.assertNull(book.getAuthor());

        Mockito.verify(session).get(Book.class, id);
        Mockito.verify(session).update(book);


    }


}

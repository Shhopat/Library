import dao.AuthorDao;
import model.Author;
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
public class AuthorDaoTest {
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Query<Author> authorQuery;
    @InjectMocks
    private AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldAllAuthors() {
        List<Author> list = Arrays.asList(new Author("Иванов Иван Иванович", 1997));
        Mockito.when(session.createQuery("FROM Author", Author.class)).thenReturn(authorQuery);
        Mockito.when(authorQuery.getResultList()).thenReturn(list);

        List<Author> result = authorDao.getAuthors();
        Assertions.assertEquals(list.size(), result.size());
        Assertions.assertEquals(list.get(0).getFullname(), result.get(0).getFullname());

        Mockito.verify(session).createQuery("FROM Author", Author.class);
        Mockito.verify(authorQuery).getResultList();
    }

    @Test
    void shouldSaveAuthor() {
        Author author = new Author("Иванов Иван Иванович", 1997);
        authorDao.save(author);
        Mockito.verify(session).save(author);

    }

    @Test
    void shouldGetAuthorById() {
        Author author = new Author("Иванов Иван Иванович", 1997);
        int id = 1;
        Mockito.when(session.get(Author.class, id)).thenReturn(author);

        Author result = authorDao.getAuthorById(id);
        Assertions.assertEquals(author.getFullname(), result.getFullname());
        Assertions.assertEquals(author.getId(), result.getId());

        Mockito.verify(session).get(Author.class, id);
    }

    @Test
    void shouldUpdate() {
        int id = 1;
        Author oldAuthor = new Author("Иванов Иван Иванович", 1997);
        Author newAuthor = new Author("Иван Иван Иванович", 199);

        Mockito.when(session.get(Author.class, id)).thenReturn(oldAuthor);
        authorDao.update(id, newAuthor);

        Assertions.assertEquals(oldAuthor.getFullname(), newAuthor.getFullname());
        Assertions.assertEquals(oldAuthor.getYear(), newAuthor.getYear());

        Mockito.verify(session).get(Author.class, id);


    }

    @Test
    void shouldRemove() {
        int id = 1;
        Author oldAuthor = new Author("Иванов Иван Иванович", 1997);

        Mockito.when(session.get(Author.class, id)).thenReturn(oldAuthor);
        authorDao.remove(id);

        Mockito.verify(session).get(Author.class, id);
        Mockito.verify(session).remove(oldAuthor);
    }


}

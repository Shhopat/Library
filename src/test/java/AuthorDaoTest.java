import model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.AuthorRepository;
import services.AuthorService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoTest {
    @Mock
    private AuthorRepository authorRepository;

    private Optional<Author> optionalAuthor;

    @InjectMocks
    private AuthorService authorService;


    @Test
    void shouldAllAuthors() {
        List<Author> list = Arrays.asList(new Author("Иванов Иван Иванович", 1997));
        Mockito.when(authorService.findAll()).thenReturn(list);
        List<Author> result = authorService.findAll();
        Assertions.assertEquals(list.get(0).getFullname(), result.get(0).getFullname());

    }

    @Test
    void shouldSaveAuthor() {
        Author author = new Author("Иванов Иван Иванович", 1997);
        authorService.save(author);
        Mockito.verify(authorRepository).save(author);


    }

    @Test
    void shouldGetAuthorById() {
        int id = 1;
        Author author = new Author("Иванов Иван Иванович", 1997);
        optionalAuthor = Optional.of(author);
        Mockito.when(authorRepository.findById(id)).thenReturn(optionalAuthor);


        Author result = authorService.findOne(id);

        Assertions.assertEquals(author.getFullname(), result.getFullname());

        Mockito.verify(authorRepository).findById(id);

    }

    @Test
    void shouldUpdate() {
        int id = 1;
        Author oldAuthor = new Author("Иванов Иван Иванович", 1997);
        Author newAuthor = new Author("Иван Иван Иванович", 199);
        optionalAuthor = Optional.of(oldAuthor);
        Mockito.when(authorRepository.findById(id)).thenReturn(optionalAuthor);
        authorService.update(id, newAuthor);
        Assertions.assertEquals(oldAuthor.getFullname(), newAuthor.getFullname());


        Mockito.verify(authorRepository).findById(id);
        Mockito.verify(authorRepository).save(oldAuthor);

    }

    @Test
    void shouldRemove() {
        int id = 1;
        Author oldAuthor = new Author("Иванов Иван Иванович", 1997);
        oldAuthor.setId(id);
        authorService.delete(id);
        Mockito.verify(authorRepository).deleteById(id);


    }


}

package services;


import jdk.dynalink.linker.LinkerServices;
import model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
        //возвратит всех авторов
    }

    public Author findOne(int id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
        //возвращает человека если не найден null
    }

    @Transactional
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Transactional
    public void update(int id, Author author) {
        Author author1 = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("автор с id" + id + "не найден"));

        author1.setFullname(author.getFullname());
        author1.setBooks(author.getBooks());
        author1.setYear(author.getYear());

        authorRepository.save(author1);
    }

    @Transactional
    public void delete(int id) {
        authorRepository.deleteById(id);
    }


}

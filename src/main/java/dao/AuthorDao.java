package dao;

import model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AuthorDao {
    private SessionFactory sessionFactory;

    public AuthorDao() {
    }

    @Autowired
    public AuthorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Author> getAuthors() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Author", Author.class).getResultList();
    }

    @Transactional
    public void save(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.save(author);
        session.flush();
    }

    @Transactional
    public Author getAuthorById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Author.class, id);
    }

    @Transactional
    public void update(int id, Author author) {
        Session session = sessionFactory.getCurrentSession();
        Author author1 = session.get(Author.class, id);
        author1.setFullname(author.getFullname());
        author1.setYear(author.getYear());
        author1.setBooks(author.getBooks());
        session.update(author1);
    }

    @Transactional
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Author.class, id));
    }

}

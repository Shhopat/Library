package dao;

import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDao {
    private final SessionFactory sessionFactory;
    private final AuthorDao authorDao;

    @Autowired
    public BookDao(SessionFactory sessionFactory, AuthorDao authorDao) {
        this.sessionFactory = sessionFactory;
        this.authorDao = authorDao;
    }

    @Transactional
    public List<Book> getAllBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).getResultList();
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public Book getBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void addAuthor(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book book1 = session.get(Book.class, id);
        book1.setAuthor(authorDao.getAuthorById(book.getAuthor().getId()));
        session.update(book1);
    }

    @Transactional
    public void update(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book book1 = session.get(Book.class, id);
        book1.setName(book.getName());
        book1.setYearBook(book.getYearBook());
        book1.setNameAuthor(book.getNameAuthor());
        book1.setAuthor(book.getAuthor());
    }

    @Transactional
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }

}

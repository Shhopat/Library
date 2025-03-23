package controller;

import dao.AuthorDao;
import dao.BookDao;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final AuthorDao authorDao;

    @Autowired
    public BookController(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDao.getAllBooks());
        return "books";
    }

    @GetMapping("/new")
    public String getFormBook(@ModelAttribute("book") Book book) {
        return "book_form.html";

    }

    @PostMapping
    public String createBook(@ModelAttribute("book") Book book) {
        bookDao.save(book);
        return "redirect:/books";

    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getBookById(id));
        model.addAttribute("authors", authorDao.getAuthors());
        return "book.html";

    }

    @PatchMapping("/{id}/addAuthor")
    public String addAuthor(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDao.addAuthor(id, book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getBookById(id));
        return "editFormBook.html";
    }

    @PatchMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String remove(@PathVariable("id") int id) {
        bookDao.remove(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/takeBook")
    public String takeBook(@PathVariable("id") int id) {
        bookDao.removeAuthorBook(id);
        return "redirect:/books/" + id;
    }


}

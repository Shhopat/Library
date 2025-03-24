package controller;

import jakarta.validation.Valid;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.AuthorService;
import services.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/new")
    public String getFormBook(@ModelAttribute("book") Book book) {
        return "book_form.html";

    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book_form.html";
        }
        bookService.save(book);
        return "redirect:/books";

    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("authors", authorService.findAll());
        return "book.html";

    }

    @PatchMapping("/{id}/addAuthor")
    public String addAuthor(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.addAuthor(id, book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "editFormBook.html";
    }

    @PatchMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editFormBook.html";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String remove(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/takeBook")
    public String takeBook(@PathVariable("id") int id) {
        bookService.deleteAuthor(id);
        return "redirect:/books/" + id;
    }


}

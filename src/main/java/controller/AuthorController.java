package controller;

import dao.AuthorDao;
import model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }


    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorDao.getAuthors());
        return "authors";
    }

    @GetMapping("/new")
    public String getForm(Model model, @ModelAttribute("author") Author author) {
        return "author_form";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute("author") Author author) {
        authorDao.save(author);
        return "authors";

    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDao.getAuthorById(id));
        return "authorId";

    }

    @GetMapping("/{id}/edit")
    private String getEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDao.getAuthorById(id));
        return "editForm";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("author") Author author, @PathVariable("id") int id) {
        authorDao.update(id, author);
        return "redirect:/authors";

    }


}

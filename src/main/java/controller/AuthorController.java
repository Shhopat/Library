package controller;

import jakarta.validation.Valid;
import model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorRepository) {
        this.authorService = authorRepository;
    }


    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors";
    }

    @GetMapping("/new")
    public String getForm(Model model, @ModelAttribute("author") Author author) {
        return "author_form";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute("author") @Valid Author author,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "author_form";
        }
        authorService.save(author);
        return "redirect:/authors";

    }

    @GetMapping("/{id}")
    public String getAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorService.findOne(id));
        return "authorId";

    }

    @GetMapping("/{id}/edit")
    private String getEdit(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorService.findOne(id));
        return "editForm";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "editForm";
        }
        authorService.update(id, author);
        return "redirect:/authors";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        authorService.delete(id);
        return "redirect:/authors";
    }


}

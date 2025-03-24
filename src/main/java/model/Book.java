package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {
    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 40, unique = true, nullable = false)
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 10, max = 40, message = "Символы должны быть между 10 - 40")
    private String name;

    @Column(name = "nameAuthor", length = 40, nullable = false)
    @NotEmpty(message = "не может быть пустым")
    @Size(min = 10, max = 40, message = "Символы должны быть между 10 - 40")
    private String nameAuthor;

    @Column(name = "yearBook", nullable = false)
    @Min(value = 10, message = "нужно больше 10")
    private int yearBook;

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public int getYearBook() {
        return yearBook;
    }

    public void setYearBook(int yearBook) {
        this.yearBook = yearBook;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = true)
    private Author author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(String name, String nameAuthor, int yearBook) {
        this.name = name;
        this.nameAuthor = nameAuthor;
        this.yearBook = yearBook;
    }
}

package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fullname", nullable = false, unique = true, length = 60)
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 5, max = 60, message = "Символы должны быть между 5- 60 ")
    private String fullname;

    @Column(name = "year", nullable = false, unique = false)
    @Min(value = 10, message = "должно быть больше 10")
    private int year;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Book> books;

    public String getFullname() {
        return fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author() {
    }

    public Author(String fullname, int year) {
        this.fullname = fullname;
        this.year = year;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", year=" + year +
                ", books=" + books +
                '}';
    }


}

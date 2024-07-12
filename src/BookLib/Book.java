package BookLib;

import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private double price;
    private int pages;
    private String isbn;
    private Author author;
    private Publisher publisher;
    private Genre genre;

    public Book(String title, double price, int pages, String isbn, Author author, Publisher publisher, Genre genre) {
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    public Book(String title, double price, int pages,  String isbn,String author, String publisher, String genre) {
        this.title = title;
        this.author = new Author(author);
        this.isbn = isbn;
        this.publisher = new Publisher(publisher);
        this.genre = new Genre(genre);
    }

    public Book(String title, double price, int pages,  String isbn){
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author.getName();
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher.getName();
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre.getName();
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + "   Title: " + title + "   BookLib.Author: " + author.getName();
    }
}
import java.util.HashSet;

public class Library {
    private HashSet<Book> books;

    public Library() {
        books = new HashSet<>();
    }

    public boolean addBook(Book book) {
        return books.add(book);
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public HashSet<Book> findBookByAuthor(String author) {
        var authorBooks = new HashSet<Book>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }

    public HashSet<Book> listBooks() {
        return books;
    }
}

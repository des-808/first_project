import BookLib.Book;

import java.sql.*;
import java.util.HashSet;

public class Library {

    public Library() {
        DatabaseManager.DatabaseManagerInit(); // Initialize the database connection
    }


    public boolean updateBook(Book book) {
        try (Connection connection = DatabaseManager.connect()) {
            String query = "UPDATE Book SET title = ?, author = ? WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }



    public boolean addBook(Book book) {
       Book isbn2 = findBookByIsbn(book.getIsbn());
       if (isbn2 == null) {return false;}
        //if (isbn2.getIsbn().equals(book.getIsbn())) { return false;}
        try (Connection connection = DatabaseManager.connect()) {
            //String query = "INSERT INTO Book ( title,price,pages,isbn) VALUES (?,?,?,?)";
            //String query = "INSERT INTO Author (name) VALUES (?); INSERT INTO Publisher (name) VALUES (?); INSERT INTO Genre (name) VALUES (?); INSERT INTO Book (title, isbn, author_id, publisher_id, genre_id) VALUES (?, ?, LAST_INSERT_ID(), LAST_INSERT_ID(), LAST_INSERT_ID());";
            String query1 = "INSERT INTO Author (name) VALUES (?);";
            String query2 = " INSERT INTO Publisher (name) VALUES (?);";
            String query3 = " INSERT INTO Genre (name) VALUES (?);";
            String query4 = " INSERT INTO Book (title, isbn, author_id, publisher_id, genre_id) VALUES (?, ?, LAST_INSERT_ID(), LAST_INSERT_ID(), LAST_INSERT_ID());";
            String query = "SELECT Book.title,Book.price,Book.pages,Book.isbn,Book.author,Book.publisher,Book.genre FROM Book JOIN Author ON Book.author_id = Author.id JOIN Publisher ON Book.publisher_id = Publisher.id JOIN Genre ON Book.genre_id = Genre.id WHERE Book.isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            /*statement.setString(1, book.getAuthor());
            statement.setString(2, book.getPublisher());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getTitle())*/;
            statement.setString(1, book.getIsbn());
            //statement.setDouble(6, book.getPrice());
            //statement.setInt(7, book.getPages());




            //-----------------------------------------------------------------------------


            statement.executeUpdate();
            //-----------------------------------------------------------------------------
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }
    public boolean removeBook(Book book) {
        try (Connection connection = DatabaseManager.connect()) {
            String query = "DELETE FROM Book WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getIsbn());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }

    // добавляем новую книгу в базу данных со связанными таблицами
    public Book findBookByIsbn(String isbn) {
        String title = "";
        int pages = 0;
        double price = 0;
        String isbn_ = "";
        String author = null;
        String publisher = null;
        String genre = null;
        try (Connection connection = DatabaseManager.connect()) {
            String query = "SELECT * FROM Book WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                title = resultSet.getString("title");
                price = resultSet.getDouble("price");
                pages = resultSet.getInt("pages");
                isbn_ = resultSet.getString("isbn");
                //author = resultSet.getString("author");
                //publisher = resultSet.getString("publisher");
                //genre = resultSet.getString("genre");
                return new Book(title, price, pages, isbn_);
            }
            //return new Book(title, price, pages, isbn_, author, publisher, genre);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);

        }
        return new Book(title, price, pages, isbn_);
    }

    public Book findBookByIsbn2(String isbn) {
        try (Connection connection = DatabaseManager.connect()) {
            String query = "SELECT * FROM Book WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Book(resultSet.getString("title"),resultSet.getDouble("price"),resultSet.getInt("pages"),resultSet.getString("isbn"),resultSet.getString("author"), resultSet.getString("publisher"), resultSet.getString("genre"));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }
    public HashSet<Book> findBookByAuthor(String author) {
        try (Connection connection = DatabaseManager.connect()) {
            String query2 = "SELECT * FROM Book WHERE author_id like= ?";
            String query = "SELECT Book.title,Book.price,Book.pages,Book.isbn,Book.author,Book.publisher,Book.genre FROM Book JOIN Author ON Book.author_id = Author.id JOIN Publisher ON Book.publisher_id = Publisher.id JOIN Genre ON Book.genre_id = Genre.id WHERE Book.isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            HashSet<Book> authorBooks = new HashSet<>();
            while (resultSet.next()) {
                authorBooks.add(new Book(resultSet.getString("title"),resultSet.getDouble("price"),resultSet.getInt("pages"), resultSet.getString("isbn"), resultSet.getString("author"), resultSet.getString("publisher"), resultSet.getString("genre")));
            }
            return authorBooks;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return null;
        }
    }
    public HashSet<Book> listBooks() {
        HashSet<Book> books = new HashSet<>();
        try (Connection connection = DatabaseManager.connect()) {

            String query2 = "SELECT * FROM Book";
            String query = "SELECT Book.title, Author.name, Publisher.name, Genre.name " +
                    "FROM Book " +
                    "JOIN Author ON Book.author_id = Author.id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.id " +
                    "JOIN Genre ON Book.genre_id = Genre.id;";


            PreparedStatement statement = connection.prepareStatement(query2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getString("title"), resultSet.getDouble("price"), resultSet.getInt("pages"), resultSet.getString("author"), resultSet.getString("isbn"), resultSet.getString("publisher"), resultSet.getString("genre")));
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return null;
        }
    }

}
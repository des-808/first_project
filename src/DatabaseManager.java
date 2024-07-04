import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
public class DatabaseManager {
    private String url;
    private final String testUrl ="jdbc:sqlserver://LAPTOP-U7N6H5S8:1433;database=master;IntegratedSecurity=False;TrustServerCertificate=True;ConnectTimeout=30;";
    private String user = "ssa";
    private String pass = "ssa";
    public DatabaseManager(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }
    public DatabaseManager() {
        initConnectionProperties(); // load connection properties from file
        checkDatabase();
    }
    public void  checkDatabase() {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
            System.out.println("База данных уже существует");
        } catch (SQLException e) {
            System.out.println("База данных не существует. Создаем базу данных...");
            createDatabase();
        }
    }
    public void createDatabase() {
        try (Connection connection = DriverManager.getConnection(getTestUrl(), getUser(), getPass())) {
            String query = "CREATE DATABASE LibraryTestDB;";
            String query2 = "USE LibraryTestDB;";
            String query3 = "CREATE TABLE Book (id INT PRIMARY KEY IDENTITY(1,1),title VARCHAR(255),author VARCHAR(255),isbn VARCHAR(13));";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            System.out.println("База данных успешно создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании базы данных: " + e);
        }
    }


    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
    public String getUrl() {
        return url;
    }
    public String getTestUrl() {
        return testUrl;
    }
    public void setUrl(String url) {
        this.url = url;
    }



    public boolean updateBook(Book book) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
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
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
            String query = "INSERT INTO Book ( isbn,title, author ) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }

    public boolean removeBook(Book book) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
            String query = "DELETE FROM Book WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getIsbn());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }

    public Book findBookByIsbn(String isbn) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
            String query = "SELECT * FROM Book WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Book(resultSet.getString("isbn"), resultSet.getString("title"), resultSet.getString("author"));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    public HashSet<Book> findBookByAuthor(String author) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
            String query = "SELECT * FROM Book WHERE author = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            HashSet<Book> authorBooks = new HashSet<>();
            while (resultSet.next()) {
                authorBooks.add(new Book(resultSet.getString("isbn"), resultSet.getString("title"), resultSet.getString("author")));
            }
            return authorBooks;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return null;
        }
    }

    public HashSet<Book> listBooks() {
        HashSet<Book> books = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
            String query = "SELECT * FROM Book";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getString("isbn"), resultSet.getString("title"), resultSet.getString("author")));
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return null;
        }
    }

    public void initConnectionProperties() {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        } catch (Exception ex){
            System.out.println(ex);
        }
        setUrl(props.getProperty("url"));
        setUser(props.getProperty("username"));
        setPass(props.getProperty("password"));
    }
}
package utils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    //-------------------------------------------------------------------------
    //Функция для подключения к базе данных с возвращаемым подключением
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(getUrl(),getUser(),getPass());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static Connection connect(String urlTest) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlTest,getUser(),getPass());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static String url ;
    private static String testUrl;// = "jdbc:sqlserver://localhost:1433;database=master;IntegratedSecurity=False;TrustServerCertificate=True;ConnectTimeout=30;";
    private static String user = "ssa";
    private static String pass = "ssa";

    public static void DatabaseManagerInit() {
        initConnectionProperties(); // load connection properties from file
        checkDatabase();
    }

    public static void initConnectionProperties() {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        } catch (Exception ex){
            System.out.println(ex);
        }
        setUrlTest(props.getProperty("url_Test"));
        setUrl(props.getProperty("url"));
        setUser(props.getProperty("username"));
        setPass(props.getProperty("password"));
    }

    public static void  checkDatabase() {
        try (Connection connection = connect()) {
            if (connection != null) {
                System.out.println("Проверка соединения с базой данных...\n Пройдена успешно!!");
            } else {
                System.out.println("База данных не существует. Создаем базу данных...");
                createNewDatabase(getUrlTest());
            }
        } catch (SQLException _) {}
    }
    public static void dropDatabase(String dbName) {
        try (Connection connection  = connect())  {
            String query  = "DROP DATABASE "+dbName+" ";
            Statement statement  = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("База данных успешно удалена");
        }
        catch  (SQLException e)
        {
            System.out.println("Ошибка при удалении базы данных: " + e);
        }
    }

    public static void createNewDatabase(String url_) {
        try (Connection connection = connect(url_)) {
                if(connection != null) {
                   Statement statement = connection.createStatement();
                    statement.executeUpdate(newBdName);
                    statement.executeUpdate(newBd);
                    System.out.println("База данных успешно создана");
                }
        } catch (SQLException e) {
            System.out.println("Ошибка при создании базы данных: " + e.getMessage());
        }
    }


    public static String getUser() { return user; }
    public static void setUser(String user_) { user = user_; }
    public static String getPass() { return pass; }
    public static void setPass(String pass_) { pass = pass_; }
    public static String getUrl() {
        return url;
    }
    public static String getUrlTest() {
        return testUrl;
    }
    private static void setUrlTest(String urlTest) {testUrl = urlTest;
    }
    public static void setUrl(String url_) {
        url = url_;
    }


    static String newBdName = "CREATE DATABASE LibraryDB2 ";
static String newBd = /*"CREATE DATABASE LibraryDB2 " +*/
        " USE LibraryDB2 " +
        "CREATE TABLE Author (" +
        "    Author_id INT PRIMARY KEY IDENTITY(1,1)," +
        "    FirstName VARCHAR(255)," +
        "    LastName VARCHAR(255)" +
        ") " +
        "CREATE TABLE Publisher (" +
        "    Publisher_id INT PRIMARY KEY IDENTITY(1,1)," +
        "    PublisherName VARCHAR(255)" +
        ") " +
        "CREATE TABLE Genre (" +
        "    Genre_id INT PRIMARY KEY IDENTITY(1,1)," +
        "    GenreName VARCHAR(255)" +
        ") " +
        "CREATE TABLE Book (" +
        "    id INT PRIMARY KEY IDENTITY(1,1)," +
        "    title VARCHAR(255)," +
        "    price DECIMAL(10)," +
        "    pages INT," +
        "    isbn VARCHAR(13)," +
        "    author_id INT," +
        "    publisher_id INT," +
        "    genre_id INT," +
        "    FOREIGN KEY (author_id) REFERENCES Author(Author_id)," +
        "    FOREIGN KEY (publisher_id) REFERENCES Publisher(Publisher_id)," +
        "    FOREIGN KEY (genre_id) REFERENCES Genre(Genre_id)" +
        ") ";

    //-------------------------------------------------------------------------

}
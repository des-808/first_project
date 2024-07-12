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

    private static String url;
    private static final String testUrl ="jdbc:sqlserver://localhost:1433;database=master;IntegratedSecurity=False;TrustServerCertificate=True;ConnectTimeout=30;";
    private static String user = "ssa";
    private static String pass = "ssa";
    /*public DatabaseHelper(String url_, String user_, String pass_) {
        url = url_;
        user = user_;
        pass = pass_;
    }*/
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
        setUrl(props.getProperty("url"));
        setUser(props.getProperty("username"));
        setPass(props.getProperty("password"));
    }

    //функция для проверки соединения с базой данных
    public boolean isConnected() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e);
            return false;
        }
    }

    public static void  checkDatabase() {
        try (Connection connection = connect()) {
            System.out.println("База данных уже существует");
        } catch (SQLException e) {
            System.out.println("База данных не существует. Создаем базу данных...");
            createNewDatabase();
        }
    }
    public static void dropDatabase(String dbName) { try (Connection connection  = connect())  {
        String query  = "DROP DATABASE "+dbName+";";
        Statement statement  = connection.createStatement();
        statement.executeUpdate(query);
        System.out.println("База данных успешно удалена");
    } catch  (SQLException e)  {  System.out.println("Ошибка при удалении базы данных: " + e);
    }
    }

    public static void createNewDatabase() {
        try (Connection connection = connect()) {
                if(connection != null) {
                    String query0 = "Drop DATABASE LibraryTestDB;";
                    String query1 = "CREATE DATABASE LibraryTestDB;";
                    String query2 = "USE LibraryTestDB;";
                    String query3 = "CREATE TABLE Book (id INT PRIMARY KEY IDENTITY(1,1),title VARCHAR(255),author VARCHAR(255),isbn VARCHAR(13));";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query0);
                    statement.executeUpdate(query1);
                    statement.executeUpdate(query2);
                    statement.executeUpdate(query3);
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
    public String getTestUrl() {
        return testUrl;
    }
    public static void setUrl(String url_) {
        url = url_;
    }



   /* //Функция для проверки нескольких таблиц в подключаемой базе данных
    public boolean checkTables(String[] tables) {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())) {
            DatabaseMetaData metaData = connection.getMetaData();
            for (String table : tables) {
                ResultSet tablesResultSet = metaData.getTables(null, null, table, null);
                if (!tablesResultSet.next()) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error checking tables: " + e);
            return false;
        }
    }*/




    //-------------------------------------------------------------------------

}
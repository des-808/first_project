import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
//import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class AppContext{

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    private String user = "";
    private String pass = "";
    private String url = "";
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public AppContext(String url,String user,String pass){
        setUrl( url );
        setUser(user);
        setPass(pass);
    }
    public AppContext(){
        initConnectionProperties();
    }

    public void connect(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getDeclaredConstructor().newInstance();
            //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            try (Connection connection = DriverManager.getConnection(getUrl(), getUser(), getPass())){
            //try (Connection connection = getConnection()){
                System.out.println("Connection to Store DB succesfull!");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        } catch (IOException e){
            System.out.println(e);
        } catch (Exception ex){
            System.out.println(ex);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }

    public void initConnectionProperties() {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        } catch (IOException e){
            System.out.println(e);
        } catch (Exception ex){
            System.out.println(ex);
        }
        setUrl(props.getProperty("url"));
        setUser(props.getProperty("username"));
        setPass(props.getProperty("password"));
    }
    //------------------------------------------------------------------------------------------------------------------
}

/*   public void alternateConnect(){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("ssa");
        ds.setPassword("ssa");
        ds.setServerName("localhost");
        ds.setPortNumber(Integer.parseInt("1433"));
        ds.setDatabaseName("LibraryTestDB");
        ds.setIntegratedSecurity(true);
        ds.setTrustServerCertificate(true);
        try (Connection con = ds.getConnection(); CallableStatement cstmt = con.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");)
        {
            // Execute a stored procedure that returns some data.
            cstmt.setInt(1, 50);
            ResultSet rs = cstmt.executeQuery();

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println("EMPLOYEE: " + rs.getString("LastName") + ", " + rs.getString("FirstName"));
                System.out.println("MANAGER: " + rs.getString("ManagerLastName") + ", " + rs.getString("ManagerFirstName"));
                System.out.println();
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


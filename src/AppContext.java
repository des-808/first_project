import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class AppContext{

   /* public String getUser() {
        return user;
    }*/

    public void setUser(String user) {
        this.user = user;
    }

    /*public String getPass() {
        return pass;
    }*/

    public void setPass(String pass) {
        this.pass = pass;
    }

    /*public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {this.DBName = DBName;}
    public String getSecyrity() {return secyrity;}
    public void setSecyrity(String secyrity) {this.secyrity = secyrity;}
*/
    private String user = "ssa";
    private String pass = "ssa";
    private String host = "localhost";
    private String SqlServer = "MSSQLSERVER";
    private int port = 1433;
    private String DBName = "LibraryTestDB";
    private String secyrity = "";

    public String getStrokaPodkl() {
        return strokaPodkl;
    }

    public void setStrokaPodkl(String strokaPodkl) {
        this.strokaPodkl = strokaPodkl;
    }
/*

    public String getSqlServer() {
        return SqlServer;
    }

    public void setSqlServer(String sqlServer) {
        SqlServer = sqlServer;
    }
*/

    private String strokaPodkl;
 /*   public AppContext(String host,String SqlServer, int port,String DBName,String secyrity, String user, String pass){
        setStrokaPodkl( "jdbc:sqlserver://"+host+"\\"+SqlServer+":"+port+";database="+DBName+";"+secyrity+";" );
        //jdbc:sqlserver://LAPTOP-U7N6H5S8\MSSQLSERVER:1433;database=LibraryTestDB;IntegratedSecurity=True;Connect Timeout=30;TrustServerCertificate=True;//
        setUser(user);
        setPass(pass);
    }
    public AppContext(String strokaPodkl,String user,String pass){
        setStrokaPodkl( strokaPodkl );
        setUser(user);
        setPass(pass);
    }*/
    public AppContext(){}

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

    public void connect(){
        try{
            String url = "jdbc:sqlserver://LAPTOP-U7N6H5S8:1433;database=LibraryTestDB;IntegratedSecurity=False;TrustServerCertificate=True;ConnectTimeout=30;";//
            String username = "ssa";
            String password = "ssa";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getDeclaredConstructor().newInstance();
            //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                System.out.println("Connection to Store DB succesfull!");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }
    public void disconnect(){
        try (Connection conn = DriverManager.getConnection(getStrokaPodkl(), "ssa", "ssa")){
            conn.close();
            // работа с базой данных
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println(e);
        }
    }

    /*init {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        val connectionUrl = strokaPodkl;
                //"jdbc:sqlserver://localhost:1433;encrypt=true;database=DBName;trustServerCertificate=true;"
        connection = DriverManager.getConnection(connectionUrl, getUser(), getPass());
    }*/
}

/*
pom.xml
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>11.2.2.jre11</version>
</dependency>
 */

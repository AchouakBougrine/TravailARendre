package devoir1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class MySQLConnection implements AutoCloseable{ 
    private Connection con;
    private Statement stmt;
    public MySQLConnection(String url, String user, String password) {
        try {
            // Se connecter à la base de données
           Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection(url, user, password);
             // Demander un objet Statement à partir de l'objet Connection
           stmt = con.createStatement();
             System.out.println("Objet Statement créé avec succès.");
             System.out.println("Connexion est réussie à la base de données.");      
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion à la base de données.");
            System.out.println(e);}}
     public Statement getStmt() {
        try {
          return con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; }}
    public Connection getConnection() {
        return con; }
    @Override
    public void close() {
        try {
             if (stmt != null) {
                stmt.close();  }
            if (con != null) {
                con.close();
             System.out.println("Connexion fermée.");}
        } catch (SQLException e) {
            e.printStackTrace(); } }
}
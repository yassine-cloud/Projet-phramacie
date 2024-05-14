package fxjava.projet_pharmacie.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LaConnection {
    private static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/javafx_pharmacie";
    private static final String user = "root";
    private static final String password = "";
    private static final String driver = "com.mysql.jdbc.Driver";

    public static Connection seConnecter() {
        if(connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connexion établie");
            } catch (SQLException e) {
                System.out.println("Erreur de connexion à la base de données avec l'exception SQL Exception : "+e.getMessage());
            }catch (ClassNotFoundException e) {
                System.out.println("Erreur de connexion à la base de données avec l'exception Class Not Found Exception : " + e.getMessage());
            }
        }
        return connection;
    }

//    public static void setUser(String us) {
//        user = us;
//    }
//
//    public static void setPassword(String pass) {
//        password = pass;
//    }

}

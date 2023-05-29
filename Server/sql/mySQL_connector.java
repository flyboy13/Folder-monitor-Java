package sql;

import java.sql.DriverManager;
import java.sql.Connection;

public class mySQL_connector {
    static private Connection con;

    static public void connect() {
        String url = "jdbc:mySQL://localhost:3306/chat_db";
        String userName = "root";
        String password = "";
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Encounter a error in while initialize connection to DB");
        }

    }

    static public Connection getConnection() {
        return con;
    }
}

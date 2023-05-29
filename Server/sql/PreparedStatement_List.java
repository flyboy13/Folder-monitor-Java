package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PreparedStatement_List {
    static public PreparedStatement findOneUserByUsername, insertOneUser;

    static public void initialize() {
        try {
            Connection con = mySQL_connector.getConnection();
            findOneUserByUsername = con.prepareStatement("SELECT * FROM `User` WHERE `Username`=?");
            insertOneUser = con
                    .prepareStatement("INSERT INTO `User`(`Username`, `Password`, `Name`, `Avatar`) VALUES (?,?,?,?)");
        } catch (Exception e) {
            System.out.println("Error in while create PrepareStatements");
        }
    }
}

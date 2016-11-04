package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class Registration {

    public static void register(String ci, String password) throws ClassNotFoundException, SQLException {
        String salt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(password, salt);

        Connection connection = DataBaseConnection.getDataBaseConnection();
        if (connection != null) {
            String insertTableSQL = "INSERT INTO User VALUES"
                    + "(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, ci);
            preparedStatement.setString(2, hashed);
            preparedStatement.setString(3, salt);

            preparedStatement.executeUpdate();
        }
    }

}

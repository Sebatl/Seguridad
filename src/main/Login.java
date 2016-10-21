package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Seba
 */
public class Login {
     public static boolean enter(String ci,String password) throws ClassNotFoundException, SQLException{
        
        Connection connection = DataBaseConnection.getDataBaseConnection();
        if(connection != null){
           	String selectSQL = "SELECT password, salt FROM User WHERE ci = ?";
           PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
           preparedStatement.setString(1, ci);
           
           ResultSet rs = preparedStatement.executeQuery();
           rs.next();
           
           String recoverHash = rs.getString("password");
           //String salt = rs.getString("salt");
            return BCrypt.checkpw(password, recoverHash); //No usa salt?
        }else{
            return false;
        }
     }         
}

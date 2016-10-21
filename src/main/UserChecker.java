package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserChecker {
     public static boolean isRegistered(String ci){
        try{
            Connection connection = DataBaseConnection.getDataBaseConnection();
            if(connection != null){
                    String selectSQL = "SELECT COUNT(ci) FROM User WHERE ci = ?";
               PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
               preparedStatement.setString(1, ci);

               ResultSet rs = preparedStatement.executeQuery();
               rs.next();
                return rs.getInt(1) == 1;
            } else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
     }   
}

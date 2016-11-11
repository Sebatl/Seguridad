/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    public static Connection getDataBaseConnection() {

        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Falla en el driver");
            return null;
        }

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/seguridad", "root", "root");

        } catch (SQLException e) {
            System.out.println("Falla en la conexión con la base de datos");
            return null;
        }

        return connection;

    }

}

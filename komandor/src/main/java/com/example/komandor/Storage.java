package com.example.komandor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Storage {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "testtest";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/cashtest";
    String dropTable = "drop table goods;";
    String createTable = "CREATE table goods (id serial primary key ,name varchar (55),price integer)";
    String sql = "INSERT  into goods(name, price) values ('cappuccino','250'),('milk','30'),('coffee','99')," +
            "('latte','200'),('espresso','150'),('cold water','20'),('hot water','40'),('ise coffee','999')";
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("DONE-connection to the database is successful");
            Statement statement = connection.createStatement();
            statement.executeUpdate(dropTable);
            statement.executeUpdate(createTable);

            int add = statement.executeUpdate(sql);
            if (add > 0) {
                System.out.println("new good is ADD");
            } else {
                System.out.println("new good is NOT_ADD");
            }


            connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR-connection to the database is incomplete");
            e.printStackTrace();
        }
    }

    public Storage() {

    }
}

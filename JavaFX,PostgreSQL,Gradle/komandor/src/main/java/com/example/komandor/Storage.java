package com.example.komandor;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "testtest";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/cashtest";

    String dropTableGoods = "drop table  if exists goods CASCADE";
    String createTableGoods = "CREATE table goods (id serial primary key ,name varchar (55),price integer)";

    String dropTableChecks = "drop table  if exists checks CASCADE";
    String createTableChecks = "CREATE table checks (id serial primary key ,date varchar (55),sum integer)";

    String dropTableChecklines = "drop table  if exists checklines";
    String createTableChecklines = "CREATE table checklines (id serial primary key, id_check integer ,id_good integer references goods(id),number_string integer ,count_goods integer ,total_sum integer )";


    String addGoodsInTable = "INSERT  into goods(name, price) values ('cappuccino','250'),('milk','30'),('coffee','99')," +
            "('latte','200'),('espresso','150'),('cold water','20'),('hot water','40'),('ise coffee','999')";
    static Connection connection;
    static Statement statement;

    {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("DONE-connection to the database is successful");
            statement = connection.createStatement();

            initTableGoods(statement);
            initTableChecks(statement);
            initTableChecklines(statement);

            //connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR-connection to the database is incomplete");
            e.printStackTrace();
        }
    }

    public Storage() {
    }

    static public void addCheck() throws SQLException {
        System.out.println("add check in db");
        //todo добовление чека в таблицу

    }

    private void initTableGoods(Statement statement) throws SQLException {
        System.out.println("start initGoods");
        statement.executeUpdate(dropTableGoods);
        statement.executeUpdate(createTableGoods);

        int add = statement.executeUpdate(addGoodsInTable);
        if (add > 0) {
            System.out.println("new good is ADD");
        } else {
            System.out.println("new good is NOT_ADD");
        }
    }

    private void initTableChecks(Statement statement) throws SQLException {
        System.out.println("start initChecks");
        statement.executeUpdate(dropTableChecks);
        statement.executeUpdate(createTableChecks);
    }

    private void initTableChecklines(Statement statement) throws SQLException {
        System.out.println("start initChecklines");
        statement.executeUpdate(dropTableChecklines);
        statement.executeUpdate(createTableChecklines);
    }

    public static void addCheckInTableChecks(Date date, Integer sum) throws SQLException {
        System.out.println("addCheckInTableChecks--------------------");
        // statement = connection.createStatement();

        String addCheck = "INSERT  into checks(date, sum) values ('" + date + "','" + sum + "')";
        statement.executeUpdate(addCheck);
        String getId = "SELECT id FROM public.checks where sum in ("+sum+")";
        ResultSet resultSet = statement.executeQuery(getId);
//        int id=resultSet.getInt("sum");
//        System.out.println("id from db= "+id);
        //System.out.println(resultSet.getString("sum"));
        //statement.close();
    }

    public static void addCheckInTableChecklines(Integer id_check, Integer id_good, Integer number_string, Integer count_goods, Integer total_sum) throws SQLException {
        System.out.println("addCheckInTableChecklines--------------------");

        String addChecklines = "INSERT  into checklines(id_check,id_good, number_string, count_goods, total_sum) values " +
                "('" + id_check + "','" + id_good + "','" + number_string + "','" + count_goods + "','" + total_sum + "')";

        statement.executeUpdate(addChecklines);

    }

    public static void addCheckInTableChecklines(ObservableList<Order> listOrder) throws SQLException {
        System.out.println("addCheckInTableChecklines--------------------");
        int size = listOrder.size();
        AtomicInteger numderString = new AtomicInteger(0);
        listOrder.forEach(s -> {
            try {
                String name = s.getNameInBasket();
                String select = "SELECT id, name, price FROM public.goods where name in ('" + name + "')";
                ResultSet resultSet = statement.executeQuery(select);
                int columns = resultSet.getMetaData().getColumnCount();
                System.out.println("count ResultSet  = " + columns);
                while (resultSet.next()) {
                    for (int i = 1; i <= columns; i++) {
                        System.out.print(resultSet.getString(i) + "\t");
                    }
                    System.out.println();
                }
                System.out.println(resultSet.toString() +
                        resultSet.findColumn("id") +
                        resultSet.findColumn("name" +
                                resultSet.findColumn("price")));
                System.out.println(resultSet.getCursorName());

            } catch (SQLException e) {
                e.printStackTrace();
            }
            numderString.getAndIncrement();
            Integer id_check=1;
            Integer id_good=1;
            AtomicInteger number_string=numderString;
            Integer count_goods = s.getCountInBasket();
            Integer total_sum = s.getTotalInBasket();
            String addChecklines = "INSERT  into checklines(id_check, id_good, number_string, count_goods, total_sum) values ('" + id_check + "','" + id_good + "','" + number_string + "','" + count_goods + "','" + total_sum + "')";

            try {
                statement.executeUpdate(addChecklines);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        System.out.println("count goods in order = " + size);
    }

    public static void getGood() throws SQLException {
        System.out.println("addCheckInTableChecklines--------------------");

    }


}

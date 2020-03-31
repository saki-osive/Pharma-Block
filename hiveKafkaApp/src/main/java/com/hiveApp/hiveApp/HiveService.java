package com.hiveApp.hiveApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class HiveService {


    @Autowired
    public SimpleDriverDataSource dataSource;


    public void testConnection() throws SQLException {

        Connection connection = dataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("");

        ResultSet res = ps.executeQuery();

        while (res.next()) {
            System.out.println(res);
        }

        res.close();
        ps.close();
        connection.close();
    }


    public boolean createTaskTable(){
        boolean result = false;

        try {
            Connection connection = dataSource.getConnection();

            Statement stmt = connection.createStatement();

            ResultSet show_tables = stmt.executeQuery("show tables");
            while (show_tables.next()) {
                System.out.println(show_tables.getString(1));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();


        }



        return result;
    }


    public boolean createNewTaskEntry(String name,String dateStr, String source){
        boolean result = true;

        try {
            Connection connection = dataSource.getConnection();

            Statement stmt = connection.createStatement();

            String query = String.format("INSERT INTO TABLE tasks VALUES (%s,'%s', '%s', '%s')", "1", name,dateStr,source);
            stmt.execute(query);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;

        }



        return result;
    }

}

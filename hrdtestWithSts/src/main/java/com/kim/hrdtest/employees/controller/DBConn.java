package com.kim.hrdtest.employees.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class DBConn {
    @Autowired
    private DataSource dataSource;

    @GetMapping("/employees/dbconn")
    public String dbConn(){
        try (Connection connection =dataSource.getConnection()){
            String result = connection.getMetaData().getURL();
            return "DB 연결성공 : " + result;
        }catch (Exception e){
            e.fillInStackTrace();
            return "DB 연결실패  ";
        }

    }
}

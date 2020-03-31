package com.hiveApp.hiveApp;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    HiveService hiveService;

    @GetMapping(value = "/")
    public String index(){
        return "hello";
    }

    @GetMapping(value = "/createTable")
    public String createTable(){

        String result = "";

        if (hiveService.createTaskTable()){
            result = "Table created";
        }else  {
            result = "Table creation failed";
        }

        return result;
    }

    @GetMapping(value = "/add")
    public String add(){

        String result = "";

        String dateStr = new DateTime().toString();
        if (hiveService.createNewTaskEntry("demo",dateStr,"kafka")){
            result = "Entry created";
        }else  {
            result = "Failed";
        }

        return result;
    }

}

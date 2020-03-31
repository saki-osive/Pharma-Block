package com.hiveApp.hiveApp.kafka.bo;

import java.util.Date;

public class TaskBO {

    private String taskName;

    private Date createdDate;

    public TaskBO(){

    }

    public TaskBO(String name,Date createdDate){
        this.taskName = name;
        this.createdDate = createdDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

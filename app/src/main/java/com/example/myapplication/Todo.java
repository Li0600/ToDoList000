package com.example.myapplication;

public class Todo {
    public int id;
    public String detail;
    public Todo(){
        super();
        detail="";
    }
    public Todo(String detail){
        super();
        this.detail=detail;
    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getDetail(){return detail;}
    public void setDetail(String detail){this.detail=detail;}
}

package com.example.myapplication;

public class User {
    public int id;
    public String phone;
    public String username;
    public String password;
    public User(){
        super();
        phone="";
        username="";
        password="";
    }
    public User(String phone,String username,String password){
        super();
        this.phone=phone;
        this.username=username;
        this.password=password;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;
    }

    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone=phone;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
}

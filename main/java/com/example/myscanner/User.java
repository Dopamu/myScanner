package com.example.myscanner;


// save  users details
public class User {
    String fullname, username, password;
    int student_id;


    // for when a user registers
    public User(String fullname, String username, String password, int student_id){
        this.fullname = fullname;
        ;this.username = username;
        this.password = password;
        this.student_id = student_id;
    }

    // when a user wish to log in
    public User(String fullname, String password){
        this.username = username;
        this.password = password;

        // default value
        this.student_id = 0;
        this.fullname = "";
    }
}

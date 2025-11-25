package com.example.group14app;

public class Users {
    private final String username;
    private final String password;

    private final String fullName;
    private final int id;


    public Users(String username, String password, String fullName, int id) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getId() {
        return id;
    }




}

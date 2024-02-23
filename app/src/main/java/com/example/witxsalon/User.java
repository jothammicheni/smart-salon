package com.example.witxsalon;
public class User {
    String email;
    String name;
    String password;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

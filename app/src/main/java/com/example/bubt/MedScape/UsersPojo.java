package com.example.bubt.MedScape;

public class UsersPojo {
    private String email;
    private String password;

    public UsersPojo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UsersPojo() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}

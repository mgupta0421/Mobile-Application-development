package com.example.week_3;

import java.io.Serializable;

public class Registration implements Serializable {
    String name;
    String email;
    String id;
    String department;

    public Registration(String name, String email, String id, String department) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.department = department;
    }

    public Registration() {
    }
}

package com.example.week4;

import java.io.Serializable;

public class Profile implements Serializable {
    String name;
    double age;

    public Profile(String name, double age) {
        this.name = name;
        this.age = age;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

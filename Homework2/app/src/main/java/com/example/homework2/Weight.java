package com.example.homework2;

import java.io.Serializable;

public class Weight implements Serializable {
    double weight;
    String gender;

    public Weight(double weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }

    public Weight() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

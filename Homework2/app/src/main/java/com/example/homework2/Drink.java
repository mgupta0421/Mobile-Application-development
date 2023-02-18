package com.example.homework2;

import java.util.Date;

public class Drink {
    Double alcohol, size;
    Date addedOn;

    public Drink(Double alcohol, Double size, Date addedOn) {
        this.alcohol = alcohol;
        this.size = size;
        this.addedOn = addedOn;
    }

    public Drink(Date addedOn) {
        this.addedOn = addedOn;
    }
}

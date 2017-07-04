package com.example.tomislavkralj.animals;

import java.util.Date;

/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public abstract class Pig extends Animals{

    public Pig(int id, boolean gender, int weight, Date dateOfBirth, String feed, boolean isAlive, int idMother, int idFather) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
    }

    protected Pig() {
    }
}

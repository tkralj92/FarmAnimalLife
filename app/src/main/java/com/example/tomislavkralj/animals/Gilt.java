package com.example.tomislavkralj.animals;

import java.util.Date;

/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public class Gilt extends Pig {

    //false = not pregnant, true = pregnant
    private boolean pregnant;

    public Gilt(int id, boolean gender, int weight, Date dateOfBirth, String feed, boolean isAlive, int idMother, int idFather) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
    }

    public Gilt(int id, boolean gender, int weight, Date dateOfBirth, String feed, boolean isAlive, int idMother, int idFather, boolean pregnant) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
        this.pregnant = pregnant;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }
}

package com.example.tomislavkralj.animals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public abstract class Animals{

    protected DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    protected int id;
    //0 = male, 1=female
    protected boolean gender;
    protected int weight;
    protected Date dateOfBirth;
    protected String feed;
    //0 = dead animal, 1= alive animal;
    protected boolean isAlive;
    protected int idMother;
    protected int idFather;

    public Animals() {
    }

    public Animals(int id, boolean gender, int weight, Date dateOfBirth, String feed, boolean isAlive, int idMother, int idFather) {
        this.id = id;
        this.gender = gender;
        this.weight = weight;
        this.dateOfBirth = dateOfBirth;
        this.feed = feed;
        this.isAlive = isAlive;
        this.idMother = idMother;
        this.idFather = idFather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getIdMother() {
        return idMother;
    }

    public void setIdMother(int idMother) {
        this.idMother = idMother;
    }

    public int getIdFather() {
        return idFather;
    }

    public void setIdFather(int idFather) {
        this.idFather = idFather;
    }
}

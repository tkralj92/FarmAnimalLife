package com.example.tomislavkralj.animals;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by tomislav.kralj on 29.5.2017..
 */
//Krmaca
public class Sow extends Pig implements Parcelable{
    //false = not pregnant, true = pregnant
    private int pregnant;
    private int numberOfBirths;
    private double percentageOfMortality;
    private double numOfchildrenPerBirth;

    public Sow(){
        super();
    }

    public Sow(int id, boolean gender, int weight, Date dateOfBirth, String feed,
               boolean isAlive, int idMother, int idFather) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
    }

    public Sow(int id, boolean gender, int weight, Date dateOfBirth,
               String feed, boolean isAlive, int idMother, int idFather,
               int pregnant, int numberOfBirths) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
        this.pregnant = pregnant;
        this.numberOfBirths = numberOfBirths;
    }

    public Sow(int id, boolean gender, int weight, Date dateOfBirth, String feed,
               boolean isAlive, int idMother, int idFather, int pregnant,
               int numberOfBirths, double precentOfMortality, double numOfchildrenPerBirth) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
        this.pregnant = pregnant;
        this.numberOfBirths = numberOfBirths;
        this.percentageOfMortality = precentOfMortality;
        this.numOfchildrenPerBirth = numOfchildrenPerBirth;
    }

    public int isPregnant() {
        return pregnant;
    }

    public void setPregnant(int pregnant) {
        this.pregnant = pregnant;
    }

    public int getNumberOfBirths() {
        return numberOfBirths;
    }

    public void setNumberOfBirths(int numberOfBirths) {
        this.numberOfBirths = numberOfBirths;
    }

    public double getPrecentOfMortality() {
        return percentageOfMortality;
    }

    public void setPrecentOfMortality(double precentOfMortality) {
        this.percentageOfMortality = precentOfMortality;
    }

    public double getNumOfchildrenPerBirth() {
        return numOfchildrenPerBirth;
    }

    public void setNumOfchildrenPerBirth(double numOfchildrenPerBirth) {
        this.numOfchildrenPerBirth = numOfchildrenPerBirth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(gender ? 0 : 1);
        dest.writeInt(weight);
        dest.writeLong(dateOfBirth.getTime());
        dest.writeString(feed);
        dest.writeString(Boolean.toString(isAlive));
        dest.writeInt(idMother);
        dest.writeInt(idFather);
        dest.writeInt(pregnant);
        dest.writeInt(numberOfBirths);
        dest.writeDouble(percentageOfMortality);
        dest.writeDouble(numOfchildrenPerBirth);
    }

    public static final Parcelable.Creator CREATOR
            =new Parcelable.Creator() {
        public Sow createFromParcel(Parcel in) {
            return new Sow(in);
        }
        public Sow[] newArray(int size) {
            return new Sow[size];
        }
    };

    public Sow(Parcel in){

        id = in.readInt();
        int i = in.readInt();
        gender = true;
        weight=in.readInt();
        dateOfBirth = new Date(in.readLong());
        feed = in.readString();
        isAlive = Boolean.getBoolean(in.readString());
        idMother = in.readInt();
        idFather = in.readInt();
        pregnant = in.readInt();
        numberOfBirths = in.readInt();
        percentageOfMortality = in.readDouble();
        numOfchildrenPerBirth = in.readDouble();
    }
}

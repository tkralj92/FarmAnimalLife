package com.example.tomislavkralj.animals;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public class Hog extends Pig implements Parcelable{

    private double percentageOfSuccPerpregnancys;
    private int numOfChildrenPerPregnancy;
    private double percentageOfMortality;

    public Hog(int id, boolean gender, int weight, Date dateOfBirth, String feed, boolean isAlive, int idMother, int idFather, double percentageOfSuccPerpregnancys, int numOfChildrenPerPregnancy, double percentageOfMortality) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
        this.percentageOfSuccPerpregnancys = percentageOfSuccPerpregnancys;
        this.numOfChildrenPerPregnancy = numOfChildrenPerPregnancy;
        this.percentageOfMortality = percentageOfMortality;
    }

    public Hog(){
        super();
    }

    public Hog(int id, boolean gender, int weight, Date dateOfBirth, String feed, boolean isAlive, int idMother, int idFather) {
        super(id, gender, weight, dateOfBirth, feed, isAlive, idMother, idFather);
    }

    public double getPercentageOfSuccPerpregnancys() {
        return percentageOfSuccPerpregnancys;
    }

    public void setPercentageOfSuccPerpregnancys(double percentageOfSuccPerpregnancys) {
        this.percentageOfSuccPerpregnancys = percentageOfSuccPerpregnancys;
    }

    public int getNumOfChildrenPerPregnancy() {
        return numOfChildrenPerPregnancy;
    }

    public void setNumOfChildrenPerPregnancy(int numOfChildrenPerPregnancy) {
        this.numOfChildrenPerPregnancy = numOfChildrenPerPregnancy;
    }

    public double getPercentageOfMortality() {
        return percentageOfMortality;
    }

    public void setPercentageOfMortality(double percentageOfMortality) {
        this.percentageOfMortality = percentageOfMortality;
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
        String str = format.format(dateOfBirth);
        dest.writeString(str);
        dest.writeString(feed);
        dest.writeString(Boolean.toString(isAlive));
        dest.writeInt(idMother);
        dest.writeInt(idFather);
        dest.writeDouble(percentageOfSuccPerpregnancys);
        dest.writeInt(numOfChildrenPerPregnancy);
        dest.writeDouble(percentageOfMortality);
    }

    public static final Parcelable.Creator CREATOR
            =new Parcelable.Creator() {
        public Hog createFromParcel(Parcel in) {
            return new Hog(in);
        }
        public Hog[] newArray(int size) {
            return new Hog[size];
        }
    };

    public Hog(Parcel in){
        id = in.readInt();
        int i = in.readInt();
        gender = false;
        weight=in.readInt();
        try {
            String str  = in.readString();
            Date date = format.parse(str);
            dateOfBirth = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        feed = in.readString();
        isAlive = Boolean.getBoolean(in.readString());
        idMother = in.readInt();
        idFather = in.readInt();
        percentageOfSuccPerpregnancys = in.readDouble();
        numOfChildrenPerPregnancy = in.readInt();
        percentageOfMortality = in.readDouble();
    }

}

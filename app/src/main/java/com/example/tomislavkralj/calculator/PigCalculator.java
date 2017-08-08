package com.example.tomislavkralj.calculator;

import android.content.Context;

import com.example.tomislavkralj.feed.Feed;
import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;

public class PigCalculator {

    public String calculateDaysUntillWantedWeight(Context context, double wantedWeight, double weightNow, double feedKG, String feedName){

        MyDbHelper myDb = new MyDbHelper(context);
        int daysItTakes = 0;
        double extraFeedKg = 0;
        double feedCalories = 0;

        try {
            Feed feed = new Feed();
            feed = myDb.getFeedByName(feedName);
            feedCalories = feed.getFeedCalories();
        } catch (Exception e) {
            e.printStackTrace();
        }
        feedKG = feedKG * (feedCalories / 385);


        while (weightNow <= wantedWeight) {
            if (weightNow < 20) {
                if (feedKG < 1.3) {
                    weightNow += (feedKG / 1.3) * 0.60;
                    extraFeedKg -= 1.3 - feedKG;
                } else {
                    weightNow += 0.60;
                    extraFeedKg -= 1.3 - feedKG;
                }
            } else if (weightNow >= 20 && weightNow < 50) {
                if (feedKG < 2) {
                    weightNow += (feedKG / 2.0) * 0.67;
                    extraFeedKg -= 2 - feedKG;
                } else {
                    weightNow += 0.67;
                    extraFeedKg -= 2 - feedKG;
                }
            } else if (weightNow >= 50 && weightNow < 110) {
                if (feedKG < 2.5) {
                    weightNow += (feedKG / 2.5) * 0.81;
                    extraFeedKg -= 2.5 - feedKG;
                } else {
                    weightNow += 0.81;
                    extraFeedKg -= 2.5 - feedKG;
                }
            } else if (weightNow >= 110 && weightNow < 140) {
                if (feedKG < 3.3) {
                    weightNow += (feedKG / 3.3) * 0.76;
                    extraFeedKg -= 3.3 - feedKG;
                } else {
                    weightNow += 0.76;
                    extraFeedKg -= 3.3 - feedKG;
                }
            } else {
                if (feedKG < 4.5) {
                    weightNow += (feedKG / 4.5) * 0.69;
                    extraFeedKg -= 4.5 - feedKG;
                } else {
                    weightNow += 0.69;
                    extraFeedKg -= 4.5 - feedKG;
                }
            }
            daysItTakes++;
        }

        if (extraFeedKg < 0) {
            extraFeedKg *= -1;
           return ("Pig needs " + Integer.toString((int) Math.round(daysItTakes)) + " days and you have to give " + Integer.toString((int) Math.round(extraFeedKg)) + "kg of extra feed");
        } else {
            return ("Pig needs " + Integer.toString((int) Math.round(daysItTakes)) + " days and you have given " + Integer.toString((int) Math.round(extraFeedKg)) + "kg of extra feed");
        }

    }

    public String calculateMortalityRate(Context context, Pig pig){

        MyDbHelper myDb = new MyDbHelper(context);
        Sow mother = (Sow) myDb.getPig(pig.getIdMother());
        Hog father = (Hog) myDb.getPig(pig.getIdFather());
        double mortality;

        if(pig.isGender()){
            Sow sow = (Sow) pig;
            if(pig.getIdFather() != 0 && pig.getIdMother() != 0){
                mortality = 0.5 * father.getPercentageOfMortality() + 0.5 * mother.getPrecentOfMortality()
                        + sow.getPrecentOfMortality();
                mortality /= 2.0;
            }else if(pig.getIdFather() != 0 && pig.getIdMother() == 0){
                mortality = 0.5 * father.getPercentageOfMortality() + sow.getPrecentOfMortality();
                mortality /= 1.5;
            }else if(pig.getIdFather() == 0 && pig.getIdMother() != 0) {
                mortality = 0.5 * mother.getPrecentOfMortality() + sow.getPrecentOfMortality();
                mortality /= 1.5;
            }else{
                mortality = sow.getPrecentOfMortality();
            }
        }else{
            Hog hog = (Hog) pig;
            if(pig.getIdFather() != 0 && pig.getIdMother() != 0) {
                mortality = 0.5 * father.getPercentageOfMortality() + 0.5 * mother.getPrecentOfMortality()
                        + hog.getPercentageOfMortality();
                mortality /= 2.0;
            }else if(pig.getIdFather() != 0 && pig.getIdMother() == 0){
                mortality = 0.5 * father.getPercentageOfMortality() + hog.getPercentageOfMortality();
                mortality /= 1.5;
            }else if(pig.getIdFather() == 0 && pig.getIdMother() != 0){
                mortality = 0.5 * mother.getPrecentOfMortality() + hog.getPercentageOfMortality();
                mortality /= 1.5;
            }else{
                mortality = hog.getPercentageOfMortality();
            }
        }
        return ("Piglet mortality: " + Double.toString(mortality*100) + "%");
    }

    public String calculatePigletsPerBirth(Context context, Pig pig) {

        MyDbHelper myDb = new MyDbHelper(context);
        Sow mother = (Sow) myDb.getPig(pig.getIdMother());
        Hog father = (Hog) myDb.getPig(pig.getIdFather());
        double pigsPerBirth;

        if (pig.isGender()) {
            Sow sow = (Sow) pig;
            if (pig.getIdFather() != 0 && pig.getIdMother() != 0) {
                if (sow.getNumberOfBirths() > 0) {
                    pigsPerBirth = 0.5 * father.getNumOfChildrenPerPregnancy() + 0.5 * mother.getNumOfchildrenPerBirth()
                            + sow.getNumOfchildrenPerBirth();
                    pigsPerBirth /= 2.0;
                } else {
                    pigsPerBirth = 0.5 * father.getNumOfChildrenPerPregnancy() + 0.5 * mother.getNumOfchildrenPerBirth();
                }
            } else if (pig.getIdFather() != 0 && pig.getIdMother() == 0) {
                if (sow.getNumberOfBirths() > 0) {
                    pigsPerBirth = 0.5 * father.getNumOfChildrenPerPregnancy() + sow.getNumOfchildrenPerBirth();
                    pigsPerBirth /= 1.5;
                } else {
                    pigsPerBirth = father.getNumOfChildrenPerPregnancy();
                }
            } else if (pig.getIdFather() == 0 && pig.getIdMother() != 0) {
                if (sow.getNumberOfBirths() > 0) {
                    pigsPerBirth = 0.5 * mother.getNumOfchildrenPerBirth() + sow.getNumOfchildrenPerBirth();
                    pigsPerBirth /= 1.5;
                } else {
                    pigsPerBirth = mother.getNumOfchildrenPerBirth();
                }
            } else {
                pigsPerBirth = sow.getPrecentOfMortality();
            }
        } else {
            Hog hog = (Hog) pig;
            if (hog.getNumOfChildrenPerPregnancy() == 0) {
                if (pig.getIdFather() != 0 && pig.getIdMother() != 0) {
                    pigsPerBirth = 0.5 * father.getNumOfChildrenPerPregnancy() + 0.5 * mother.getNumOfchildrenPerBirth();
                } else if (pig.getIdFather() != 0 && pig.getIdMother() == 0) {
                    pigsPerBirth = father.getNumOfChildrenPerPregnancy();
                } else if (pig.getIdFather() == 0 && pig.getIdMother() != 0) {
                    pigsPerBirth = mother.getNumOfchildrenPerBirth();
                } else {
                    pigsPerBirth = hog.getNumOfChildrenPerPregnancy();
                }
            } else {
                if (pig.getIdFather() != 0 && pig.getIdMother() != 0) {
                    pigsPerBirth = 0.5 * father.getNumOfChildrenPerPregnancy() + 0.5 * mother.getNumOfchildrenPerBirth()
                            + hog.getNumOfChildrenPerPregnancy();
                    pigsPerBirth /= 2.0;
                } else if (pig.getIdFather() != 0 && pig.getIdMother() == 0) {
                    pigsPerBirth = 0.5 * father.getNumOfChildrenPerPregnancy() + hog.getNumOfChildrenPerPregnancy();
                    pigsPerBirth /= 1.5;
                } else if (pig.getIdFather() == 0 && pig.getIdMother() != 0) {
                    pigsPerBirth = 0.5 * mother.getNumOfchildrenPerBirth() + hog.getNumOfChildrenPerPregnancy();
                    pigsPerBirth /= 1.5;
                } else {
                    pigsPerBirth = hog.getNumOfChildrenPerPregnancy();
                }
            }
        }
        return ("Piglet per birth: " + Double.toString(pigsPerBirth*100));
    }
}
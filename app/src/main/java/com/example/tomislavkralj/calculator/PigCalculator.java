package com.example.tomislavkralj.calculator;

public class PigCalculator {

    public String calculateDays(double wantedWeight,double weightNow, double feedKG){

        int daysItTakes = 0;
        double extraFeedKg = 0;

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
}

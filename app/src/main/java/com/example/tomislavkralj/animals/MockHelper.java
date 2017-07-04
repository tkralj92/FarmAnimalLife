package com.example.tomislavkralj.animals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public class MockHelper {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");

    public static ArrayList<Sow> getPigs() {
        //Feed feed = new Feed("kukuruz", 120, 10);
        Sow sow = new Sow(1, true, 123, new Date(), "kukuruz", true, 2, 3, 0, 11, 0.2, 3);
        Sow sow2 = new Sow(2, true, 223, new Date(), "soja", true, 4, 3, 1, 12, 0.1, 1);
        Sow sow3 = new Sow(3, true, 22, new Date(), "majmun", true, 4, 3, 1, 13, 0.5, 3);
        Sow sow4 = new Sow(4, true, 89, new Date(), "soja", true, 4, 3, 0, 14, 0.55, 1);

        List<Sow> listaSvinja = new ArrayList<Sow>();
        listaSvinja.add(sow);
        listaSvinja.add(sow2);
        listaSvinja.add(sow3);
        listaSvinja.add(sow4);
        return (ArrayList<Sow>) listaSvinja;
    }

    public static ArrayList<Hog> getHogs() {
       //s Feed feed = new Feed("kukuruz", 120, 10);
        Hog sow = new Hog(5, false, 123, new Date(), "kukuruz", true, 2, 3, 0.1, 5, 0.12);
        Hog sow2 = new Hog(6, false, 223, new Date(), "soja", true, 4, 3, 0.3, 9, 0.13);
        Hog sow3 = new Hog(7, false, 22133, new Date(), "majmun", true, 4, 3, 0.75, 7, 0.14);
        Hog sow4 = new Hog(8, false, 2123, new Date(), "soja", true, 4, 3, 0.82, 5, 0.15);

        List<Hog> listaSvinja = new ArrayList<Hog>();
        listaSvinja.add(sow);
        listaSvinja.add(sow2);
        listaSvinja.add(sow3);
        listaSvinja.add(sow4);
        return (ArrayList<Hog>) listaSvinja;
    }

    public static ArrayList<Pig> getAllPigs() {
        List<Sow> listaSvinja1 = new ArrayList<Sow>(getPigs());
        List<Hog> listaSvinja2 = new ArrayList<Hog>(getHogs());
        List<Pig> listaSvinja3 = new ArrayList<Pig>();
        listaSvinja3.addAll(listaSvinja1);
        listaSvinja3.addAll(listaSvinja2);
        return (ArrayList<Pig>) listaSvinja3;
    }
}

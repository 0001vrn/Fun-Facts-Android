package com.studio.varun.funfacts;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Varun on 01-10-2017.
 */

public class ColorWheel {
    private static String[] mColors = {
            "#39add1",
            "#3079ab",
            "#c25975",
            "#e15258",
            "#f9845b",
            "#838cc7",
            "#7d669e",
            "#53bbb4",
            "#51b46d",
            "#e0ab18",
            "#637a91",
            "#f092b0",
            "#b7c0c7",
    };

    public static int getColor() {


        String color = "";

        // Randomly select a fact
        Random randomGenerator = new Random(); // construct new random generator

        int randomNumber = randomGenerator.nextInt(10);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color+"");
        return colorAsInt;


    }
}

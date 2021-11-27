package com.openclassrooms.realestatemanager;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void getTodayDate() {
        // WHEN
        String date = Utils.getTodayDate();

        // THEN
        assertEquals(date.substring(0, 2), String.valueOf(new Date().getDate()));
        assertEquals(date.substring(3, 5), String.valueOf(new Date().getMonth() + 1));
        assertEquals(date.substring(6, 10), String.valueOf(new Date().getYear() + 1900));
    }

    @Test
    public void convertDollarToEuro(){
        // WHEN
        int result = Utils.convertDollarToEuro(500);

        // THEN
        assertEquals(560, result);
    }

    @Test
    public void convertEuroToDollars(){
        // WHEN
        int result = Utils.convertEuroToDollars(500);

        // THEN
        assertEquals(440, result);
    }
}

package com.example.advokat.cleanenergy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /*public static int getFirstYear(Date dateStart) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int firstYear = Integer.parseInt(simpleDateFormat.format(dateStart));
        return firstYear;
    }

    public static int getSecondYear(Date dateStop) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int secondYear = Integer.parseInt(simpleDateFormat.format(dateStop));
        return secondYear;
    }

    public static int getFirstMonth(Date dateStart) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        int firstMonth = Integer.parseInt(simpleDateFormat.format(dateStart));
        return firstMonth;
    }

    public static int getSecondMonth(Date dateStop) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        int secondMonth = Integer.parseInt(simpleDateFormat.format(dateStop));
        return secondMonth;
    }*/

    public static int getYear(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(simpleDateFormat.format(date));
        return year;
    }

    public static int getMonth(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        int month = Integer.parseInt(simpleDateFormat.format(date));
        return month;
    }

    public static boolean isValidBetweenDates(Date dateStart, Date dateStop) {
        return dateStop.compareTo(dateStart) == 1;
    }

    public static boolean isValidStringBetweenDates(String dateStart, String dateStop) {
        return dateStart.equals(dateStop);
    }
}

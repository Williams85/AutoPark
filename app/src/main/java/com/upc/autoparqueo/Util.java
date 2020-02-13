package com.upc.autoparqueo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {


    public static String formatDate(Date date){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String strDate = simpleDateFormat.format(date);
        return strDate;
    }
    public static Date formatStringtoDate(String strDate){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatHour(String hora){

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat1.format(date);
    }

    public static Date formatHourString(String hora){

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

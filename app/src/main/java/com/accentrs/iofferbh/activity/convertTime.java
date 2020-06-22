package com.accentrs.iofferbh.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class convertTime {

//    public Date Time (String T){
//        Date date = null;
//        //String dtStart = "2010-10-15T09:27:37Z";
//        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy",  Locale.ENGLISH);
//        //        try {);
//        try {
//             date = format.parse(T);
//            //System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    return date;
//    }

    private static SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-mm-dd");
    private static SimpleDateFormat outSDF = new SimpleDateFormat("dd/mm/yyyy");

    public  String formatDate(String inDate) {
        String outDate = "";
        if (inDate != null) {
            try {
                Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);
            } catch (ParseException ex){
            }
        }
        return outDate;
    }
}

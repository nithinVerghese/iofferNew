package com.accentrs.iofferbh.activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class nfc_test {

    public void test(){

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        DecimalFormat dc = (DecimalFormat)nf;
        dc.applyPattern("");
    }

}

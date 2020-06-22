package com.accentrs.iofferbh.activity;

import java.io.IOException;

public class payment_debit {

    public static void main(String[] args) throws IOException {

    }

    public String pay(String amount,String description) {


        String currentTime = String.valueOf(System.currentTimeMillis()); //timestamp
        String paymentchannel = "BHBENEFITDC";
        String isysid = currentTime.substring(0, currentTime.length() - 1);
        //String amount = "0.5";
        //String description = "Description of service";
        String description2 = "";
        String tunnel = "isys";
        String currency = "BHD";
        String language = "EN";
        String country = "BH";
        String merchant_name = "Ioffer";
        String akey = "d9UHaWgikPDqT022";
        String timestamp = currentTime.substring(0, currentTime.length() - 3);
        String rnd = "";
        String original = "bN0EuuC9EywY/vPkeW0TEryVYxCMSEoSAsENqlGkcPA=";
        String dataToComputeHash = paymentchannel + "paymentchannel" + isysid
                + "isysid" + amount + "amount"
                + timestamp + "timestamp" + description + "description"
                + rnd + "rnd" + original + "original";

        String decryptedOriginal = "ZTi2s2bubkAAWo6o";


        String hma = new hmac_SHA256().hmacDigest(dataToComputeHash, decryptedOriginal, "HMACSHA256");
        System.out.println("hash value  " + hma + "\n");


        String merchantResponseUrl = "https://pay-it.mobi/";

        String path = "https://pay-it.mobi/globalpayit/pciglobal/WebForms/Payitcheckoutservice%20.aspx";
        String url = path + "?country=" + country +
                "&paymentchannel=" + paymentchannel +
                "&isysid=" + isysid +
                "&amount=" + amount +
                "&tunnel=" + tunnel +
                "&description=" + description +
                "&description2=" + description2 +
                "&currency=" + currency +
                "&Responseurl=" + merchantResponseUrl +
                "&merchant_name=" + merchant_name +
                "&akey=" + akey +
                "&hash=" + hma.toUpperCase() +
                "&original=" + original +
                "&timestamp=" + timestamp +
                "&rnd=" + rnd;


        return  url;


    }

}

package com.accentrs.iofferbh.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import com.accentrs.iofferbh.interfaces.ExponentialBackoffRunnable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Utils {

    // Progress Dialog
    private static ProgressDialog pDialog;
    private static boolean progressAlive = false;

    public static String getHeaderfromAPIResponseVolley(String header_key, Map<String, String> headers) {
        String data = headers.get(header_key);
        if (data != null) {
            return data;
        }
        return "";
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void dismissDialog(ProgressDialog mProgressDialog) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public static boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isNull(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        } else {
            return false;
        }
    }

    public static int dipToPixels(float dipValue, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Log.d("int_value", (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics) + "");
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static String formateDate(String date) {
        SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "IN"));

        Date d1;
        try {
            d1 = sdfg.parse(date);
            return new SimpleDateFormat("dd MMM yyyy", new Locale("en", "IN")).format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formateDateInWords(String date) {
        SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "IN"));

        Date d1;
        try {
            d1 = sdfg.parse(date);
            return new SimpleDateFormat("dd MMM", new Locale("en", "IN")).format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formateDownloadDate(String date) {
        SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "IN"));

        Date d1;
        try {
            d1 = sdfg.parse(date);
            return new SimpleDateFormat("MMM dd, yyyy", new Locale("en", "IN")).format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readDataFromAsset(Context context, String mFileName) {
        String data = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = context.getAssets().open(mFileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(json,
                    "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            data = buf.toString();
//            System.out.println("@@@@   " + data);
            in.close();
        } catch (Exception exception) {
            System.out.println("@@@@   " + exception);
        }
        return data;
    }

    public static String convertDateInWords(String... date) {
        int selectedYear = Integer.parseInt(date[2]);
        int selectedDay = Integer.parseInt(date[0]);
        int selectedMonth = Integer.parseInt(date[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, selectedYear);
        cal.set(Calendar.DAY_OF_MONTH, selectedDay);
        cal.set(Calendar.MONTH, selectedMonth - 1);


        switch (selectedDay) {
            case 1:
            case 21:
            case 31:

                return new SimpleDateFormat("E, MMM d'st'", new Locale("en", "IN")).format(cal.getTime());

            case 2:
            case 22:


                return new SimpleDateFormat("E, MMM d'nd'", new Locale("en", "IN")).format(cal.getTime());

            case 3:
            case 23:

                return new SimpleDateFormat("E, MMM d'rd'", new Locale("en", "IN")).format(cal.getTime());

            default:

                return new SimpleDateFormat("E, MMM d'th'", new Locale("en", "IN")).format(cal.getTime());
        }


    }

    public static String convertDate(String... date) {
        int selectedYear = Integer.parseInt(date[2]);
        int selectedDay = Integer.parseInt(date[0]);
        int selectedMonth = Integer.parseInt(date[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, selectedYear);
        cal.set(Calendar.DAY_OF_MONTH, selectedDay);
        cal.set(Calendar.MONTH, selectedMonth - 1);

        return new SimpleDateFormat("dd MMM yyyy", new Locale("en", "IN")).format(cal.getTime());

    }

    public static void exponentialBackOffOperation(final ExponentialBackoffRunnable runnable, final long delayInMillis, final int maxAttempts) {
        Random random = new Random();
        for (int i = 1; i <= maxAttempts; i++) {

            runnable.run();
            if (runnable.isTaskSuccessful()) {
                break;
            }

            try {
                Thread.sleep(((1 << i) * delayInMillis) + random.nextInt(10));
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static double getScreenSize(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
//        float scaleFactor = metrics.density;
//        float widthDp = widthPixels / scaleFactor;
//        float heightDp = heightPixels / scaleFactor;
        float widthDpi = metrics.xdpi;
        float heightDpi = metrics.ydpi;
        float widthInches = widthPixels / widthDpi;
        float heightInches = heightPixels / heightDpi;
        double diagonalInches = Math.sqrt(
                (widthInches * widthInches)
                        + (heightInches * heightInches));
        return diagonalInches;
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = df.format(c.getTime());
        Log.d("current_time", formattedDate + "");
        return formattedDate;
    }

    public static String getNameFromPath(String path) {
        String logoName = "";
        if (path.contains("/")) {
            logoName = path.substring(path.lastIndexOf("/") + 1, path.length());
        }
        return logoName;
    }

    public static boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() != 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static String userAccessTokenStatus(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.has("error")) {
                return "Invalid Request";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Something went wrong";
        }

        return "";

    }

    public static String getOfferDaysLeft(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return String.valueOf(elapsedDays);
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed() || activity.isFinishing()) {
                return false;
            } else if (activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isArabicSelected(Context context) {
        String lang = new SharedPreferencesData(context).getLanguage();
        if (!lang.isEmpty()) {
            if (lang.equals(Constants.ARABIC_LOCALE_LANGUAGE)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyString(String text) {
        if (text != null && !text.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static int getApplicationVersion(Context mContext) {
        int versionCode = 0;
        PackageManager manager = mContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String packageName = info.packageName;
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }
        return versionCode;

    }

    public static String getCompanyImageDownloadedPath(String fileName) {
        if (fileName.contains(" ")) {
            fileName = fileName.replace(" ", "_");
        }
        return Environment.getExternalStorageDirectory() + "/" + fileName + ".png";
    }

    public static File getCompanyImageDownloadedFilePath(Context context, String fileName) {
        if (fileName.contains(" ")) {
            fileName = fileName.replace(" ", "_");
        }
        return new File(getStorageDirectory(context) + "/" + fileName + ".png");
    }

    public static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();


        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    public static String getStorageDirectory(Context mContext) {

        if (mContext != null) {
            String state = Environment.getExternalStorageState();
            File filesDir = null;
            // Make sure it's available
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                File[] files = ContextCompat.getExternalFilesDirs(mContext, null);
//            Log.d("external_storage_lenght",files.length+ "");
                if (files.length > 1 && files[1] != null) {
                    filesDir = files[1];
                    Log.d("Storage Space", "sd_card");
                } else if (files[0] != null) {
                    filesDir = files[0];
                    Log.d("Storage Space", "external");
                } else {
                    Log.d("Storage Space", "external1");
                    filesDir = mContext.getFilesDir();
                }
            } else {
                // Load another directory, probably local memory
                filesDir = mContext.getExternalFilesDir(null);
                Log.d("Storage Space", "internal");
            }
            if (filesDir != null) {
                return filesDir + File.separator + "Iofferbh";
            } else {
                return "";
            }
        }
        return "";
    }

    public static boolean isMoreThan1GbAvailable(File localPath) {
//        String localPath = getStorageDirectory(context, "", "");
//        localPath = localPath.substring(0, localPath.lastIndexOf("/"));
        long freeSpace = localPath.getUsableSpace();
        long freeSize = gbToByte(1);

        if (freeSpace > 0 && freeSize > 0) {
            if (freeSpace > freeSize) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

//
//        return false;
    }

    private static long gbToByte(int size) {
        String hrSize = null;
        return (((size * 1024) * 1024) * 1024);
    }

    public static <T> ArrayList<T> shiftArraylist(ArrayList<T> aL, int shift) {
        ArrayList<T> newValues = new ArrayList<>(aL);
        Collections.rotate(newValues, shift);
        return newValues;
    }

    public static void showDialog(Context context, String message, Boolean flag) {
        if (flag) {
            if (progressAlive) {
                try {
                    pDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressAlive = false;
            }
            pDialog = new ProgressDialog(context);
            pDialog.setMessage(message);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setCancelable(false);
            progressAlive = true;
            pDialog.show();
        } else {
            if (progressAlive) {
                pDialog.dismiss();
                pDialog.cancel();
                progressAlive = false;
            }
        }
    }



    public static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private void generateHashWithHmac256(String message, String key) {
        try {
            final String hashingAlgorithm = "HmacSHA256"; //or "HmacSHA1", "HmacSHA512"

            byte[] bytes = hmac(hashingAlgorithm, key.getBytes(), message.getBytes());

            final String messageDigest = bytesToHex(bytes);

            Log.i("Utils", "message digest: " + messageDigest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

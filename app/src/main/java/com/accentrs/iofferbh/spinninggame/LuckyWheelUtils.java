package com.accentrs.iofferbh.spinninggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by kiennguyen on 11/5/16.
 */

public class LuckyWheelUtils {
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public static ArrayList<Integer> spinningWheelColorCode(){
        ArrayList<Integer> spinningWheelArrayList = new ArrayList<>();

        spinningWheelArrayList.add(0xff30A0CE);

        spinningWheelArrayList.add(0xff283955);

        spinningWheelArrayList.add(0xff1363AA);

        spinningWheelArrayList.add(0xff7E87C2);

        spinningWheelArrayList.add(0xffB07764);

        spinningWheelArrayList.add(0xffD25058);

        spinningWheelArrayList.add(0xffC52027);

        spinningWheelArrayList.add(0xffF68E2F);

        spinningWheelArrayList.add(0xffFFCF6B);

        spinningWheelArrayList.add(0xff75C4AE);

        spinningWheelArrayList.add(0xff30A0CE);


        return spinningWheelArrayList;
    }



    public static Bitmap getBitmapClippedCircle(Bitmap bitmap) {

        if(bitmap != null){
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();
            final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            final Path path = new Path();
            path.addCircle(
                    (float)(width / 2)
                    , (float)(height / 2)
                    , (float) Math.min(width, (height / 2))
                    , Path.Direction.CCW);

            final Canvas canvas = new Canvas(outputBitmap);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap, 0, 0, null);
            return outputBitmap;
        }
        return null;

    }


}

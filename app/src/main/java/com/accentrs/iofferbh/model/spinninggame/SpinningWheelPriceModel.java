package com.accentrs.iofferbh.model.spinninggame;

import android.graphics.Bitmap;

public class SpinningWheelPriceModel {

    private Bitmap spinningWheelBlurBitmap;
    private String spinningWheelPrizeImageUrl;

    public Bitmap getSpinningWheelPrizeBitmap() {
        return spinningWheelBlurBitmap;
    }

    public void setSpinningWheelPrizeBitmap(Bitmap spinningWheelPrizeBitmap) {
        this.spinningWheelBlurBitmap = spinningWheelPrizeBitmap;
    }

    public String getSpinningWheelPrizeImageUrl() {
        return spinningWheelPrizeImageUrl;
    }

    public void setSpinningWheelPrizeImageUrl(String spinningWheelPrizeImageUrl) {
        this.spinningWheelPrizeImageUrl = spinningWheelPrizeImageUrl;
    }
}

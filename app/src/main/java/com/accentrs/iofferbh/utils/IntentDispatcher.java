package com.accentrs.iofferbh.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;

import com.accentrs.iofferbh.application.IOfferBhApplication;

import java.io.File;
import java.io.IOException;

public class IntentDispatcher {

    public static Intent dispatchTakePictureIntent(Context context, File outputFile) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = outputFile;
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.technapse.iofferbh" + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
        }
        return takePictureIntent;
    }

    public static Intent dispatchSendLogFileIntent(String fullName) {

        Uri logFileUri = FileProvider.getUriForFile(IOfferBhApplication.getInstance(),
                "com.technapse.iofferbh" + ".provider",
                new File(fullName));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mallharavi28@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Iofferbh log file");
        intent.putExtra(Intent.EXTRA_STREAM, logFileUri);
        intent.putExtra(Intent.EXTRA_TEXT, "Log file attached.");

        return intent;
    }
}

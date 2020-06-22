package com.accentrs.iofferbh.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.util.Log;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;

public class CompanyBitmapService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private List<File> imageBitmapList;

    public static final int DOWNLOAD_SUCCESS = 2;
    public static final int DOWNLOAD_ERROR = 3;
    private ResultReceiver receiver;
    private int colorCode;

    public CompanyBitmapService(String name) {
        super(name);
    }

    public CompanyBitmapService() {
        super(CompanyBitmapService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        receiver = intent.getParcelableExtra("receiver");
        String imageUrl = intent.getStringExtra("url");
        String imageName = intent.getStringExtra("image_name");
        new DownloadImageBitmapTask().execute(imageUrl,imageName);

        Log.d("on handle intent","called");
        Log.d("iamgeURL Called",imageUrl+"");


    }


    private class DownloadImageBitmapTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String imageUrl = Constants.BASE_URL + params[0];
            String imageName = params[1];
//            Log.d("image url in service",imageUrl+"");
            String pathToSave = Utils.getStorageDirectory(getApplicationContext());
            File dir = new File(pathToSave);
            if (!dir.exists()) {
                dir.mkdir();
            }

            File downloadFile = Utils.getCompanyImageDownloadedFilePath(getApplicationContext(),imageName);
            if (downloadFile.exists()){
                return downloadFile.getPath();
            }else{
                try {
                    downloadFile.createNewFile();
                    URL downloadURL = new URL(imageUrl);
                    HttpURLConnection conn = (HttpURLConnection) downloadURL
                            .openConnection();
                    InputStream is = conn.getInputStream();
                    FileOutputStream os = new FileOutputStream(downloadFile);
                    byte buffer[] = new byte[1024];
                    int byteCount;
                    while ((byteCount = is.read(buffer)) != -1) {
                        os.write(buffer, 0, byteCount);
                    }
                    os.close();
                    is.close();

                    return downloadFile.getPath();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";

                }
            }

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Bundle bundle = new Bundle();

            if(result != null && !result.isEmpty()){
//                Log.d("download image file ",result+"");
                bundle.putString("filePath", result);
                receiver.send(DOWNLOAD_SUCCESS, bundle);
            }else{
                receiver.send(DOWNLOAD_ERROR, bundle);
            }

        }
    }


}

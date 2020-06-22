package com.accentrs.iofferbh.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.utils.IntentDispatcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogActivity extends AppCompatActivity {

    private static final String TAG = LogActivity.class.getSimpleName();
    private Button btnSendLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;

        this.getWindow().setAttributes(params);

        setContentView(R.layout.activity_log);
        initializeResources();
    }

    private void initializeResources() {
        btnSendLog = findViewById(R.id.btn_send_log);
        btnSendLog.setOnClickListener(view -> sendLogFile());
    }

    private void sendLogFile() {

        String fullName = extractLogToFile();
        if (fullName == null)
            return;
        Intent intent = IntentDispatcher.dispatchSendLogFileIntent(fullName);
        startActivity(intent);
        this.finish();
    }

    private String extractLogToFile() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER))
            model = Build.MANUFACTURER + " " + model;


        String path = getApplicationContext().getExternalCacheDir() + File.separator + "Iofferbh" + File.separator;
        String fileName = "Log_File.txt";

        File logFilePath = new File(path);

        logFilePath.mkdirs();

        File logFile = new File(logFilePath, fileName);

        if (logFile.exists()) {
            logFile.delete();
        }

        InputStreamReader reader = null;
        FileWriter writer = null;
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
                    "logcat -d -v time MyApp:v dalvikvm:v System.err:v *:s" :
                    "logcat -d -v time";

            Process process = Runtime.getRuntime().exec(cmd);
            reader = new InputStreamReader(process.getInputStream());

            writer = new FileWriter(logFile);
            writer.write("Android version: " + Build.VERSION.SDK_INT + "\n");
            writer.write("Device: " + model + "\n");
            writer.write("App version: " + (info == null ? "(null)" : info.versionCode) + "\n");
            writer.write("StackTrace: " + getStacktraceFromIntent());

            char[] buffer = new char[10000];
            do {
                int n = reader.read(buffer, 0, buffer.length);
                if (n == -1)
                    break;
                writer.write(buffer, 0, n);
            } while (true);

            reader.close();
            writer.close();
        } catch (IOException e) {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e1) {
                }

            return null;
        }

        return logFile.getAbsolutePath();
    }

    private String getStacktraceFromIntent() {
        Intent intent = getIntent();

        if (intent != null) {
            String stackTrace = intent.getStringExtra("exception");
            return stackTrace;
        }
        return null;
    }

}

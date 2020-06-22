package com.accentrs.iofferbh.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.HomeScreenActivity;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class BetterLuckNextTimeFragment extends DialogFragment implements View.OnClickListener {

    private View view;
    private TextView tvGameStartTime;
    int seconds, minutes;
    private TextView tvTryAgainIn;
    private TextView tvOops;
    private TextView tvStartBrowsing;
    private AppCompatImageView ivCloseDialog;
    private static final String FORMAT = "%02d:%02d:%02d";
    private CountDownTimer countDownTimer;
    private TextView tvAlertMessage;
    private String message = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_better_luck_next_time, container, false);
            getData();
            initializeResources();
        }
        return view;
    }

    private void getData() {
        if (getArguments() != null) {
            if (getArguments().getString("message") != null) {
                message = getArguments().getString("message");
            }
        }
    }

    private void initializeResources() {
        tvOops = view.findViewById(R.id.tv_oops);
        tvAlertMessage = view.findViewById(R.id.tv_alert_message);
        tvTryAgainIn = view.findViewById(R.id.tv_try_again_in);
        tvGameStartTime = view.findViewById(R.id.tv_game_start_time);
        tvStartBrowsing = view.findViewById(R.id.tv_start_browsing);
        ivCloseDialog = view.findViewById(R.id.iv_better_luck_close);
        tvStartBrowsing.setOnClickListener(this);
        ivCloseDialog.setOnClickListener(this);

        if (message != null) {
            if (message.isEmpty()) {
                startBetterLuckNextTimeTimer();
            } else {

                if (message.equalsIgnoreCase("You have already played today")) {
                    startBetterLuckNextTimeTimer();
                } else {
                    tvTryAgainIn.setVisibility(View.GONE);
                    tvOops.setVisibility(View.GONE);
                    tvGameStartTime.setVisibility(View.GONE);
                    tvAlertMessage.setVisibility(View.VISIBLE);
                    tvAlertMessage.setText(message);
                }
            }
        }

    }

    private void startBetterLuckNextTimeTimer() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long howMany = (c.getTimeInMillis() - System.currentTimeMillis());

        countDownTimer = new CountDownTimer(howMany, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                tvGameStartTime.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                tvGameStartTime.setText("Done!");
            }
        }.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_start_browsing:
                dismiss();
                if (getActivity() != null) {
                    if (getActivity() instanceof HomeScreenActivity) {
                        dismiss();
                    } else {
                        getActivity().finish();
                    }
                }
                break;


            case R.id.iv_better_luck_close:
                dismiss();
                if (getActivity() != null) {
                    if (getActivity() instanceof HomeScreenActivity) {
                        dismiss();
                    } else {
                        getActivity().finish();
                    }
                }
                break;

        }
    }
}

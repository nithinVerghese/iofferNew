package com.accentrs.iofferbh.activity;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.winners.GameWinnerAdapter;
import com.accentrs.iofferbh.model.winner.GameWinnerModel;
import com.google.gson.Gson;

public class WinnerActivity extends HeaderActivity {

    private TextView           tvGameCongraculation;
    private RecyclerView       rvWinners;
    private AppCompatImageView ivSpinnerWheelWinnerClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        initializeViews();
        hitSpinWheelWinnerApi();
    }

    private void initializeViews(){
        tvGameCongraculation      = findViewById(R.id.tv_game_congraculation);
        rvWinners                 = findViewById(R.id.rv_winners);
        ivSpinnerWheelWinnerClose = findViewById(R.id.iv_spinning_wheel_winner_close);
        rvWinners.setLayoutManager(new LinearLayoutManager(this));
        ivSpinnerWheelWinnerClose.setOnClickListener(this);
    }

    private void hitSpinWheelWinnerApi(){
        showProgressDialog(getString(R.string.msg_loading));
        Results mResults = new Results(this);
        mResults.gameWinner();
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();

                if(response.getStringResponse().toString() != null){

                    GameWinnerModel gameWinnerResponse = new Gson().fromJson(response.getStringResponse().toString(),GameWinnerModel.class);
                    if(gameWinnerResponse != null){
                        if(gameWinnerResponse.getData() != null){

                            if(gameWinnerResponse.getData().size() > 0){
                                if(gameWinnerResponse.getData().get(0).getName() != null){
                                    setGameCongraculationText(gameWinnerResponse.getData().get(0).getName());
                                }
                                GameWinnerAdapter winnerAdapter = new GameWinnerAdapter(WinnerActivity.this,gameWinnerResponse.getData());
                                rvWinners.setAdapter(winnerAdapter);
                            }

                        }else{
                            if(gameWinnerResponse.getMessage() != null){
                                if(TextUtils.isEmpty(gameWinnerResponse.getMessage())){
                                    showToast(getString(R.string.no_winners_found));
                                }
                            }
                        }
                    }

                }

            }

            @Override
            public void onError(String error) {
                dismissProgressDialog();
                showToast(getString(R.string.no_winners_found));
                showWinnerNotFoundDialog(true);
//                showToast(error);
            }
        });
    }

    private void setGameCongraculationText(String gameName){
        String congraculationText = gameName.concat(" ")
                                    .concat(getString(R.string.winner_text));
        tvGameCongraculation.setText(congraculationText);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_spinning_wheel_winner_close:
                finish();
                break;
        }
    }


    private void showWinnerNotFoundDialog(final boolean isCancellable) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.winner_not_found_header_text));

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.winner_not_found_text));

        alertDialog.setCancelable(!isCancellable);
      /*  // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getString(R.string.text_update), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface delivery_indo_dialog, int which) {

                delivery_indo_dialog.dismiss();
            }
        });*/
        alertDialog.setNegativeButton(getString(R.string.ok_text), (dialog, which) -> {
            if (isCancellable)
                finish();

            dialog.dismiss();
        });
        // Showing Alert Message
        alertDialog.show();

    }




}

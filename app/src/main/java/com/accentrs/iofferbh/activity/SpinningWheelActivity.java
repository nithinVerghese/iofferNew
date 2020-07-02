package com.accentrs.iofferbh.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.spininggame.SpinningWheelCompanyAdapter;
import com.accentrs.iofferbh.fragment.BetterLuckNextTimeFragment;
import com.accentrs.iofferbh.fragment.SpinningWheelRulesDialogFragment;
import com.accentrs.iofferbh.helper.BlurView;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.helper.RecyclerItemClickListener;
import com.accentrs.iofferbh.model.spinninggame.SpinningGameModel;
import com.accentrs.iofferbh.model.spinninggame.SpinningWheelPlayResponse;
import com.accentrs.iofferbh.model.spinninggame.SpinningWheelPriceModel;
import com.accentrs.iofferbh.model.spinninggame.WheelItemsItem;
import com.accentrs.iofferbh.service.CompanyBitmapService;
import com.accentrs.iofferbh.spinninggame.LuckyItem;
import com.accentrs.iofferbh.spinninggame.LuckyWheelUtils;
import com.accentrs.iofferbh.spinninggame.LuckyWheelView;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class SpinningWheelActivity extends HeaderActivity implements RecyclerItemClickListener.OnItemClickListener {

    private ArrayList<WheelItemsItem> priceArraylist;
    private ImageResultReceiver imageResultReceiver;
    private ArrayList<String> downloadedImageFileArrayList;
    private ArrayList<WheelItemsItem> spinningWheelArrayList;
    private ArrayList<LuckyItem> data = new ArrayList<>();
    private String spinningWheelData = "";
    private int wheelId;
    private TextView tvKnowTheRules;
    private TextView tvPreviousWinners;
    private AppCompatImageView ivCloseSpinningGame;
    private LuckyWheelView luckyWheelView;
    private LuckyWheelView luckyWheelViewV1;
    private ScrollView svSpinWheelParent;
    private AppCompatImageView ivWinner;
    private AppCompatImageView ivRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spnning_wheel_actvity);
        initializeViews();
        fetchIntentData();
    }

    private void initializeViews() {
        tvPreviousWinners = findViewById(R.id.tv_previous_winners);
        tvKnowTheRules = findViewById(R.id.tv_know_the_rules);
        ivCloseSpinningGame = findViewById(R.id.iv_spinning_wheel_game_close);
        svSpinWheelParent = findViewById(R.id.sv_spin_wheel_parent);
        ivRules = findViewById(R.id.iv_know_the_rules);
        ivWinner = findViewById(R.id.iv_spin_wheel_winners);
        tvKnowTheRules.setOnClickListener(this);
        ivRules.setOnClickListener(this);
        ivWinner.setOnClickListener(this);
        tvPreviousWinners.setOnClickListener(this);
        ivCloseSpinningGame.setOnClickListener(this);
        underLineTextView(tvKnowTheRules);
        underLineTextView(tvPreviousWinners);
    }

    private void underLineTextView(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void fetchIntentData() {
        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                spinningWheelData = getIntent().getExtras().getString(Constants.SPINNING_WHEEL_DATA_KEY);
            }
        }

        if (spinningWheelData != null && !TextUtils.isEmpty(spinningWheelData)) {
            setSpinningWheelData(spinningWheelData);
        } else {
            hitSpinningWheelGameApi();
        }
    }

    private void hitSpinningWheelGameApi() {
        showProgressDialog(getString(R.string.msg_loading));
        Results mResults = new Results(this);
        mResults.fetchSpinningWheelGame();
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();
                setSpinningWheelData(response.getStringResponse().toString());
                Log.d("spintest",response.getStringResponse().toString());
            }

            @Override
            public void onError(String error) {
                dismissProgressDialog();
            }
        });
    }

    private void setSpinningWheelData(String spinningWheelData) {
        SpinningGameModel spinningGameModel = new Gson().fromJson(spinningWheelData, SpinningGameModel.class);

        if (spinningGameModel != null && spinningGameModel.getData() != null) {

            if (spinningGameModel.getUser() != null) {
                if (spinningGameModel.getUser().getName() != null) {
                    new SharedPreferencesData(this).setUserName(spinningGameModel.getUser().getName());
                }
                if (spinningGameModel.getUser().getContactNumber() != null) {
                    new SharedPreferencesData(this).setUserPhoneNumber(spinningGameModel.getUser().getContactNumber());
                }
            }

            if (spinningGameModel.getData().getWheelItems() != null) {
                wheelId = spinningGameModel.getData().getId();
                spinningWheelArrayList = new ArrayList<>(spinningGameModel.getData().getWheelItems());
                setSpinningWheelCompanyAdapter(spinningWheelArrayList);
                startDownloadImageBitmapService(spinningWheelArrayList);
            }
        }
    }

    private void setSpinningWheelCompanyAdapter(ArrayList<WheelItemsItem> wheelItemsArrayList) {
        RecyclerView rvGameCompany = (RecyclerView) findViewById(R.id.rv_sponsored_company);
        GridLayoutManager manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        rvGameCompany.setLayoutManager(manager);
        rvGameCompany.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        SpinningWheelCompanyAdapter companyAdapter = new SpinningWheelCompanyAdapter(this, wheelItemsArrayList);
        rvGameCompany.setAdapter(companyAdapter);
    }


    private void startDownloadImageBitmapService(ArrayList<WheelItemsItem> wheelItemsArrayList) {

        if (priceArraylist == null)
            priceArraylist = new ArrayList<>();

        imageResultReceiver = new ImageResultReceiver(new Handler());

        for (int i = 0; i < wheelItemsArrayList.size(); i++) {

            if (wheelItemsArrayList.get(i) != null) {

                if (wheelItemsArrayList.get(i).getPrizeImage() != null && wheelItemsArrayList.get(i).getPrizeImage() != null) {
                    if (wheelItemsArrayList.get(i).getPrizeType().equalsIgnoreCase("prize")) {
                        priceArraylist.add(wheelItemsArrayList.get(i));
                        Intent intent = new Intent(this, CompanyBitmapService.class);
                        intent.putExtra("receiver", imageResultReceiver);
                        intent.putExtra("url", wheelItemsArrayList.get(i).getCompanyImageUrl());
                        intent.putExtra("image_name", wheelItemsArrayList.get(i).getCompanyName().replace(" ", "_"));
                        startService(intent);
                    }
                }
            }
        }
    }

    private class ImageResultReceiver extends ResultReceiver {

        public ImageResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            switch (resultCode) {
                case CompanyBitmapService.DOWNLOAD_ERROR:
                    break;

                case CompanyBitmapService.DOWNLOAD_SUCCESS:
                    String filePath = resultData.getString("filePath");

                    if (downloadedImageFileArrayList == null)
                        downloadedImageFileArrayList = new ArrayList<>();

                    downloadedImageFileArrayList.add(filePath);

                    if (downloadedImageFileArrayList.size() == priceArraylist.size()) {
                        setLuckySpinningWheel();
                    }

                    break;
            }
            super.onReceiveResult(resultCode, resultData);
        }

    }

    @Override
    public void onItemClick(View childView, int position) {
        if (priceArraylist != null && position <= priceArraylist.size()) {
            new BlurAsyncTask().execute(priceArraylist.get(position).getPrizeImage());
        }
    }

    @Override
    public void onItemLongPress(View childView, int position) {

        if (priceArraylist != null && position <= priceArraylist.size()) {
            new BlurAsyncTask().execute(priceArraylist.get(position).getPrizeImage());
        }
    }


    private void setLuckySpinningWheel() {

        if (spinningWheelArrayList != null) {
            spinningWheelArrayList = Utils.shiftArraylist(spinningWheelArrayList, 7);
            luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);
            luckyWheelViewV1 = (LuckyWheelView) findViewById(R.id.luckyWheel_v1);
            final ArrayList<Integer> spinningWheelColorCode = LuckyWheelUtils.spinningWheelColorCode();
            for (int i = 0; i < spinningWheelArrayList.size(); i++) {
                if (spinningWheelArrayList.get(i) != null) {

                    if (spinningWheelArrayList.get(i).getPrizeType().equalsIgnoreCase("prize")) {
                        LuckyItem luckyItem = new LuckyItem();
                        luckyItem.imageBitmap = getResizedBitmap(spinningWheelArrayList.get(i).getCompanyName(), 250, 250);
                        luckyItem.color = (spinningWheelColorCode.get(i));
                        data.add(luckyItem);
                    } else if (spinningWheelArrayList.get(i).getPrizeType().equalsIgnoreCase("no_prize")) {
                        LuckyItem luckyItem = new LuckyItem();
                        luckyItem.imageBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                R.drawable.ic_no_price), 100, 100, true);
                        luckyItem.color = (spinningWheelColorCode.get(i));
                        data.add(luckyItem);
                    }
                }
            }

            luckyWheelView.setRound(getRandomRound());
            luckyWheelView.setLuckyWheelBackgrouldColor(0xffffffff);
            luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
            luckyWheelView.setData(data, this);

            findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getRandomIndex();
                    luckyWheelView.setVisibility(View.VISIBLE);
                    luckyWheelViewV1.setVisibility(View.GONE);
                    luckyWheelView.startLuckyWheelWithTargetIndex(index);
                }
            });

            luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
                @Override
                public void LuckyRoundItemSelected(int index) {
                    try {
                        setLuckyWheelV1(index);
                    } catch (Exception ex) {
                        startGiftActivity(0);
                    }
                }
            });
        }
    }

    private void setLuckyWheelV1(int shiftIndex) {
        luckyWheelView.setVisibility(View.GONE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            TransitionManager.beginDelayedTransition(svSpinWheelParent);
//        }
        int indexToRotateCount = data.size() - 2 - shiftIndex;
        ArrayList<LuckyItem> tempArrayList = data;
        ArrayList<LuckyItem> newList = Utils.shiftArraylist(tempArrayList, indexToRotateCount);
        luckyWheelViewV1.setVisibility(View.VISIBLE);
        luckyWheelViewV1.setLuckyWheelBackgrouldColor(0xffffffff);
        luckyWheelViewV1.setLuckyWheelTextColor(0xffcc0000);
        luckyWheelViewV1.setData(newList, this);
        startGiftActivity(shiftIndex - 1);
    }

    private void startGiftActivity(int index) {
        if (spinningWheelArrayList != null) {
            if (spinningWheelArrayList.get(index) != null) {
                if (spinningWheelArrayList.get(index).getPrizeType() != null) {
                    WheelItemsItem wheelItemsItem = spinningWheelArrayList.get(index);
                    if (wheelItemsItem != null) {
                        if (wheelItemsItem.getPrizeType().equalsIgnoreCase("prize")) {
                            new SharedPreferencesData(SpinningWheelActivity.this).setUserGamePlayedStatus(false);
                            Intent intent = new Intent(SpinningWheelActivity.this, GiftActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Constants.SPINNING_WHEEL_ITEM_DATA_KEY, wheelItemsItem);
                            bundle.putInt(Constants.WHEEL_ID_DATA_KEY, wheelId);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Better luck next time", Toast.LENGTH_SHORT).show();
                            hitSpinWheelPlayApi(wheelItemsItem);
                        }
                    }

                }
            }

        }
    }

    private void hitSpinWheelPlayApi(WheelItemsItem wheelItemsItem) {
        showProgressDialog(getString(R.string.msg_loading));
        Results mResults = new Results(this);
        mResults.playSpinningWheel(createPlaySpinningWheelJsonObject(wheelItemsItem));
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();
//                Log.d("data response",response.getStringResponse().toString());

                if (response.getStringResponse().toString() != null) {

                    SpinningWheelPlayResponse wheelPlayResponse = new Gson().fromJson(response.getStringResponse().toString(), SpinningWheelPlayResponse.class);
                    if (wheelPlayResponse != null) {
                        if (wheelPlayResponse.getMessage() != null) {
                            new SharedPreferencesData(SpinningWheelActivity.this).setUserGamePlayedStatus(false);
                            showToast(wheelPlayResponse.getMessage());
                            showBetterLuckNextTimeDialogFragment();
                        }
                    }
                }

            }

            @Override
            public void onError(String error) {
                dismissProgressDialog();
                showToast(error);
                finish();
            }
        });
    }

    private JSONObject createPlaySpinningWheelJsonObject(WheelItemsItem wheelItemsItem) {
        String fullname = new SharedPreferencesData(this).getUserName();
        String mobileNo = new SharedPreferencesData(this).getUserPhoneNumber();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.WHEEL_ID_KEY, wheelId);
            jsonObject.put(Constants.WHEEL_ITEM_ID_KEY, wheelItemsItem.getId());
            jsonObject.put(Constants.USER_NAME_KEY, fullname);
            jsonObject.put(Constants.CONTACT_NO_KEY, mobileNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.d("game json object",jsonObject.toString());
        return jsonObject;
    }

    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(spinningWheelArrayList.size()) + 1;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

    public Bitmap getResizedBitmap(String companyName, int bitmapWidth, int bitmapHeight) {
//            Bitmap imageBitmap = BitmapFactory.decodeFile(Utils.getCompanyImageDownloadedPath(companyName));
        Bitmap imageBitmap = LuckyWheelUtils.getBitmapClippedCircle(BitmapFactory.decodeFile(Utils.getCompanyImageDownloadedFilePath(this,companyName).getAbsolutePath()));

        if (imageBitmap != null) {
            return Bitmap.createScaledBitmap(imageBitmap, bitmapWidth, bitmapHeight, true);
        } else {
            return BitmapFactory.decodeFile(Utils.getCompanyImageDownloadedFilePath(this,companyName).getAbsolutePath());
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_previous_winners:
            case R.id.iv_spin_wheel_winners:
                startWinnerActivity();
                break;

            case R.id.tv_know_the_rules:
            case R.id.iv_know_the_rules:
                showKnowTheRulesDialogFragment();
                break;

            case R.id.iv_spinning_wheel_game_close:
                finish();
                break;


        }
    }

    private void loadSpinningWheelPrizeImage(String spinningWheelPrizeImageUrl, View alertDlgView) {
        final AppCompatImageView ivPrice = (AppCompatImageView) alertDlgView.findViewById(R.id.iv_price);
        showProgressDialog(getString(R.string.msg_loading));
//        final ProgressBar pbSpinWheelGift = alertDlgView.findViewById(R.id.pb_spin_wheel_gift);
//        pbSpinWheelGift.setVisibility(View.VISIBLE);
        SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                pbSpinWheelGift.setVisibility(View.GONE);
                dismissProgressDialog();
                ivPrice.setImageBitmap(resource);
            }

        };
        GlideApp.with(this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(Constants.BASE_URL1 + spinningWheelPrizeImageUrl)
                .into(target);

        Log.d("cuppy",spinningWheelPrizeImageUrl);

    }

    private void startWinnerActivity() {
        Intent intent = new Intent(this, WinnerActivity.class);
        startActivity(intent);
    }

    private void showKnowTheRulesDialogFragment() {
        SpinningWheelRulesDialogFragment wheelRulesDialogFragment = new SpinningWheelRulesDialogFragment();
        wheelRulesDialogFragment.show(getSupportFragmentManager(), "SpinningWheelRulesDialogFragment");
    }

    private void showBetterLuckNextTimeDialogFragment() {
        BetterLuckNextTimeFragment betterLuckNextTimeFragment = new BetterLuckNextTimeFragment();
        betterLuckNextTimeFragment.setCancelable(false);
        betterLuckNextTimeFragment.show(getSupportFragmentManager(), "BetterLuckNextTimeFragment");
    }

    class BlurAsyncTask extends AsyncTask<String, Integer, SpinningWheelPriceModel> {

        private final String TAG = BlurAsyncTask.class.getName();

        protected SpinningWheelPriceModel doInBackground(String... arg0) {
            SpinningWheelPriceModel spinningWheelPriceModel = new SpinningWheelPriceModel();
            Bitmap map = Utils.takeScreenShot(SpinningWheelActivity.this);
            Bitmap blurBitmap = new BlurView().fastBlur(map, 10);
            spinningWheelPriceModel.setSpinningWheelPrizeImageUrl(arg0[0]);
            spinningWheelPriceModel.setSpinningWheelPrizeBitmap(blurBitmap);
            return spinningWheelPriceModel;
        }


        protected void onPostExecute(SpinningWheelPriceModel result) {
            if (result != null && result.getSpinningWheelPrizeBitmap() != null) {
                final Drawable draw = new BitmapDrawable(getResources(), result.getSpinningWheelPrizeBitmap());

                final Dialog alert = new Dialog(SpinningWheelActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                View alertDlgView = inflater.inflate(R.layout.show_price_dialog, null);

                LinearLayout llPriceParentView = (LinearLayout) alertDlgView.findViewById(R.id.ll_price_parent_view);
                AppCompatImageView ivClosePrize = alertDlgView.findViewById(R.id.iv_close);

                loadSpinningWheelPrizeImage(result.getSpinningWheelPrizeImageUrl(), alertDlgView);

                ivClosePrize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });

                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert.setCancelable(false);
                alert.setContentView(alertDlgView);

                Window window = alert.getWindow();
                window.setBackgroundDrawable(draw);
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER);
                alert.show();
            }

        }
    }


}

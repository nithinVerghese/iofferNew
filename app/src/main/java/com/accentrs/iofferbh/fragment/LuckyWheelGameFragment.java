package com.accentrs.iofferbh.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.SpinningWheelActivity;
import com.accentrs.iofferbh.model.spinninggame.SpinningGameModel;
import com.accentrs.iofferbh.model.spinninggame.WheelItemsItem;
import com.accentrs.iofferbh.service.CompanyBitmapService;
import com.accentrs.iofferbh.spinninggame.LuckyItem;
import com.accentrs.iofferbh.spinninggame.LuckyWheelUtils;
import com.accentrs.iofferbh.spinninggame.LuckyWheelView;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class LuckyWheelGameFragment extends DialogFragment implements View.OnClickListener {

    private View view;
    private ArrayList<WheelItemsItem> priceArraylist;
    private ImageResultReceiver imageResultReceiver;
    private ArrayList<String> downloadedImageFileArrayList;
    private ArrayList<WheelItemsItem> spinningWheelArrayList;
    private List<LuckyItem> data = new ArrayList<>();
    private String spinningWheelData = "";
    private int wheelId;
    private ProgressDialog mProgressDialog;
    private AppCompatButton btnTryNow;
    private AppCompatImageView ivSpinWheelClose;
    private AppCompatImageView ivSpinWheelImage;
    private AppCompatImageView ivSpinWheelCenterIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_lucky_wheel_game_fragment, container, false);
            getDialog().requestWindowFeature(STYLE_NO_TITLE);
            getDialog().setCancelable(false);
            initializeViews();
            fetchIntentData();
        }
        return view;
    }

    private void initializeViews() {
        btnTryNow = view.findViewById(R.id.btn_try_it);
        ivSpinWheelImage = view.findViewById(R.id.dialog_luckyWheel);
        ivSpinWheelClose = view.findViewById(R.id.iv_spinning_wheel_game_close);
        ivSpinWheelCenterIndicator = view.findViewById(R.id.iv_spinwheel_center_indicator);
        ivSpinWheelClose.setOnClickListener(this);
        btnTryNow.setOnClickListener(this);
    }

    private void fetchIntentData() {
        spinningWheelData = new SharedPreferencesData(getActivity()).getSpinningWheelData();

        if (spinningWheelData != null && !TextUtils.isEmpty(spinningWheelData)) {
            setSpinningWheelData(spinningWheelData);
        } else {
            hitSpinningWheelGameApi();
        }
    }

    private void hitSpinningWheelGameApi() {
        showProgressDialog(getString(R.string.msg_loading));
        Results mResults = new Results(getActivity());
        mResults.fetchSpinningWheelGame();
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();
                setSpinningWheelData(response.getStringResponse().toString());
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
                    new SharedPreferencesData(getActivity()).setUserName(spinningGameModel.getUser().getName());
                }
                if (spinningGameModel.getUser().getContactNumber() != null) {
                    new SharedPreferencesData(getActivity()).setUserPhoneNumber(spinningGameModel.getUser().getContactNumber());
                }
            }

            if (spinningGameModel.getData().getWheelItems() != null) {
                wheelId = spinningGameModel.getData().getId();
                spinningWheelArrayList = new ArrayList<>(spinningGameModel.getData().getWheelItems());
                startDownloadImageBitmapService(spinningWheelArrayList);
            }
        }
    }

    private void startDownloadImageBitmapService(ArrayList<WheelItemsItem> wheelItemsArrayList) {

        if (priceArraylist == null)
            priceArraylist = new ArrayList<>();

        imageResultReceiver = new ImageResultReceiver(new Handler());

        if (getActivity() != null) {
            for (int i = 0; i < wheelItemsArrayList.size(); i++) {

                if (wheelItemsArrayList.get(i) != null) {

                    if (wheelItemsArrayList.get(i).getPrizeImage() != null && wheelItemsArrayList.get(i).getPrizeImage() != null) {
                        if (wheelItemsArrayList.get(i).getPrizeType().equalsIgnoreCase("prize")) {
                            priceArraylist.add(wheelItemsArrayList.get(i));
                            Intent intent = new Intent(getActivity(), CompanyBitmapService.class);
                            intent.putExtra("receiver", imageResultReceiver);
                            intent.putExtra("url", wheelItemsArrayList.get(i).getCompanyImageUrl());
                            intent.putExtra("image_name", wheelItemsArrayList.get(i).getCompanyName().replace(" ", "_"));
                            getActivity().startService(intent);
                        }
                    }
                }
            }
        }
    }

    private void setLuckySpinningWheel() {

        if (getActivity() != null && isAdded()) {

            if (spinningWheelArrayList != null) {
                spinningWheelArrayList = Utils.shiftArraylist(spinningWheelArrayList, 7);
                final LuckyWheelView luckyWheelView = (LuckyWheelView) view.findViewById(R.id.luckyWheel);
                final ArrayList<Integer> spinningWheelColorCode = LuckyWheelUtils.spinningWheelColorCode();
                for (int i = 0; i < spinningWheelArrayList.size(); i++) {
                    if (spinningWheelArrayList.get(i) != null) {

                        if (spinningWheelArrayList.get(i).getPrizeType().equalsIgnoreCase("prize")) {
                            LuckyItem luckyItem = new LuckyItem();
                            luckyItem.imageBitmap = getResizedBitmap(spinningWheelArrayList.get(i).getCompanyName(), 150, 150);
                            luckyItem.color = (spinningWheelColorCode.get(i));
                            data.add(luckyItem);
                        } else if (spinningWheelArrayList.get(i).getPrizeType().equalsIgnoreCase("no_prize")) {
                            LuckyItem luckyItem = new LuckyItem();
                            luckyItem.imageBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                                    R.drawable.ic_no_price), 100, 100, true);
                            luckyItem.color = (spinningWheelColorCode.get(i));
                            data.add(luckyItem);
                        }
                    }
                }
                luckyWheelView.setLuckyWheelBackgrouldColor(0xffffffff);
                luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
                luckyWheelView.setData(data, getActivity());
            }
        }


    }

    public Bitmap getResizedBitmap(String companyName, int bitmapWidth, int bitmapHeight) {
        Bitmap imageBitmap = LuckyWheelUtils.getBitmapClippedCircle(BitmapFactory.decodeFile(Utils.getCompanyImageDownloadedFilePath(getActivity(), companyName).getAbsolutePath()));

        if (imageBitmap != null) {
            return Bitmap.createScaledBitmap(imageBitmap, bitmapWidth, bitmapHeight, true);
        } else {
            return BitmapFactory.decodeFile(Utils.getCompanyImageDownloadedFilePath(getActivity(), companyName).getAbsolutePath());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_try_it:
                startSpinningWheelActivity();
                break;

            case R.id.iv_spinning_wheel_game_close:
                if (getActivity() != null && isAdded()) {
                    dismiss();
                }
                break;

        }
    }

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        Utils.dismissDialog(mProgressDialog);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void startSpinningWheelActivity() {
        if (getActivity() != null) {
            Intent spinningWheelIntent = new Intent(getActivity(), SpinningWheelActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SPINNING_WHEEL_DATA_KEY, spinningWheelData);
            spinningWheelIntent.putExtras(bundle);
            startActivity(spinningWheelIntent);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            dismiss();
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
                        ivSpinWheelCenterIndicator.setVisibility(View.VISIBLE);
                        ivSpinWheelImage.setVisibility(View.GONE);
                        setLuckySpinningWheel();
                    } else {
                        if (ivSpinWheelImage.getVisibility() == View.GONE) {
                            ivSpinWheelImage.setVisibility(View.VISIBLE);
                        }
                    }

                    break;
            }
            super.onReceiveResult(resultCode, resultData);
        }

    }

}

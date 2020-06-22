package com.accentrs.iofferbh.interfaces;



import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.View;

import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.android.volley.Request;


public interface IFragmentBasicBehaviorProvider {

    void showMessage(int messageId);

    void showMessage(String message);

    void showMessage(String message, int color);

    SharedPreferencesData getSharedPreferencesData();

    void showProgressDialog(String title, String message);

    void showProgressDialog(String message);

    void dismissProgressDialog();

    boolean addVolleyRequest(Request request, String tag, boolean requestAgain, IFragmentBasicBehaviorProvider IFragmentsBasicBehaviourProvider);

    void cancelAll(String tag);

    void hideKeyboard(View view);

    void retryProgressDialog();

    void setCurrentFragment(Fragment fragment, String tag);

    void showSnackbar(View view, String msg);
    public void showToast(String msg);
    public void showToast(Context context, String msg);

    void showAlertDialog(String title, String message, String clickButtonText, String cancelButtonText, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener
            onCancelListener, boolean isCancellable);
    void showAlertDialog(String message, String clickButtonText, String cancelButtonText, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener
            onCancelListener, boolean isCancellable);
}
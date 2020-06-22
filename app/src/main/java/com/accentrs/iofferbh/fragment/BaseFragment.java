package com.accentrs.iofferbh.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.volley.Request;
import com.accentrs.iofferbh.interfaces.IFragmentBasicBehaviorProvider;
import com.accentrs.iofferbh.utils.SharedPreferencesData;

public class BaseFragment extends Fragment implements IFragmentBasicBehaviorProvider,View.OnClickListener {

    IFragmentBasicBehaviorProvider mCallbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fragmentName = this.getClass().getSimpleName();
    }

    @Override

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (IFragmentBasicBehaviorProvider) activity;
    }



    @Override
    public void showMessage(int messageId) {
        mCallbacks.showMessage(messageId);
    }

    @Override
    public void showMessage(String message) {
        mCallbacks.showMessage(message);
    }

    @Override
    public void showMessage(String message, int color) {
        mCallbacks.showMessage(message, color);
    }

    @Override
    public SharedPreferencesData getSharedPreferencesData() {
        return mCallbacks.getSharedPreferencesData();
    }

    @Override
    public void showProgressDialog(String title, String message) {
        mCallbacks.showProgressDialog(title, message);
    }

    @Override
    public void showProgressDialog(String message) {
        mCallbacks.showProgressDialog(message);
    }

    @Override
    public void dismissProgressDialog() {
        mCallbacks.dismissProgressDialog();
    }

    @Override
    public boolean addVolleyRequest(Request request, String tag, boolean requestAgain, IFragmentBasicBehaviorProvider IFragmentsBasicBehaviourProvider) {
        return mCallbacks.addVolleyRequest(request, tag, requestAgain, IFragmentsBasicBehaviourProvider);
    }

    @Override
    public void cancelAll(String tag) {
        mCallbacks.cancelAll(tag);
    }

    @Override
    public void hideKeyboard(View view) {
        mCallbacks.hideKeyboard(view);
    }

    @Override
    public void retryProgressDialog() {
        mCallbacks.showProgressDialog("", "Please wait...");
    }

    @Override
    public void setCurrentFragment(Fragment fragment, String tag) {
        mCallbacks.setCurrentFragment(fragment, tag);
    }

    @Override
    public void showSnackbar(View view, String msg) {
        mCallbacks.showSnackbar(view,msg);
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showToast(Context context, String msg) {

    }

    @Override
    public void showAlertDialog(String title, String message, String clickButtonText, String cancelButtonText, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onCancelListener, boolean isCancellable) {
        mCallbacks.showAlertDialog(title,message,clickButtonText,cancelButtonText,onClickListener,onCancelListener,isCancellable);
    }

    @Override
    public void showAlertDialog(String message, String clickButtonText, String cancelButtonText, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onCancelListener, boolean isCancellable) {
        mCallbacks.showAlertDialog(message,clickButtonText,cancelButtonText,onClickListener,onCancelListener,isCancellable);
    }

    @Override
    public void onClick(View v) {
    }
}

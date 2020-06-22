package com.accentrs.iofferbh.fragment;


import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.accentrs.iofferbh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpinningWheelRulesDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = SpinningWheelRulesDialogFragment.class.getSimpleName();

    private View view;
    private AppCompatImageView ivCloseSpinningWheelRules;

    public SpinningWheelRulesDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_spinning_wheel_rules, container, false);
            initializeViews();
            setDialog();
        }
        return view;
    }

    private void setDialog() {
        if (getDialog().getWindow() != null)
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }


    private void initializeViews() {
        ivCloseSpinningWheelRules = view.findViewById(R.id.iv_spinning_wheel_rules_close);
        ivCloseSpinningWheelRules.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_spinning_wheel_rules_close:
                dismiss();
                break;
        }
    }

}

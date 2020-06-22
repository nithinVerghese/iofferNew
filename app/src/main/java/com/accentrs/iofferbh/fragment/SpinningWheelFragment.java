package com.accentrs.iofferbh.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.SpinningWheelActivity;
import com.accentrs.iofferbh.model.companydetail.LocationsItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpinningWheelFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = SpinningWheelFragment.class.getSimpleName();

    private View view;
    private RecyclerView rvLocation;
    private ArrayList<LocationsItem> locationsItems;
    private AppCompatButton btnTryIt;
    private AppCompatImageView ivCloseSpinningWheel;
    private String spinningWheelData="";
    public SpinningWheelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spinningWheelData =  getArguments().getString(Constants.SPINNING_WHEEL_DATA_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_spinning_wheel, container, false);
            initializeViews();
            setDialog();
        }
        return view;
    }

    private void setDialog() {
        if (getDialog().getWindow() != null){
            //set the delivery_indo_dialog to non-modal and disable dim out fragment behind
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

    }


    private void initializeViews() {
        btnTryIt             = view.findViewById(R.id.btn_try_it);
        ivCloseSpinningWheel = view.findViewById(R.id.iv_spinning_wheel_close);
        ivCloseSpinningWheel.setOnClickListener(this);
        btnTryIt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_spinning_wheel_close:
                dismiss();
                break;

            case R.id.btn_try_it:
                startSpinningWheelActivity();
                break;
        }
    }

    private void startSpinningWheelActivity(){
        if(getActivity() != null){
            Intent spinningWheelIntent = new Intent(getActivity(), SpinningWheelActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SPINNING_WHEEL_DATA_KEY,spinningWheelData);
            spinningWheelIntent.putExtras(bundle);
            startActivity(spinningWheelIntent);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            dismiss();
        }

    }

}

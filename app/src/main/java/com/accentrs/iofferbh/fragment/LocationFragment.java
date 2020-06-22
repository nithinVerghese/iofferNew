package com.accentrs.iofferbh.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.location.LocationAdapter;
import com.accentrs.iofferbh.model.companydetail.LocationsItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends DialogFragment {

    public static final String TAG = LocationFragment.class.getSimpleName();

    private View view;

    private RecyclerView rvLocation;
    private ArrayList<LocationsItem> locationsItems;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationsItems = (ArrayList<LocationsItem>) getArguments().getSerializable(Constants.LOCATION_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_location, container, false);
            initializeViews();
            setDialog();
            setAdapter();
        }
        return view;
    }

    private void setDialog() {
        if (getDialog().getWindow() != null)
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }


    private void initializeViews() {
        rvLocation = view.findViewById(R.id.rv_location);
        rvLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private void setAdapter() {
        LocationAdapter locationAdapter = new LocationAdapter(getActivity(), locationsItems);
        rvLocation.setAdapter(locationAdapter);
    }


}

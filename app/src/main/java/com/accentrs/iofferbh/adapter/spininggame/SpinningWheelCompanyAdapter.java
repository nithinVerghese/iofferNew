package com.accentrs.iofferbh.adapter.spininggame;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.model.spinninggame.WheelItemsItem;
import com.accentrs.iofferbh.viewholder.spinningwheel.SpinningWheelCompanyViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SpinningWheelCompanyAdapter extends RecyclerView.Adapter<SpinningWheelCompanyViewHolder>{

    private View view;
    private ArrayList<WheelItemsItem> wheelItemsArrayList;
    private Context context;

    public SpinningWheelCompanyAdapter(Context context,ArrayList<WheelItemsItem> wheelItemsArrayList) {
        this.wheelItemsArrayList = filterSpinningWheelCompanyData(wheelItemsArrayList);
        this.context = context;
    }

    private ArrayList<WheelItemsItem> filterSpinningWheelCompanyData(ArrayList<WheelItemsItem> wheelItemsData){
        ArrayList<WheelItemsItem> wheelItemsList = new ArrayList<>();
        for (int i = 0; i < wheelItemsData.size(); i++) {
            if(wheelItemsData.get(i).getPrizeType() != null && !wheelItemsData.get(i).getPrizeType().equalsIgnoreCase("no_prize")){
                wheelItemsList.add(wheelItemsData.get(i));
            }
        }
        return wheelItemsList;
    }

    @Override
    public SpinningWheelCompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spinning_wheel_company_layout,parent,false);
        return new SpinningWheelCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpinningWheelCompanyViewHolder holder, int position) {
        if(wheelItemsArrayList != null){

            String companyImageUrl = com.accentrs.apilibrary.utils.Constants.BASE_URL+ wheelItemsArrayList.get(position).getCompanyImageUrl();

            Glide.with(context)
                 .load(companyImageUrl)
                 .into(holder.ivSponseredCompany);

        }
    }

    @Override
    public int getItemCount() {
        if(wheelItemsArrayList != null && wheelItemsArrayList.size() > 0){
            return wheelItemsArrayList.size();
        }else{
            return 0;
        }
    }
}


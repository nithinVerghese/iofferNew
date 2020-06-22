package com.accentrs.iofferbh.viewholder.spinningwheel;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.accentrs.iofferbh.R;

public class SpinningWheelCompanyViewHolder extends RecyclerView.ViewHolder{

    public AppCompatImageView ivSponseredCompany;

    public SpinningWheelCompanyViewHolder(View itemView) {
        super(itemView);
        ivSponseredCompany = (AppCompatImageView) itemView.findViewById(R.id.iv_spinning_wheel_company);
    }
}

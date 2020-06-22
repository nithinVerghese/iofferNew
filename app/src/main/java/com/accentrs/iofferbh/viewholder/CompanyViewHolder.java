package com.accentrs.iofferbh.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;

public class CompanyViewHolder extends MainViewHolder {

    public ImageView ivCompany;
    public RelativeLayout rlCompany;
    public TextView tvAllCompany;
    public RelativeLayout rlCompanyParent;
    public RelativeLayout rlCompanyViewMargin;

    public CompanyViewHolder(View itemView) {
        super(itemView);
        ivCompany = itemView.findViewById(R.id.iv_company);
        rlCompany = itemView.findViewById(R.id.rl_company);
        tvAllCompany = itemView.findViewById(R.id.tv_company_all);
        rlCompanyParent = itemView.findViewById(R.id.rk_company_parent);
        rlCompanyViewMargin = itemView.findViewById(R.id.rl_company_margin);
    }
}

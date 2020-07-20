package com.accentrs.iofferbh.adapter.company;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.home.CompaniesItem;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.CompanyViewHolder;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder> {


    private View view;
    private List<CompaniesItem> companiesItemList;
    private Context context;
    private int selectedPosition;

    public CompanyAdapter(Context context, List<CompaniesItem> companiesItemList) {
        this.context = context;
        this.companiesItemList = companiesItemList;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.row_company_layout, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CompanyViewHolder holder, int position) {


        if (position == 0) {
            if(selectedPosition == 0){
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_all_offer));
                holder.tvAllCompany.setTextColor(ContextCompat.getColor(context,R.color.white));
            }else{
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_all_offer));
                holder.tvAllCompany.setTextColor(ContextCompat.getColor(context,R.color.white));
            }
            holder.tvAllCompany.setVisibility(View.VISIBLE);
            holder.ivCompany.setVisibility(View.INVISIBLE);
            holder.rlCompanyViewMargin.setVisibility(View.VISIBLE);


        } else {
            holder.rlCompanyViewMargin.setVisibility(View.GONE);


            if (selectedPosition == position) {
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_offer));
            } else {
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_offer_deselected));
            }

            holder.tvAllCompany.setVisibility(View.GONE);
            holder.ivCompany.setVisibility(View.VISIBLE);

            String companyUrl = Constants.BASE_URL + companiesItemList.get(position - 1).getLogo();

//            GlideApp.with(context)
//                    .asBitmap()
//                    .load(companyUrl)
//                    .fitCenter()
//                    .into(new BitmapImageViewTarget(holder.ivCompany).on);
//

            GlideApp.with(context).asBitmap().load(companyUrl).into(new BitmapImageViewTarget(holder.ivCompany) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(Utils.dipToPixels(4,context));
                    holder.ivCompany.setImageDrawable(circularBitmapDrawable);
                }
            });


        }


    }

    @Override
    public int getItemCount() {

        if (companiesItemList != null) {
            return companiesItemList.size() + 1;
        } else {
            return 0;
        }
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }


}
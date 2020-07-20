package com.accentrs.iofferbh.adapter.categoryOffer;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.categoryOffer.CategoriesItem;
import com.accentrs.iofferbh.viewholder.CompanyViewHolder;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CompanyViewHolder> {


    private View view;
    private List<CategoriesItem> companiesItemList;
    private Context context;
    private int selectedPosition;

    public CategoryAdapter(Context context, List<CategoriesItem> companiesItemList) {
        this.context = context;
        this.companiesItemList = companiesItemList;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.row_category_layout, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CompanyViewHolder holder, int position) {

        Log.d("inside adaper ", "called" + position);


        if (position == 0) {
            if(selectedPosition == 0){
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_category_selected));
            }else{
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_category_deselected));
            }
            holder.tvAllCompany.setVisibility(View.VISIBLE);
            holder.ivCompany.setVisibility(View.INVISIBLE);
        } else {


            if (selectedPosition == position) {
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_category_selected));
            } else {
                holder.rlCompany.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_circular_category_deselected));
            }

            holder.tvAllCompany.setVisibility(View.GONE);
            holder.ivCompany.setVisibility(View.VISIBLE);

            String companyUrl = Constants.BASE_URL + companiesItemList.get(position - 1).getLogo();

            GlideApp.with(context)
                    .load(companyUrl)
                    .fitCenter()
                    .into(holder.ivCompany);

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
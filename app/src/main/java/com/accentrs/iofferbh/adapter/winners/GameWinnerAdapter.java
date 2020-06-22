package com.accentrs.iofferbh.adapter.winners;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.winner.DataItem;
import com.accentrs.iofferbh.viewholder.winners.GameWinnerViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

public class GameWinnerAdapter extends RecyclerView.Adapter<GameWinnerViewHolder> {

    private Context        mContext;
    private ArrayList<DataItem> mGameWinnerList;
    private View view;

    public GameWinnerAdapter(Context mContext, List<DataItem> mGameWinnerList) {
        this.mContext = mContext;
        this.mGameWinnerList = new ArrayList<>(mGameWinnerList);
    }

    @Override
    public GameWinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_game_winner_layout,parent,false);
        return new GameWinnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GameWinnerViewHolder holder, int position) {
        DataItem dataItem = mGameWinnerList.get(position);
        if(dataItem != null){

            if(dataItem.getUserName() != null ){
                holder.tvWinningUserName.setText(dataItem.getUserName());
            }

            if(dataItem.getPrizeImage() != null){
                if(!TextUtils.isEmpty(dataItem.getPrizeImage())){
                    Glide.with(mContext)
                            .load(com.accentrs.apilibrary.utils.Constants.BASE_URL + dataItem.getPrizeImage())
                            .into(holder.ivUserWinningGift);
                }
            }

            if(dataItem.getWinnerImage() != null){
                GlideApp.with(mContext).asBitmap().load(com.accentrs.apilibrary.utils.Constants.BASE_URL + dataItem.getWinnerImage()).into(new BitmapImageViewTarget(holder.ivUserImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ivUserImage.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }else{
                GlideApp.with(mContext).asBitmap().load(R.drawable.ic_winner_avatar).into(new BitmapImageViewTarget(holder.ivUserImage) {

                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ivUserImage.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        if(mGameWinnerList != null){
            return mGameWinnerList.size();
        }else{
            return 0;
        }
    }
}

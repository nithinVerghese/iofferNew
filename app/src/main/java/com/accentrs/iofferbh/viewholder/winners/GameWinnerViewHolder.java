package com.accentrs.iofferbh.viewholder.winners;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.accentrs.iofferbh.R;

public class GameWinnerViewHolder extends RecyclerView.ViewHolder {

    public TextView           tvWinningUserName;
    public AppCompatImageView ivUserImage;
    public AppCompatImageView ivUserWinningGift;

    public GameWinnerViewHolder(View itemView) {
        super(itemView);
        tvWinningUserName = itemView.findViewById(R.id.tv_winner_name);
        ivUserImage       = itemView.findViewById(R.id.iv_winning_user_image);
        ivUserWinningGift = itemView.findViewById(R.id.iv_winning_gift);
    }

}


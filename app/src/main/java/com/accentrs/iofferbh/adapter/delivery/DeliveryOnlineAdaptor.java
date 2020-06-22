package com.accentrs.iofferbh.adapter.delivery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.model.delivery.OnlineDataAdaptor;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.accentrs.apilibrary.utils.Constants.IMAGE_URL_DE;

public class DeliveryOnlineAdaptor extends RecyclerView.Adapter<DeliveryOnlineAdaptor.mainViewHolder> {
    LayoutInflater inflater;
    private ArrayList<OnlineDataAdaptor> modelList = new ArrayList<>();
    private Context context;
    //HashMap <String,String> modelList;


    public DeliveryOnlineAdaptor(Context context, List<OnlineDataAdaptor> modelList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.modelList = (ArrayList<OnlineDataAdaptor>) modelList;
    }


    @Override
    public mainViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.delivery_data_with_image_store_info, parent, false);
        return new mainViewHolder(view);
    }

    @Override
    public void onBindViewHolder( mainViewHolder holder, int position) {

        String logo = IMAGE_URL_DE+modelList.get(position).getValue();
        Glide.with(context)
                .load(logo)
                .into(holder.iv_data_logo);

        holder.subText.setText(modelList.get(position).getKey());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(modelList.get(position).getKey()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class mainViewHolder extends RecyclerView.ViewHolder {

        TextView  subText;
        ImageView iv_data_logo;
        LinearLayout linearLayout;

        public mainViewHolder(View itemView) {
            super(itemView);

            iv_data_logo = (ImageView) itemView.findViewById(R.id.iv_data_logo);
            subText = (TextView) itemView.findViewById(R.id.subText);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }
    }
}

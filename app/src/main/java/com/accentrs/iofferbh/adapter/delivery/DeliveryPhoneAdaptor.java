package com.accentrs.iofferbh.adapter.delivery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.model.delivery.PhoneDataAdaptor;

import java.util.ArrayList;
import java.util.List;

public class DeliveryPhoneAdaptor extends RecyclerView.Adapter<DeliveryPhoneAdaptor.mainViewHolder> {
    LayoutInflater inflater;
    private ArrayList<PhoneDataAdaptor> modelList = new ArrayList<>();
    private Context context;
    //HashMap <String,String> modelList;


    public DeliveryPhoneAdaptor(Context context, List<PhoneDataAdaptor> modelList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.modelList = (ArrayList<PhoneDataAdaptor>) modelList;
    }


    @Override
    public mainViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.delivery_data_store_info, parent, false);
        return new mainViewHolder(view);
    }

    @Override
    public void onBindViewHolder( mainViewHolder holder, int position) {
        holder.mainText.setText(modelList.get(position).getValue());
        holder.subText.setText(modelList.get(position).getKey());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + modelList.get(position).getKey()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class mainViewHolder extends RecyclerView.ViewHolder {

        TextView mainText, subText;
        LinearLayout linearLayout;

        public mainViewHolder(View itemView) {
            super(itemView);

            mainText = (TextView) itemView.findViewById(R.id.mainText);
            subText = (TextView) itemView.findViewById(R.id.subText);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }
    }
}

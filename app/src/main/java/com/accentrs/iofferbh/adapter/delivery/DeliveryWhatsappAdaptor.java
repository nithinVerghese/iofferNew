package com.accentrs.iofferbh.adapter.delivery;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.model.delivery.WhatsappDataAdaptor;

import java.util.ArrayList;
import java.util.List;

public class DeliveryWhatsappAdaptor extends RecyclerView.Adapter<DeliveryWhatsappAdaptor.mainViewHolder> {
    LayoutInflater inflater;
    private ArrayList<WhatsappDataAdaptor> modelList = new ArrayList<>();
    private Context context;
    //HashMap <String,String> modelList;


    public DeliveryWhatsappAdaptor(Context context, List<WhatsappDataAdaptor> modelList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.modelList = (ArrayList<WhatsappDataAdaptor>) modelList;
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

                boolean installed = appInstalledOrNot("com.whatsapp");
                if(installed) {
//                    Uri uri = Uri.parse("smsto:"+modelList.get(position).getKey());
//                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
//                    i.setPackage("com.whatsapp");
//                    context.startActivity(i);

                    String url = "https://api.whatsapp.com/send?phone=" + modelList.get(position).getKey() ;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);

                } else {

                    Toast.makeText(context, "Whatsapp is not installed ", Toast.LENGTH_SHORT).show();
                    //System.out.println("App is not currently installed on your phone");
                }

//                Uri uri = Uri.parse("smsto:"+modelList.get(position).getKey());
//                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
//                i.setPackage("com.whatsapp");
//                context.startActivity(i);
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

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

}

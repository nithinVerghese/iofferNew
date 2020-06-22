package com.accentrs.iofferbh.adapter.drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.model.drawer.DrawerModel;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    List<DrawerModel> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private int mNavigationDrawerIcon[] = { R.drawable.logout,
            R.drawable.logout,
            R.drawable.logout,
            R.drawable.logout,
            R.drawable.logout,
            R.drawable.ic_share_app,
            R.drawable.logout,
            R.drawable.logout,
            R.drawable.logout};

    public NavigationDrawerAdapter(Context context, List<DrawerModel> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrawerModel current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.mDrawerImageView.setImageResource(mNavigationDrawerIcon[position]);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView mDrawerImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.nav_title);
            mDrawerImageView = (ImageView) itemView.findViewById(R.id.iv_drawer_icon);
        }
    }
}

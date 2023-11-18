package com.example.navdraw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<NavItem> data;


    public Adapter(Context context, List<NavItem> items) {
        this.context = context;
        this.data = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.drawer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder ){

            NavItem item = data.get(position);
            ((ViewHolder) holder).tvTitle.setText(item.mTitle);
            ((ViewHolder) holder).tvTitle.setCompoundDrawablesWithIntrinsicBounds(item.mpic, 0, 0, 0);
            ((ViewHolder) holder).tvSubtitle.setText(item.mSubtitle);


        }

    }

    @Override
    public int getItemCount() {
        return  data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvSubtitle = itemView.findViewById(R.id.subtitle);
        }
    }

}

package com.example.navdraw.streamfrag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdraw.R;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    List<FilteredUser> data;

    List<FilteredUser> dataAllTmp;
    List<FilteredUser> dataSuggestionsTmp;


    public FilterAdapter(Context context, List<FilteredUser> items) {
        this.context = context;
        this.data = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new FilterAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.stream_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FilterAdapter.ViewHolder){

            try {
                FilteredUser item = data.get(position);
                ((FilterAdapter.ViewHolder) holder).nameView.setText(item.getFullName());
                ((FilterAdapter.ViewHolder) holder).ageView.setText(String.valueOf(item.getAge()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public int getItemCount() {
        return  data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView,genderView, natView, ageView, userView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            genderView = itemView.findViewById(R.id.gender);
            natView = itemView.findViewById(R.id.nat);
            ageView = itemView.findViewById(R.id.age);
            userView = itemView.findViewById(R.id.User);
        }
    }

    @Override
    public Filter getFilter()
    {
        return nameFilter;
    }

    Filter nameFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            if (constraint != null)
            {
                dataSuggestionsTmp.clear();
                for (FilteredUser names : dataAllTmp)
                {
                    if (names.getFullName().toLowerCase().startsWith(constraint.toString().toLowerCase()))
                    {
                        dataSuggestionsTmp.add(names);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataSuggestionsTmp;
                filterResults.count = dataSuggestionsTmp.size();
                return filterResults;
            }
            else
            {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            ArrayList<FilteredUser> filterList = (ArrayList<FilteredUser>) results.values;
            //dataSet.clear();


            if (results != null && results.count > 0)
            {
                //clear();
                data.clear();
                for (FilteredUser item : filterList)
                {
                    //add(item);
                    data.add(item);
                }
                notifyDataSetChanged();
            }
            else{
                notifyDataSetChanged();
            }
        }
    };

}


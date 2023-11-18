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


public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {


    Context context;
    List<User> dataAllTmp; // segédváltozó, ami az összes adatot tartalmazza
    List<User> data; // ezt használja az adapter, ezen dolgozik, ezek jelennek meg
    List<User> dataSuggestionsTmp; // segédváltozó, ami a szűréseket tartalmazza


    public UserAdapter(Context context, List<User> items) {
        this.context = context;
        //this.dataAll = items;

        this.dataAllTmp = new ArrayList<>();
        this.data = new ArrayList<>();
        this.dataSuggestionsTmp = new ArrayList<>();
    }

    public void updateDataSet(List<User> data){
        this.dataAllTmp.clear();
        this.data.clear();
        this.dataSuggestionsTmp.clear();

        this.dataAllTmp.addAll(data);
        this.data.addAll(data);
        this.dataSuggestionsTmp.addAll(data);
        notifyDataSetChanged();
        //suggestions = new ArrayList<User>();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.stream_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder ){

            try {
                User item = data.get(position);
                ((ViewHolder) holder).nameView.setText(item.getName().getFirst());
                ((ViewHolder) holder).genderView.setText(item.getGender());
                ((ViewHolder) holder).natView.setText(item.getNat());
                ((ViewHolder) holder).ageView.setText(String.valueOf(item.getDob().getAge()));
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
                for (User names : dataAllTmp)
                {
                    if (names.getName().getFirst().toLowerCase().startsWith(constraint.toString().toLowerCase()))
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
            ArrayList<User> filterList = (ArrayList<User>) results.values;
            //dataSet.clear();


            if (results != null && results.count > 0)
            {
                //clear();
                data.clear();
                for (User item : filterList)
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

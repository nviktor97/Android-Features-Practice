package com.example.navdraw.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.navdraw.R;

import java.util.ArrayList;

public class LVAdapter extends ArrayAdapter<User> implements Filterable{

    private ArrayList<User> dataAll;
    private ArrayList<User> data;
    private ArrayList<User> dataSuggestions;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtGender;
        TextView txtNat;
    }

    public LVAdapter(ArrayList<User> data, Context context) {
        super(context, R.layout.listview_item, data);
        this.mContext=context;// this makes the difference.

        this.dataAll = new ArrayList<>();
        this.data = new ArrayList<>();
        this.dataSuggestions = new ArrayList<>();
        //suggestions.addAll(data);

    }

    public void updateDataSet(ArrayList<User> data){
        this.dataAll.clear();
        this.data.clear();
        this.dataSuggestions.clear();

        this.dataAll.addAll(data);
        this.data.addAll(data);
        this.dataSuggestions.addAll(data);
        this.notifyDataSetChanged();
        //suggestions = new ArrayList<User>();

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtGender = (TextView) convertView.findViewById(R.id.gender);
            viewHolder.txtNat = (TextView) convertView.findViewById(R.id.nat);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;


        viewHolder.txtName.setText(dataModel.getName().getFirst());
        viewHolder.txtGender.setText(dataModel.getGender());
        viewHolder.txtNat.setText(dataModel.getNat());

        // Return the completed view to render on screen
        return convertView;
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
                dataSuggestions.clear();
                for (User names : dataAll)
                {
                    if (names.getName().getFirst().toLowerCase().startsWith(constraint.toString().toLowerCase()))
                    {
                        dataSuggestions.add(names);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataSuggestions;
                filterResults.count = dataSuggestions.size();
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
                clear();
                //data.clear();
                for (User item : filterList)
                {
                    add(item);
                    //data.add(item);
                    notifyDataSetChanged();
                }
            }
            else{
                clear();
                addAll(dataAll);
                //dataSuggestions.clear();
                //dataSuggestions.addAll(dataAll);
                //data.addAll(dataAll);
                notifyDataSetChanged();
            }
        }
    };
}
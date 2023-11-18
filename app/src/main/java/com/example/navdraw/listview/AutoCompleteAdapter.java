package com.example.navdraw.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.navdraw.R;

import java.util.ArrayList;

/*public class AutoCompleteAdapter extends ArrayAdapter<User> {

    Context context;
    int resource, textViewResourceId;
    ArrayList<User> items, tempItems, suggestions;

    public AutoCompleteAdapter(Context context, int resource, int textViewResourceId, ArrayList<User> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<User>(items); // this makes the difference.
        suggestions = new ArrayList<User>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, parent, false);
        }
        User user = items.get(position);
        if (user != null) {
            TextView lblName = (TextView) view.findViewById(R.id.name);
            if (lblName != null)
                lblName.setText(user.getName().getFirst());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }


    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((User) resultValue).getName().getFirst();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (User user : tempItems) {
                    if (user.getName().getFirst().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(user);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<User> filterList = (ArrayList<User>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (User user : filterList) {
                    add(user);
                    notifyDataSetChanged();
                }
            }
        }
    };
}*/

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<User> items;
    private ArrayList<User> filteredItems;
    private ItemFilter mFilter = new ItemFilter();

    public AutoCompleteAdapter(Context context, ArrayList<User> items) {
        //super(context, R.layout.your_row, items);
        this.context = context;
        this.items = items;
        this.filteredItems = items;
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (AutoCompleteTextView) convertView.findViewById(R.id.autocom);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User location = filteredItems.get(position);
        if (location != null || viewHolder != null) {
            viewHolder.tvTitle.setText(location.getName().getFirst());
        }
        return convertView;
    }

    public static class ViewHolder {
        AutoCompleteTextView tvTitle;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            int count = items.size();
            final ArrayList<User> tempItems = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                if (items.get(i).getName().getFirst().toLowerCase().contains(filterString)) {
                    tempItems.add(items.get(i));
                }
            }

            results.values = tempItems;
            results.count = tempItems.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems = (ArrayList<User>) results.values;
            notifyDataSetChanged();
        }
    }

    public Filter getFilter() {
        return mFilter;
    }
}

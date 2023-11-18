package com.example.navdraw.listview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listViewFragment extends Fragment {

    public static final String TAG = listViewFragment.class.getName();

    ArrayList<User> data, temp;
    LVAdapter adapter;

    ListView listView;

    AutoCompleteTextView autoComplete;

    Button btnReload;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listview_fragment, container, false);

        btnReload = v.findViewById(R.id.reload);

        listView = v.findViewById(R.id.list);
        autoComplete = v.findViewById(R.id.autocom);

        data = new ArrayList<>();
        temp = new ArrayList<>();

        adapter = new LVAdapter(data, getActivity());

        listView.setAdapter(adapter);

        autoComplete.setThreshold(1);
        autoComplete.setDropDownHeight(0);


        autoComplete.setAdapter(adapter);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Map<String, String> params = new HashMap<>();
        params.put("noinfo", "");
        params.put("results", "30");
        params.put("inc", "name,gender,nat, email");

        Call<Results> call = apiInterface.getRandomUsers(params);

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {


                if (!response.isSuccessful()) {
                    return;
                }

                //data.clear();
                Results respResult = response.body();
                List<User> users = respResult.getResults();


                data.addAll(users);
                /*adapter.notifyDataSetChanged();
                temp = data.stream().collect(Collectors.toCollection(ArrayList::new));*/
                adapter.updateDataSet(new ArrayList<User>(users));
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* adapter.clear();
                data = temp.stream().collect(Collectors.toCollection(ArrayList::new));
                adapter = new LVAdapter(data, getActivity());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/
            }
        });




        return v;
    }
}

package com.example.navdraw.streamfrag;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdraw.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StreamFragment extends Fragment {

    public static final String TAG = StreamFragment.class.getName();

    UserAdapter adapter;

    FilterAdapter filterAdapter;
    ArrayList<User> data;

    RecyclerView recyclerView;

    Button btnApi;
    Button btnNameFilter;

    Button btnAgeFilter;

    Button btnMixed;

    TextView tvCounter;

    ConstraintLayout clLoadingIndicator;
    ProgressBar pbLoadingIndicator;

    AutoCompleteTextView autoComplete;

    EditText autoEdit;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stream_layout, container, false);

        btnApi = v.findViewById(R.id.apiCall);
        btnNameFilter = v.findViewById(R.id.namefilter);
        btnAgeFilter = v.findViewById(R.id.agefilter);
        btnMixed = v.findViewById(R.id.mixFilter);
        tvCounter = v.findViewById(R.id.counter);
        autoComplete = v.findViewById(R.id.autocom);
        autoEdit = v.findViewById(R.id.autoEdit);

        clLoadingIndicator = v.findViewById(R.id.clLoadingIndicator);
        pbLoadingIndicator = v.findViewById(R.id.progress_loader);

        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        btnApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clLoadingIndicator.setVisibility(View.VISIBLE);
                pbLoadingIndicator.setVisibility(View.VISIBLE);


                data = new ArrayList<>();

                adapter = new com.example.navdraw.streamfrag.UserAdapter(getActivity(), data);
                recyclerView.setAdapter(adapter);

                autoComplete.setThreshold(1);
                //autoComplete.setDropDownHeight(0);
                ArrayAdapter<User> adapter2 = new ArrayAdapter<User>(getActivity(),android.R.layout.simple_dropdown_item_1line, data);
                autoComplete.setAdapter(adapter2);


                //autoComplete.setAdapter(adapter);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://randomuser.me/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                com.example.navdraw.streamfrag.ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                Map<String, String> params = new HashMap<>();
                params.put("noinfo", "");
                params.put("results", "20");
                params.put("inc", "name,gender,nat,dob");

                Call<Results> call = apiInterface.getRandomUsers(params);

                call.enqueue(new Callback<Results>() {
                    @Override
                    public void onResponse(Call<Results> call, Response<Results> response) {
                        try {
                            clLoadingIndicator.setVisibility(View.GONE);

                            if (!response.isSuccessful()) {
                                Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            data.clear();
                            Results users = response.body();

                            data.addAll(users.getResults());
                            data.get(0).getDob().setAge(29636);


                            //adapter.notifyDataSetChanged();
                            adapter.updateDataSet(data);
                            adapter2.notifyDataSetChanged();
                            long res = data.stream().count();
                            tvCounter.setText("Count: " + res);

                            boolean isValidData = data != null && data.size() > 0;
                            recyclerView.setVisibility(isValidData ? View.VISIBLE : View.GONE);


                            boolean isValidCount = adapter != null && adapter.getItemCount() > 0;
                            String countText = isValidCount ? String.valueOf(adapter.getItemCount()) : "nincs adat";
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(Call<Results> call, Throwable t) {
                        Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                        clLoadingIndicator.setVisibility(View.GONE);
                    }
                });

               // recyclerView.setAdapter(adapter);


            }
        });

        btnNameFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> filter = StreamSupport.stream(data.spliterator(), false).filter(e -> e.getName().getFirst().charAt(0)=='A').collect(Collectors.toList());
                adapter = new com.example.navdraw.streamfrag.UserAdapter(getActivity(), filter);
                adapter.updateDataSet(filter);
                recyclerView.setAdapter(adapter);
                long res = filter.stream().count();

                tvCounter.setText("Count: " + res);
                adapter.notifyDataSetChanged();
            }
        });

        btnAgeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                data.get(0).getDob().setAge(30);
                List<User> agefilter = data.stream().filter(e -> e.getDob().getAge()==Integer.valueOf(29636).intValue()).collect(Collectors.toList());
                adapter = new com.example.navdraw.streamfrag.UserAdapter(getActivity(), agefilter);
                adapter.updateDataSet(agefilter);
                recyclerView.setAdapter(adapter);
                long res = agefilter.stream().count();

                tvCounter.setText("Count: " + res);
                adapter.notifyDataSetChanged();
            }
        });

        btnMixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<FilteredUser> mixfilter = data.stream().filter(e -> e.getName().getFirst().charAt(0)=='A' && e.getDob().getAge()>=30)
                        .map(s -> new FilteredUser(s.getName().getFirst().concat(" ").concat(s.getName().getLast()), s.getDob().getAge())).collect(Collectors.toList());
                filterAdapter = new com.example.navdraw.streamfrag.FilterAdapter(getActivity(), mixfilter);
                recyclerView.setAdapter(filterAdapter);

                long res = mixfilter.stream().count();
                tvCounter.setText("Count: " + res);
                filterAdapter.notifyDataSetChanged();
            }
        });

        autoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                adapter.getFilter().filter(text);



            }
        });



        return v;
    }
}

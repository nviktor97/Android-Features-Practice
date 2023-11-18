package com.example.navdraw.room;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdraw.R;
import com.example.navdraw.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomFragment extends Fragment {

    public static final String TAG = RoomFragment.class.getName();

    UserAdapter adapter;
    List<UserRoomModel> data;
    RecyclerView recyclerView;

    Button btnClear;
    Button btnSave;

    Button btnApi;

    Button btnLoad;

    Button btnDrop;

    ConstraintLayout clLoadingIndicator;
    ProgressBar pbLoadingIndicator;

    SwipeHelper swipeHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.room_layout, container, false);

        btnClear = v.findViewById(R.id.clear);
        btnSave = v.findViewById(R.id.save);
        btnLoad = v.findViewById(R.id.load);
        btnApi = v.findViewById(R.id.apiCall);
        btnDrop = v.findViewById(R.id.clearDB);
        pbLoadingIndicator = v.findViewById(R.id.progress_loader);
        clLoadingIndicator = v.findViewById(R.id.clLoadingIndicator);

        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CheckBox box = view.findViewById(R.id.marriedCheck);
                data.get(position).setMarried(box.isChecked());
                adapter.notifyDataSetChanged();
            }
        }));*/

        btnApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clLoadingIndicator.setVisibility(View.VISIBLE);
                pbLoadingIndicator.setVisibility(View.VISIBLE);


                data = new ArrayList<>();

                adapter = new UserAdapter(getActivity(), data);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://randomuser.me/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                Map<String, String> params = new HashMap<>();
                params.put("noinfo", "");
                params.put("results", "5");
                params.put("inc", "name,gender,nat, email");

                Call<Results> call = apiInterface.getRandomUsers(params);

                call.enqueue(new Callback<Results>() {
                    @Override
                    public void onResponse(Call<Results> call, Response<Results> response) {

                        clLoadingIndicator.setVisibility(View.GONE);

                        if (!response.isSuccessful()) {
                            return;
                        }

                        data.clear();
                        Results respResult = response.body();
                        List<User> users = respResult.getResults();
                        List<UserRoomModel> usersRoom = new ArrayList<>();

                        for(int i = 0; i < users.size(); i++)
                        {
                            User u= users.get(i);

                            UserRoomModel um = new UserRoomModel();
                            um.setName(u.getName().getFirst());
                            um.setNat(u.getNat());
                            um.setGender(u.getGender());
                            um.setMarried(false);
                            usersRoom.add(um);
                        }

                        data.addAll(usersRoom);



                        adapter.notifyDataSetChanged();

                        boolean isValidData = data != null && data.size() > 0;
                        recyclerView.setVisibility(isValidData ? View.VISIBLE : View.GONE);

                        boolean isValidCount = adapter != null && adapter.getItemCount() > 0;
                        String countText = isValidCount ? String.valueOf(adapter.getItemCount()) : "nincs adat";

                    }

                    @Override
                    public void onFailure(Call<Results> call, Throwable t) {

                    }
                });

                recyclerView.setAdapter(adapter);


                addSwipeHelper(recyclerView);

                /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        // this method is called
                        // when the item is moved.
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        // this method is called when we swipe our item to right direction.
                        // on below line we are getting the item at a particular position.
                        UserRoomModel deletedUser = data.get(viewHolder.getAdapterPosition());

                        // below line is to get the position
                        // of the item at that position.
                        int position = viewHolder.getAdapterPosition();

                        // this method is called when item is swiped.
                        // below line is to remove item from our array list.
                        data.remove(viewHolder.getAdapterPosition());

                        // below line is to notify our item is removed from adapter.
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                        // below line is to display our snackbar with action.
                        Snackbar.make(recyclerView, deletedUser.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // adding on click listener to our action of snack bar.
                                // below line is to add our item to array list with a position.
                                data.add(position, deletedUser);

                                // below line is to notify item is
                                // added to our adapter class.
                                adapter.notifyItemInserted(position);
                            }
                        }).show();
                    }
                    // at last we are adding this
                    // to our recycler view.
                }).attachToRecyclerView(recyclerView);*/

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                String gender;
                String nat;

//                Iterator<User> it = data.iterator();

//                while(it.hasNext()){
//                    name = it.next().getName().getLast();
//                    gender = it.next().getGender();
//                    nat = it.next().getNat();
//
//                    UserModel model = new UserModel();
//                    model.setName(name);
//                    model.setGender(gender);
//                    model.setNat(nat);


                    /*UserRoomModel model = new UserRoomModel();
                    model.setName(data.get(0).getName());
                    model.setGender(data.get(0).getGender());
                    model.setNat(data.get(0).getNat());*/

               ThreadRunnable();
               /* AsyncTaskMethod();
               ExecutorExecuteMethod(); */
               //ExecutorFutureMethod();


            }


        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<UserRoomModel> list;
                        list = DatabaseClass.getDatabase(getActivity()).getDao().getAllData();
                        data.addAll(list);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);


                        /*getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), list.get(0).getName(), Toast.LENGTH_SHORT).show();

                            }
                        });*/
                    }
                }).run();


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //data.clear();
//                DatabaseClass.getDatabase(getActivity()).getDao().deleteAllData();
                data.clear();
                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "RecyclerView cleared", Toast.LENGTH_SHORT).show();
            }
        });

        btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseClass.getDatabase(getActivity()).getDao().deleteAllData();
                data.clear();
                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "RecyclerView and room database cleared", Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }

    private void ExecutorFutureMethod() {
        try {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                DatabaseClass database = DatabaseClass.getDatabase(getActivity());
                DAO dao = database.getDao();
                //dao.insertAllData(model);
                dao.insertAll(data);
                return "Success";
            }
        });

            String resultString = future.get();
            Toast.makeText(getActivity(), resultString, Toast.LENGTH_SHORT).show();



        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void ExecutorExecuteMethod() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            DatabaseClass database = DatabaseClass.getDatabase(getActivity());
            DAO dao = database.getDao();
            //dao.insertAllData(model);
            dao.insertAll(data);
            handler.post(() -> {
                //UI Thread work here
                Toast.makeText(getActivity(), "After Saved", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void ThreadRunnable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatabaseClass database = DatabaseClass.getDatabase(getActivity());
                    DAO dao = database.getDao();
                    //dao.insertAllData(model);
                    dao.insertAll(data);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        }).run();
    }

    private void AsyncTaskMethod() {
        AsyncTask as = new AsyncTask<String, Void, String>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(getActivity(), "Before Save", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                DatabaseClass database = DatabaseClass.getDatabase(getActivity());
                DAO dao = database.getDao();
                //dao.insertAllData(model);
                dao.insertAll(data);
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getActivity(), "After Saved", Toast.LENGTH_SHORT).show();



            }
        }.execute();
    }

    private void addSwipeHelper(RecyclerView recyclerView) {
        Integer width = SwipeHelper.BUTTON_WIDTH_128;
        float textSize = SwipeHelper.TEXT_SIZE_24;

        swipeHelper = new SwipeHelper(getActivity(), width, recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                UnderlayButton deleteButton = new UnderlayButton(
                        getActivity(),
                        "TÖRÖL",
                        SwipeHelper.TEXT_SIZE_24,
                        getActivity().getResources().getColor(R.color.white),
                        0,
                        Color.parseColor(SwipeHelper.COLOR_DANGER),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                               // swipeDeleteFunction(pos);
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                UnderlayButton modifyButton = new UnderlayButton(
                        getActivity(),
                        "Módosít",
                        SwipeHelper.TEXT_SIZE_24,
                        getActivity().getResources().getColor(R.color.white),
                        0,
                        Color.parseColor(SwipeHelper.COLOR_PRIMARY),
//                            getResources().getColor(R.color.bootstrap_brand_danger),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
//                                swipeModifyFunction(pos);
                                Toast.makeText(getActivity(), "Modified", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                UnderlayButton viktorButton = new UnderlayButton(
                        getActivity(),
                        "Viktor",
                        SwipeHelper.TEXT_SIZE_48,
                        getActivity().getResources().getColor(R.color.white),
                        0,
                        Color.parseColor(SwipeHelper.COLOR_SUCCES),
//                            getResources().getColor(R.color.bootstrap_brand_danger),
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
//                                swipeModifyFunction(pos);
                                Toast.makeText(getActivity(), "Viktor", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                underlayButtons.add(deleteButton);
                underlayButtons.add(modifyButton);
                underlayButtons.add(viktorButton);

            }
        };
    }

}

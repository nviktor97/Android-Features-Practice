package com.example.navdraw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.example.navdraw.ViewPagerPack.ViewpagerFragment;
import com.example.navdraw.camera.CameraFragment;
import com.example.navdraw.databind.DataBindFrag;
import com.example.navdraw.deviceinfo.DeviceInfoFragment;
import com.example.navdraw.listview.listViewFragment;
import com.example.navdraw.popup.PopupFragment;
import com.example.navdraw.room.RoomFragment;
import com.example.navdraw.sharedpreferences.SharedPrefFragment;
import com.example.navdraw.spannable.SpannableFragment;
import com.example.navdraw.streamfrag.StreamFragment;
import com.example.navdraw.tabdialog.SwitchDialogFragment;
import com.example.navdraw.textfileop.fileFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    RadioGroup radioGroup;
    NavigationView navigationView;

    Adapter adapter;

    List<NavItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        1
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            drawerLayout.closeDrawer(GravityCompat.START);
                            NavItem menuItem = data.get(position);
                            if(menuItem.mTitle.equals("Save to Txt"))
                                loadNavFragment(new fileFragment(), fileFragment.TAG);
                            else if(menuItem.mTitle.equals("Save to room"))
                                loadNavFragment(new RoomFragment(), RoomFragment.TAG);
                            else if(menuItem.mTitle.equals("Sahared Preferences"))
                                loadNavFragment(new SharedPrefFragment(), SharedPrefFragment.TAG);
                            else if(menuItem.mTitle.equals("Camera"))
                                loadNavFragment(new CameraFragment(), CameraFragment.TAG);
                            else if(menuItem.mTitle.equals("ViewPager"))
                                loadNavFragment(new ViewpagerFragment(), ViewpagerFragment.TAG);
                            else if(menuItem.mTitle.equals("Streams"))
                                loadNavFragment(new StreamFragment(), StreamFragment.TAG);
                            else if(menuItem.mTitle.equals("Spannable"))
                                loadNavFragment(new SpannableFragment(), SpannableFragment.TAG);
                            else if(menuItem.mTitle.equals("ListView"))
                                loadNavFragment(new listViewFragment(), listViewFragment.TAG);
                            else if(menuItem.mTitle.equals("PopUp"))
                                loadNavFragment(new PopupFragment(), PopupFragment.TAG);
                            else if(menuItem.mTitle.equals("Device Info"))
                                loadNavFragment(new DeviceInfoFragment(), DeviceInfoFragment.TAG);
                            else if(menuItem.mTitle.equals("Databinder"))
                                loadNavFragment(new DataBindFrag(), DataBindFrag.TAG);
                            else if(menuItem.mTitle.equals("FragmentDialog")){
                               /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                                alertDialog.setTitle("AlertDialog");
                                String[] items = {"A fragment","B fragment"};
                                int checkedItem = 0;
                                final AlertDialog alert;
                                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                loadNavFragment(new AFragment(), AFragment.TAG);
                                                break;
                                            case 1:
                                                loadNavFragment(new BFragment(), BFragment.TAG);
                                                break;
                                        }
                                    }
                                });
                                alert = alertDialog.create();
                                alert.setCanceledOnTouchOutside(true);
                                alert.show();*/

                                //loadNavFragment(new SwitchDialogFragment(), SwitchDialogFragment.TAG);
                                SwitchDialogFragment yourDialogFragment = SwitchDialogFragment.newInstance("CustomAlert");
                                yourDialogFragment.show(getSupportFragmentManager(), "DialogFragment");

                            }

                            //Toast.makeText(MainActivity.this, menuItem.mTitle, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                })
        );
//2
//        data = new ArrayList<NavItem>();
//        data.add(new NavItem("First","sub"));
//        data.add(new NavItem("Second","sub"));

//3
        data = new ArrayList<NavItem>();
        adapter = new Adapter(this, data);
        data.add(new NavItem("Save to Txt","Save and load text", R.drawable.text));
        data.add(new NavItem("Save to room","Room local database", R.drawable.room));
        data.add(new NavItem("Sahared Preferences","Data stays after session", R.drawable.share));
        data.add(new NavItem("Camera","Take a pic and load it", R.drawable.camera));
        data.add(new NavItem("FragmentDialog","Custom dialog with fragments", R.drawable.alert));
        data.add(new NavItem("ViewPager","3 page viewpager", R.drawable.pager));
        data.add(new NavItem("Streams","Stream actions", R.drawable.stream));
        data.add(new NavItem("Spannable","Spannable text example", R.drawable.span));
        data.add(new NavItem("ListView","ListView example", R.drawable.span));
        data.add(new NavItem("PopUp","PopUp menu example", R.drawable.span));
        data.add(new NavItem("Device Info","Get device information", R.drawable.span));
        data.add(new NavItem("Databinder","Databinding example in fragment", R.drawable.span));

        recyclerView.setAdapter(adapter);






        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void  loadNavFragment(Fragment fragment, String fTAG){

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment, fTAG);
        fragmentTransaction.commit();
    }



}
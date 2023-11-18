package com.example.navdraw.popup;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;


public class PopupFragment extends Fragment {

    public static final String TAG = PopupFragment.class.getName();

    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_fragment, container, false);

        button = v.findViewById(R.id.clickBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(getActivity(), button);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        if(menuItem.getTitle().equals("Toast"))
                        Toast.makeText(getActivity(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        else if(menuItem.getTitle().equals("Close"))
                            popupMenu.dismiss();
                        else if(menuItem.getTitle().equals("Dialog")){

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                            builder.setTitle("POPUP");
                            builder.setMessage("Alert");

                            builder.setNegativeButton("close", (DialogInterface.OnClickListener) (dialog, which) -> {
                                dialog.cancel();
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        return v;
    }
}

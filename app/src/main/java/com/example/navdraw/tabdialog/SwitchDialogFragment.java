package com.example.navdraw.tabdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.navdraw.R;

public class SwitchDialogFragment extends DialogFragment {

    //public static final String TAG = DialogFragment.class.getName();

    RadioGroup switchGroup;

    FrameLayout container;

    public static SwitchDialogFragment newInstance(String title) {
        SwitchDialogFragment yourDialogFragment = new SwitchDialogFragment();

        //example of passing args
        Bundle args = new Bundle();
        args.putString("title", title);
        yourDialogFragment.setArguments(args);

        return yourDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_switcher_dialog, container, false);

        switchGroup = v.findViewById(R.id.radioGroup);

        container = v.findViewById(R.id.fragment_container2);

        switchGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(i);

                if(checkedRadioButton.getText().equals("A fragment")) {
                    //loadNavFragment(new AFragment(), AFragment.TAG);

                    /*FragmentManager fragmentManager= getParentFragmentManager();
                    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_container2, new AFragment(), AFragment.TAG);
                    fragmentTransaction.commit();*/

                    Fragment childFragment = new AFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container2, childFragment).commit();

                }
                else if(checkedRadioButton.getText().equals("B fragment")) {
                    Fragment childFragment = new BFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container2, childFragment).commit();
                }



            }
        });



        return v;
    }

}

package com.example.navdraw.sharedpreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;

public class SharedPrefFragment extends Fragment {

    EditText name;
    EditText age;

    Button btnSave;
    Button btnLoad;

    Button btnClear;

    public static final String TAG = SharedPrefFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shared_preferences_layout, container, false);

        name = v.findViewById(R.id.nameEdit);

        age = v.findViewById(R.id.ageEdit);

        btnSave = v.findViewById(R.id.spSave);

        btnLoad = v.findViewById(R.id.spLoad);

        btnClear = v.findViewById(R.id.clear);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                // write all the data entered by the user in SharedPreference and apply
                myEdit.putString("name", name.getText().toString());
                myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
                myEdit.apply();

            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Fetching the stored data from the SharedPreference
                SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String s1 = sh.getString("name", "");
                int a = sh.getInt("age", 0);

                // Setting the fetched data in the EditTexts
                name.setText(s1);
                age.setText(String.valueOf(a));

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                age.setText("");
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetching the stored data from the SharedPreference
        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("name", "");
        int a = sh.getInt("age", 0);

        // Setting the fetched data in the EditTexts
        name.setText(s1);
        age.setText(String.valueOf(a));
    }

    // Store the data in the SharedPreference in the onPause() method
    // When the user closes the application onPause() will be called and data will be stored
    @Override
    public void onPause() {
        super.onPause();
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("name", name.getText().toString());
        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
        myEdit.apply();
    }
}


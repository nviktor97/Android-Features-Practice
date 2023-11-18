package com.example.navdraw.databind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;
import com.example.navdraw.databinding.DatabindFragmentBinding;

public class DataBindFrag extends Fragment {

    public static final String TAG = DataBindFrag.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.databind_fragment, container, false);

        DatabindFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.databind_fragment, container, false);
        View v = binding.getRoot();
        User user = new User("Viktor", "Debrecen");
        binding.setUser(user);
        user.setCity("Szolnokü");

        binding.sbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.res.setText(binding.input.getText());
            }
        });

        return v;
    }
}

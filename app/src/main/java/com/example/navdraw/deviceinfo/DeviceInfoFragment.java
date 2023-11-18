package com.example.navdraw.deviceinfo;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;
import com.example.navdraw.listview.listViewFragment;

public class DeviceInfoFragment extends Fragment {

    public static final String TAG = DeviceInfoFragment.class.getName();
    TextView tvSerial,tvModel,tvManufacturer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.device_info_fragment, container, false);

        tvSerial = v.findViewById(R.id.serial);
        tvModel = v.findViewById(R.id.model);
        tvManufacturer = v.findViewById(R.id.manufacturer);

        /*tvSerial.setText(Build.SERIAL);
        tvModel.setText(Build.MODEL);*/
       // tvManufacturer.setText(Build.VERSION.RELEASE);

        return v;
    }
}

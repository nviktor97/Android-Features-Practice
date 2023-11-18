package com.example.navdraw.nfc;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;
import com.example.navdraw.spannable.SpannableFragment;

public class NfcFragment extends Fragment {

    public static final String TAG = NfcFragment.class.getName();

    ToggleButton tglRead;

    NfcAdapter nfcAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nfc_layout, container, false);

        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());

        tglRead = v.findViewById(R.id.tglRead);
        return v;
    }


}

package com.example.navdraw.textfileop;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class fileFragment extends Fragment {

    public static final String TAG = fileFragment.class.getName();

    EditText inputMsg;
    Button btnSave;
    Button btnLoad;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.save_to_file, container, false);
        inputMsg = v.findViewById(R.id.etMessage);
        btnSave = v.findViewById(R.id.save);
        btnLoad = v.findViewById(R.id.load);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputMsg.getText().toString();
                //File path = getActivity().getFilesDir();
                File path = Environment.getExternalStorageDirectory();
                String pathWithDir = path.getPath() + "/Download";

                try {
                    FileOutputStream writer = new FileOutputStream(new File(pathWithDir, "message.txt"));
                    writer.write(message.getBytes());
                    writer.close();
                    Toast.makeText(getActivity(), "Siker", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFileRead();

            }
        });

        return v;
    }

    private void textFileRead() {
        File path = Environment.getExternalStorageDirectory();
        String pathWithDir = path.getPath() + "/Download";
        File readFrom = new File(pathWithDir,"message.txt");

        byte[] content = new byte[(int) readFrom.length()];

        try{
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            inputMsg.setText(new String(content));

        }catch(Exception e){
            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }

}

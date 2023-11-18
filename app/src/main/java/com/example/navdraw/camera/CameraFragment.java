package com.example.navdraw.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navdraw.R;

import java.io.File;
import java.io.FileOutputStream;

public class CameraFragment extends Fragment {

    public static final String TAG = CameraFragment.class.getName();

    //public static final int pic_id = 123;
    ImageView imgCamera;
    Button btnOpenCam;
    Button btnLoadPic;

    Button btnClearPic;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    cameraAction(data);
                }
            });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.camera_layout, container, false);

        btnOpenCam = v.findViewById(R.id.camera_button);
        imgCamera = v.findViewById(R.id.click_image);
        btnLoadPic = v.findViewById(R.id.loadPic);
        btnClearPic = v.findViewById(R.id.clearPic);

        btnOpenCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(camera_intent, pic_id);*/

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(intent);

            }
        });

        btnLoadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File path = Environment.getExternalStorageDirectory();
                String pathWithDir = path.getPath() + "/Download/pic.jpeg";
                imgCamera.setImageBitmap(BitmapFactory.decodeFile(pathWithDir));
            }
        });

        btnClearPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCamera.setImageResource(0);
            }
        });

        return v;
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgCamera.setImageBitmap(photo);
        }
    }*/

    public void cameraAction(Intent data){


            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgCamera.setImageBitmap(photo);

            File path = Environment.getExternalStorageDirectory();
            String pathWithDir = path.getPath() + "/Download";

            try {
                FileOutputStream os = new FileOutputStream(new File(pathWithDir,"pic.jpeg"));
                photo.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();
                Toast.makeText(getActivity(), "Siker", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }



    }
}

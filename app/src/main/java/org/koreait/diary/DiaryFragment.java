package org.koreait.diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class DiaryFragment extends Fragment {

    private Button takePhoto;
    private FrameLayout previewFrame;
    private EditText subject;
    private EditText content;

    private CameraSurfaceView cameraView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup =  (ViewGroup)inflater.inflate(R.layout.fragment_diary, container, false);

        takePhoto = viewGroup.findViewById(R.id.takePhoto);
        previewFrame = viewGroup.findViewById(R.id.previewFrame);
        subject = viewGroup.findViewById(R.id.subject);
        content = viewGroup.findViewById(R.id.content);

        cameraView = new CameraSurfaceView(getActivity().getApplicationContext());
        previewFrame.addView(cameraView);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        return viewGroup;
    }

    private void takePicture() {
        cameraView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    String outUriStr = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "이미지", "이미지 캡쳐");

                    if (outUriStr == null) {
                        Log.d("SampleCapture", "이미지 추가 실패");
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }

                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
package com.oracle.dgnmovil.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.oracle.dgnmovil.util.MultipartUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ReportActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView photo;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button button_camera = (Button) findViewById(R.id.button_camera);
        photo = (ImageView) findViewById(R.id.image_photo);
        button_camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);
            new SendHttpRequestTask().execute("monkey", "monkey2");
        }
    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String charset = "UTF-8";
            String requestURL = "http://ec2-54-69-165-46.us-west-2.compute.amazonaws.com:8080/applicate/sayhello";

            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                multipart.addFormField("firstName", params[0]);
                multipart.addFormField("lastName", params[1]);

                File f = new File(ReportActivity.this.getCacheDir(), "tempFile");
                f.createNewFile();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);

                fos.flush();
                fos.close();

                multipart.addFilePart("photo", f);

                List<String> response = multipart.finish();

                System.out.println("SERVER REPLIED:");

                for (String line : response) {
                    System.out.println(line);
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }

            return null;
        }
    }
}

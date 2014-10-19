package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.utilities.Base64;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class ReportActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView photo;
    Bitmap imageBitmap;
    String mImage;
    Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseRef = new Firebase("https://dgn.firebaseio.com/");

        setContentView(R.layout.activity_report);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView titleTextView = (TextView) findViewById(titleId);
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

        TextView informacion = (TextView) findViewById(R.id.informacion);
        informacion.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

        TextView comentarios = (TextView) findViewById(R.id.comentarios);
        comentarios.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

        TextView fotografia = (TextView) findViewById(R.id.fotografia);
        fotografia.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

        Button submitBtn = (Button) findViewById(R.id.send_report);
        submitBtn.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

        Button photoBtn = (Button) findViewById(R.id.button_camera);
        photoBtn.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

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

        Button enviar = (Button) findViewById(R.id.send_report);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase postRef = mFirebaseRef.child("reportes");
                Map<String, String> post = new HashMap<String, String>();

                EditText producto_text = (EditText) findViewById(R.id.producto_textbox);
                String producto = producto_text.getText().toString();

                post.put("producto", producto);

                EditText ubicacion_text = (EditText) findViewById(R.id.empresa_textbox);
                String ubicacion = ubicacion_text.getText().toString();

                post.put("ubicacion", ubicacion);

                EditText comentario_text = (EditText) findViewById(R.id.comentarios_textbox);
                String comentarios = ubicacion_text.getText().toString();

                post.put("comentario", comentarios);

                if (mImage != null) {
                    post.put("imagen", mImage);
                }

                postRef.push().setValue(post);

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Tu reporte has sido guardado", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        },
                        1000);

            }
        });

        Button contact = (Button) findViewById(R.id.contact_btn);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "36336892"));
                startActivity(callIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);
            mImage = imageToBase64(imageBitmap);
            // new SendHttpRequestTask().execute("monkey", "monkey2");
        }
    }

    private String imageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeBytes(b);
    }

}

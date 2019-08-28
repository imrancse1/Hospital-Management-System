package com.example.bubt.MedScape;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewPresiptionActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO = "com.example.bubt.MedScape.EXTRA_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_presiption);
        ImageView imageView = findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] photo = bundle.getByteArray(EXTRA_PHOTO);
            if (photo != null) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            } else {
                imageView.setImageResource(R.drawable.prescription);
            }
        }
    }
}

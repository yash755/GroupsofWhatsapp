package com.example.sanchit.groupsofwhatsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JoinGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        String name   = getIntent().getExtras().getString("name");
        final String icon   = getIntent().getExtras().getString("icon");
       // String link   = getIntent().getExtras().getString("link");

        //ImageView imageView = (ImageView)findViewById(R.id.icon);
        TextView textView = (TextView)findViewById(R.id.textView);
        Button button = (Button)findViewById(R.id.button);

        textView.setText(name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Uri uri = Uri.parse(icon);
                 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                 startActivity(intent);

            }
        });
      //  new DownloadImageTask(imageView).execute(icon);

//        new DownloadImageTask(imageView).execute("https://www.w3schools.com/css/trolltunga.jpg");
    }
}

package com.example.sanchit.groupsofwhatsapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

        GridView gridView;
    int[] images = {R.mipmap.all, R.mipmap.adult, R.mipmap.artandculture, R.mipmap.bitcoin, R.mipmap.blog,R.mipmap.buyandsell, R.mipmap.clubs, R.mipmap.dating, R.mipmap.education, R.mipmap.fashion, R.mipmap.friendship, R.mipmap.funny, R.mipmap.marketing, R.mipmap.michevious, R.mipmap.money, R.mipmap.music, R.mipmap.news, R.mipmap.sports, R.mipmap.spritiual, R.mipmap.youtube};
//    int[] images = {R.drawable.adult, R.drawable.artandculture, R.drawable.bitcoin, R.drawable.blog,R.drawable.buyandsell, R.drawable.clubs, R.drawable.dating, R.drawable.education, R.drawable.fashion, R.drawable.friendship, R.drawable.funny, R.drawable.marketing, R.drawable.michevious, R.drawable.money, R.drawable.music, R.drawable.news, R.drawable.sports, R.drawable.spritiual, R.drawable.youtube};



    String[] names = {"All","Adult 18+", "Arts & Culture", "Bitcoin/Cryptocurrency", "Blogging and Website" ,"Buy & Sale" , "FAN CLUBS", "Dating", "Educational","Fashion Trends", "Friendship", "Funny", "Digital Marketing", "Mischief/Affairs", "Make Money Online", "Music", "News and politics", "Sports", "Spiritual/Religious", "YouTube"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AddGroup.class);
                startActivity(intent);
            }
        });




        gridView = (GridView) findViewById(R.id.gridView);
        GridAdapter adapter =  new GridAdapter(Home.this, images, names);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               Toast.makeText(Home.this, "Clicked letter "+ names[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, Groups.class);
                intent.putExtra("category", names[position]);
                startActivity(intent);


            }
        });
  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
            case R.id.rate:
                Uri uri = Uri.parse("market://details?id=" + Home.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Home.this.getPackageName())));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
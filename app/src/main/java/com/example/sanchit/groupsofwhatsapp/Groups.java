package com.example.sanchit.groupsofwhatsapp;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

public class Groups extends Activity implements SearchView.OnQueryTextListener
{
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<SearchGroup> employeeArrayList;
    private SearchGroupAdapter employeeAdapter;
    int count=0;
    InterstitialAd interstitialAd;
    List<String> groupsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        mSearchView=(SearchView) findViewById(R.id.searchView);
        mListView=(ListView) findViewById(R.id.listview);


        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd = new InterstitialAd(Groups.this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(adRequest);





        String extras   = getIntent().getExtras().getString("category");

        if (!new Util().check_connection(Groups.this)) {
            new SweetAlertDialog(Groups.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("No Internent Connection")
                    .setContentText("Won't be able to go in!")
                    .setConfirmText("Go to Settings!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                            sDialog.cancel();
                        }
                    })
                    .show();
        }


        final android.app.AlertDialog dialog = new SpotsDialog(this, R.style.Custom1);

        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        System.out.println("Reference is : "+mDatabase);

        employeeArrayList=new ArrayList<SearchGroup>();

        dialog.show();


        Query query;
        if(extras.equals("All")) {
            query = mDatabase.child("groups").orderByChild("category");
        }
        else {

            query = mDatabase.child("groups").orderByChild("category").equalTo(extras);
        }
        query.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren())
        {
            System.out.println("Middle value is "+child.toString());
            String name=null, icon=null, link=null;

            for (DataSnapshot ch : child.getChildren()) {

                 //   System.out.println("Middle value is more inside "+ch.toString());
                    System.out.println("Key is" + ch.getKey()+"key");
                    System.out.println("value is " + ch.getValue());
                    if (ch.getKey().equals("name")) {
                        name = ch.getValue().toString();
                        System.out.println("Updated name");
                    }
                if (ch.getKey().equals("link")) {
                    link = ch.getValue().toString();
                    System.out.println("Updated link");

                }
                if (ch.getKey().equals("icon")) {
                    icon = ch.getValue().toString();
                    System.out.println("Updated icon");

                }
            }
            if(name!=null &&  icon!=null  &&  link!=null)
            {
            //    Toast.makeText(Groups.this, "Adding check "+name+" "+icon+" "+link,Toast.LENGTH_SHORT).show();
                System.out.println("Adding check "+name+" "+icon+" "+link);
                employeeArrayList.add(new SearchGroup(name, icon, link));
                count++;
            }
        }
        dialog.hide();
        if(count==0){
            TextView textView4 = (TextView)findViewById(R.id.textView4);
            textView4.setText("No group found in this category");
        }
        else{
            TextView textView4 = (TextView)findViewById(R.id.textView4);
            textView4.setText("");
            count=0;
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});


        employeeAdapter=new SearchGroupAdapter(Groups.this, employeeArrayList);
        mListView.setAdapter(employeeAdapter);


        mListView.setTextFilterEnabled(false);
        setupSearchView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }

             //   Toast.makeText(Groups.this, employeeArrayList.get(position).getName()+employeeArrayList.get(position).getIcon()+employeeArrayList.get(position).getLink(),Toast.LENGTH_SHORT).show();
                System.out.println("Check this nil "+employeeArrayList.get(position).getName()+employeeArrayList.get(position).getIcon()+employeeArrayList.get(position).getLink());

                Intent i = new Intent(Groups.this, JoinGroup.class);
                i.putExtra("name", employeeArrayList.get(position).getName());
                i.putExtra("icon", employeeArrayList.get(position).getIcon());
                i.putExtra("link", employeeArrayList.get(position).getLink());
                startActivity(i);
            }
        });
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }


    @Override
    public boolean onQueryTextChange(String newText)
    {
        android.widget.Filter filter = employeeAdapter.getFilter();
        filter.filter(newText);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
      //  Toast.makeText(Groups.this, query, Toast.LENGTH_SHORT).show();
        return true;
    }
}
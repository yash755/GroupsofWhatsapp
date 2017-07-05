package com.example.sanchit.groupsofwhatsapp;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;


public class AddGroup extends AppCompatActivity {
    String str1="";
    String str2="";
    String str3="";
    String str4="";
    UserLocalStore userLocalStore;
    int flag=0,done=0;
    int a=0;
    String link_icon=null,heading=null;
    DatabaseReference mDatabase;
    int checked=0;
    android.app.AlertDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        final EditText editText1,editText2;
        Button button;
        Spinner spinner1, spinner2;

        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        button = (Button)findViewById(R.id.button);

        userLocalStore = new UserLocalStore(AddGroup.this);

        dialog = new SpotsDialog(this, R.style.Custom);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str3 = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str4 = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (!new Util().check_connection(AddGroup.this)) {
            new SweetAlertDialog(AddGroup.this, SweetAlertDialog.WARNING_TYPE)
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



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str1 = editText1.getText().toString();
                str2 = editText2.getText().toString();


                if(editText1.getText().toString().trim().equals("")  || editText2.getText().toString().trim().equals("") ||  str3.trim().equals("")  ||  str4.trim().equals("")) {
                    if (editText1.getText().toString().trim().equals("")) {
                            YoYo.with(Techniques.Tada)
                                .duration(700)
                                .playOn(findViewById(R.id.editText1));
                    }
                    if (editText2.getText().toString().trim().equals("")) {
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .playOn(findViewById(R.id.editText2));
                    }
                    if (str3.trim().equals("")) {
                        YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(findViewById(R.id.spinner1));
                    }
                    if (str4.trim().equals("")) {
                        YoYo.with(Techniques.Tada)
                            .duration(700)
                             .playOn(findViewById(R.id.spinner2));
                    }
                }
                 else {

                    dialog.show();

String test = str2;
                    if(test.startsWith("https://chat.whatsapp.com/invite/") ){
                        test = test.replace("https://chat.whatsapp.com/invite/", "");
                        System.out.println("Answer is sent from invite");
                        checkValidLink(test);
                    }

                    else if(test.startsWith("https://chat.whatsapp.com/") )// ||  str2.startsWith("https://chat.whatsapp.com/invite/"))
                    {
                        test = test.replace("https://chat.whatsapp.com/", "");
                        System.out.println("Answer is sent from basic");
                        checkValidLink(test);
                    }
                    else if(test.startsWith("http://chat.whatsapp.com/invite/") ){
                        test = test.replace("http://chat.whatsapp.com/invite/", "");
                        System.out.println("Answer is sent from invite");
                        checkValidLink(test);
                    }

                    else if(test.startsWith("http://chat.whatsapp.com/") )// ||  str2.startsWith("https://chat.whatsapp.com/invite/"))
                    {
                        test = test.replace("http://chat.whatsapp.com/", "");
                        System.out.println("Answer is sent from basic");
                        checkValidLink(test);
                    }

                    else{
                        System.out.println("Answer is bilkul invalid");
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .playOn(findViewById(R.id.editText2));
                        Toast.makeText(AddGroup.this, "Invalid link",Toast.LENGTH_SHORT).show();
                        dialog.hide();
                        checked=1;
                    }

            }
            }
        });
    }

    public void checkValidLink(String domain)
    {
        System.out.println("Response for " + domain);

//        http://139.59.59.105:5005/geticon/9zd6CodxEA0C
        String url = "http://139.59.59.105:5005/geticon/"+domain;
        Map<String, String> params = new HashMap<String, String>();
//        params.put("url",domain);

        System.out.println("str0" + params.toString());

//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        CustomRequest1 jsObjRequest = new CustomRequest1(Request.Method.GET, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response is  : "+response.toString());
                System.out.println("Answer is response : "+response.toString());


                try {
                        if(response.getString("heading").equals(""))
                        {
                            System.out.println("Answer is response in if");
                            a=0;
                        }
                        else {
                            a = 1;
                            link_icon = response.getString("success");
                            heading = response.getString("heading");
                            System.out.println("Answer is response in else");

                        }
                    func();

                    }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            //        dialog.hide();
                    new Util().showerrormessage(AddGroup.this, "Time Out Error.....Try Later!!!");

                } else if (error instanceof AuthFailureError) {
              //      dialog.hide();
                    new Util().showerrormessage(AddGroup.this, "Authentication Error.....Try Later!!!");
                } else if (error instanceof ServerError) {
                //    dialog.hide();
                    new Util().showerrormessage(AddGroup.this, "Server Error.....Try Later!!!");
                } else if (error instanceof NetworkError) {
                  //  dialog.hide();
                    new Util().showerrormessage(AddGroup.this, "Network Error.....Try Later!!!");
                } else if (error instanceof ParseError) {
                    //dialog.hide();
                    Log.d("Response: ", error.toString());
                    System.out.println("Resonse" + error.toString());
                    new Util().showerrormessage(AddGroup.this, "Unknown Error.....Try Later!!!");
                }
            }
        }


        ); /*{

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
*/
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);

    }

    public void func(){

        if(a==0){

            if(checked==0){
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(findViewById(R.id.editText2));
                Toast.makeText(AddGroup.this, "Invalid link",Toast.LENGTH_SHORT).show();
                System.out.println("Answer is found invalid");
                dialog.hide();
            }
        }

        else{
            final SpotsDialog dialog = new SpotsDialog(AddGroup.this, R.style.Custom);

            dialog.show();
            System.out.println("Answer is valid");

            flag=0;
            done=0;
            mDatabase = FirebaseDatabase.getInstance().getReference();


            System.out.println("AddGroup mein checking for "+str2);

            Query query= mDatabase.child("groups").orderByChild("link").equalTo(str2);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String s = dataSnapshot.toString();
                    System.out.println("value is " + s);

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        System.out.println("Middle value is " + child.toString());
                        for (DataSnapshot ch : child.getChildren()) {

                            System.out.println("Key is " + ch.getKey());
                            System.out.println("value is " + ch.getValue());
                            if (ch.getKey().toString().equals("link") && ch.getValue().toString().equals(str2)) {
                                System.out.println("AddGroup mein found flag " + flag);
                                flag = 1;
                                System.out.println("AddGroup mein found flag " + flag);
                            }
                        }
                    }

                    System.out.println("AddGroup mein finally flag " + flag);

                    if (flag == 0) {
                        int userId3 = userLocalStore.getCount();
                        String name = str1;
                        String link = str2;
                        String language = str3;
                        String category = str4;
//                                String icon = "sanchitmittal18@gmail.com";

                        userId3++;
                        userLocalStore.setCount(userId3);

                        GroupObject groupObject = new GroupObject();
                        groupObject.setName(heading);
                        groupObject.setLink(link);
                        groupObject.setLanguage(language);
                        groupObject.setCategory(category);
                        groupObject.setIcon(link_icon);


                        try {
                            mDatabase.child("groups").push().setValue(groupObject);
                        } catch (DatabaseException e) {
                            e.printStackTrace();
                        }
                        dialog.hide();
                        Intent intent = new Intent(AddGroup.this, Home.class);
                        startActivity(intent);
//                                Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
//                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //                              startActivity(intent);
                        done = 1;
                    } else if (flag == 1 && done == 0) {
                        Toast.makeText(AddGroup.this, "Group Already Exist", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                        Intent intent = new Intent(AddGroup.this, Home.class);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            dialog.hide();
        }
    }
}
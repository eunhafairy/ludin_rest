package com.bsit3omagitech.ludin_rest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    Button searchBtn, clearBtn;
    EditText l_name, f_name, m_name, age, bday, civil_status, natl, contact, email, search;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        reg();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
    private void apiCall(Context c){


        loading.setVisibility(View.VISIBLE);
        searchBtn.setVisibility(View.GONE);
        clearBtn.setVisibility(View.GONE);
        RequestQueue r = Volley.newRequestQueue(c);
        r.start();

        String url = "https://run.mocky.io/v3/06a0ce26-e8c4-439c-9864-7e1738bd7085";

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url , null,
               response ->{

                   try {
                       boolean flag = false;
                       //get json array
                       JSONArray jsonArr = response.getJSONArray("users");
                       JSONObject targetData = new JSONObject();
                       //get json object with id = user input
                        for(int i = 0; i < jsonArr.length(); i++){

                            JSONObject data = jsonArr.getJSONObject(i);

                            Log.d("laman", "laman ng data.getString(id) ay: " + data.getString("id") + " at ang laman ng searchGetText ay; " + search.getText());

                            if(data.getString("id").trim().equals(search.getText().toString().trim())){

                                flag = true;
                                targetData = data;

                                    //meron
                                    Log.d("meron", "meron");
                                    f_name.setText(targetData.getString("f_name"));
                                    l_name.setText(targetData.getString("l_name"));
                                    m_name.setText(targetData.getString("m_name"));
                                    age.setText(targetData.getString("age"));
                                    bday.setText(targetData.getString("birthdate"));
                                    civil_status.setText(targetData.getString("civil_status"));
                                    natl.setText(targetData.getString("nationality"));
                                    contact.setText(targetData.getString("contact"));
                                    email.setText(targetData.getString("email"));





                            }



                        }

                       if(!flag){
                           //wala
                           Log.d("wala", "wala");
                           AlertDialog.Builder builder = new AlertDialog.Builder(this);
                           builder.setTitle("404")
                                   .setMessage("No data found.")
                                   .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           clearText();
                                       }

                                   })
                                   .setCancelable(true);
                           AlertDialog dialog = builder.create();
                           dialog.show();
                       }


                       loading.setVisibility(View.GONE);
                       searchBtn.setVisibility(View.VISIBLE);
                       clearBtn.setVisibility(View.VISIBLE);


                       return;




                   } catch (JSONException e) {
                       Log.d("May error", e.toString());
                       e.printStackTrace();
                   }


               },
                error->{

                      Log.d("error http",error.toString());

                });
        r.add(json);


    }

    private void init(){

        searchBtn = findViewById(R.id.searchBtn);
        l_name = findViewById(R.id.l_name);
        f_name = findViewById(R.id.f_name);
        m_name = findViewById(R.id.m_name);
        age = findViewById(R.id.age);
        bday = findViewById(R.id.bday);
        civil_status = findViewById(R.id.status);
        natl = findViewById(R.id.natl);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        search = findViewById(R.id.search);
        loading = findViewById(R.id.loading);
        clearBtn = findViewById(R.id.clearBtn);

    }

    private void clearText(){

        f_name.setText("");
        l_name.setText("");
        m_name.setText("");
        age.setText("");
        bday.setText("");
        civil_status.setText("");
        natl.setText("");
        contact.setText("");
        email.setText("");
        search.setText("");


    }

    private void reg(){

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get api
                apiCall(getApplicationContext());
                //set edit text to retrieved json object
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearText();
            }
        });


    }
}
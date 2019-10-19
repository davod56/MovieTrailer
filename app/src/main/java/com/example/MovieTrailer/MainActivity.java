package com.example.MovieTrailer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText user,pass,email;
    Button insr;
    RequestQueue requestQueue;
    AlertDialog.Builder progressDialog;
    String username,password,emailStr;
    String URL="https://10.0.3.2/android/php_scripts_insert_data_to_database/insert_record.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue= Volley.newRequestQueue(getApplicationContext());

        progressDialog= new AlertDialog.Builder(this);



        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        email=findViewById(R.id.email);

        insr=findViewById(R.id.insert);
        insr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=user.getText().toString();
                password=pass.getText().toString();
                emailStr=email.getText().toString();

                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.setMessage(response);
                        progressDialog.show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.setMessage("OOOPs " + error);
                        progressDialog.show();
                    }
                }){
                    @Override
                    protected Map<String ,String > getParams(){

                        Map<String,String > params= new HashMap<String,String>();

                        params.put("username",username);
                        params.put("password",password);
                        params.put("email",emailStr);

                        return params;

                    }
                };
                        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(stringRequest);

            }
        });



    }
}

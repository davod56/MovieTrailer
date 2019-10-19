package com.example.MovieTrailer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener{
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    ArrayList<Contact> arrayList = new ArrayList<>();
    String URL="http://10.0.3.2/android/php_scripts_insert_data_to_database/";
    String url = URL+"get_data.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mRecyclerView = findViewById(R.id.second_recycler_view);
        displayListView();
        arrayList.clear();



    }
    private void displayListView() {


        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                Contact user;
                int i=0;
                while(i<response.length()){
                    try {

                        jsonObject=response.getJSONObject(i);
                        user=new Contact(jsonObject.getString("id"),jsonObject.getString("name")
                                ,jsonObject.getString("image"),jsonObject.getString("description"),
                                jsonObject.getString("url"),jsonObject.getString("subject")
                                ,jsonObject.getString("time"),jsonObject.getString("year"),
                                jsonObject.getString("director"),jsonObject.getString("actors"),jsonObject.getString("language"));

                        Log.d("__","affectation");
                        arrayList.add(i,user);
                        i++;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Log.d("__", "get"+ arrayList.size());
                horizontalRecyclerView(arrayList);
                mAdapter=new RecyclerAdapter(getApplicationContext(),arrayList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(TestActivity.this);


            }
        }, new Response.ErrorListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_LONG);
                error.printStackTrace();

            }
        });
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);


    }
    public void onItemClick(int position) {

        Intent mIntent=new Intent(getApplicationContext(),information_activity.class);
        Contact clickedItem=arrayList.get(position);
        String extra_id = "id";
        mIntent.putExtra(extra_id,clickedItem.getId());
        String extra_Name = "name";
        mIntent.putExtra(extra_Name,clickedItem.getName());
        String extra_des = "des";
        mIntent.putExtra(extra_des,clickedItem.getDes());
        String extra_image = "image";
        mIntent.putExtra(extra_image,clickedItem.getImage());
        String extra_download = "download";
        mIntent.putExtra(extra_download,clickedItem.getDowndoad());
        String extra_subject = "subject";
        mIntent.putExtra(extra_subject,clickedItem.getSubject());
        String extra_time = "time";
        mIntent.putExtra(extra_time,clickedItem.getTime());
        String extra_year = "year";
        mIntent.putExtra(extra_year,clickedItem.getYear());
        String extra_director = "director";
        mIntent.putExtra(extra_director,clickedItem.getDirector());
        String extra_actors = "actor";
        mIntent.putExtra(extra_actors,clickedItem.getActors());
        String extra_language="lang";
        mIntent.putExtra(extra_language,clickedItem.getLanguage());

        startActivity(mIntent);

    }
    public void horizontalRecyclerView(ArrayList<Contact> list2){

        RecyclerAdapter firstAdapter = new RecyclerAdapter(getApplicationContext(),list2);
        MultiSnapRecyclerView firstRecyclerView = findViewById(R.id.second_recycler_view);
        LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        firstRecyclerView.setLayoutManager(firstManager);
        firstRecyclerView.setAdapter(firstAdapter);
    }
}

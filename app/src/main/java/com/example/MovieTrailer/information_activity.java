package com.example.MovieTrailer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class information_activity extends AppCompatActivity {

    ImageView imageView;
    TextView name,subject,time,year,Director,Actors,description,language;
    Button download,onlineMovie;
    String downloadURL;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_activity);
        imageView=findViewById(R.id.imageView2);
        name=findViewById(R.id.name_textView);
        subject=findViewById(R.id.subject_textView);
        time=findViewById(R.id.time_textView);
        year=findViewById(R.id.Year_textView);
        Director=findViewById(R.id.Director_textView);
        Actors=findViewById(R.id.Actors_textView);
        description=findViewById(R.id.description_textView);
        download=findViewById(R.id.download_button);
        onlineMovie=findViewById(R.id.onlineMovieButton);
        language=findViewById(R.id.language_textView);
        Intent myIntent=getIntent();
        String image=myIntent.getStringExtra("image");
        String  Id =myIntent.getStringExtra("id");
        String des=myIntent.getStringExtra("des");
        String Name=myIntent.getStringExtra("name");
        downloadURL =myIntent.getStringExtra("download");
        String Subject=myIntent.getStringExtra("subject");
        String Time=myIntent.getStringExtra("time");
        String Year=myIntent.getStringExtra("year");
        String director=myIntent.getStringExtra("director");
        String actors=myIntent.getStringExtra("actor");
        String lang=myIntent.getStringExtra("lang");

        name.setText("نام: " +Name);
        subject.setText("موضوع: "+ Subject);
        time.setText("مدت زمان فیلم: "+Time);
        year.setText("سال تولید: " +Year);
        Director.setText("کارگردان: " + director);
        Actors.setText("بازیگران: " +actors);
        description.setText("خلاصه ی فیلم: " +des);
        language.setText("زبان: "+lang);

        Picasso.get().load(image)
                .error(R.drawable.ic_launcher_background)
                .resize(300,500)
                .placeholder(R.drawable.indicator_corner_bg)
                .into(imageView);
        download();
        onlineMovie();
    }

    public void download(){

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void onlineMovie(){
       onlineMovie.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

              Intent intent=new Intent(getApplicationContext(),OnlineMoviesActivity.class);

              intent.putExtra("URL",downloadURL);
              startActivity(intent);

           }
       });

    }


}

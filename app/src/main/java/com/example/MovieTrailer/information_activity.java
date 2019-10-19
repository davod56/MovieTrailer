package com.example.MovieTrailer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class information_activity extends RuntimePermissionsActivity  {
    private static final int REQUEST_PERMISSIONS = 20;
    ImageView imageView;
    TextView name, subject, time, year, Director, Actors, description, language;
    Button download, onlineMovie;
    String downloadURL,Name;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_activity);
        imageView = findViewById(R.id.imageView2);
        name = findViewById(R.id.name_textView);
        subject = findViewById(R.id.subject_textView);
        time = findViewById(R.id.time_textView);
        year = findViewById(R.id.Year_textView);
        Director = findViewById(R.id.Director_textView);
        Actors = findViewById(R.id.Actors_textView);
        description = findViewById(R.id.description_textView);
        download = findViewById(R.id.download_button);
        onlineMovie = findViewById(R.id.onlineMovieButton);
        language = findViewById(R.id.language_textView);
        Intent myIntent = getIntent();
        String image = myIntent.getStringExtra("image");
        String Id = myIntent.getStringExtra("id");
        String des = myIntent.getStringExtra("des");
         Name = myIntent.getStringExtra("name");
        downloadURL = myIntent.getStringExtra("download");
        String Subject = myIntent.getStringExtra("subject");
        String Time = myIntent.getStringExtra("time");
        String Year = myIntent.getStringExtra("year");
        String director = myIntent.getStringExtra("director");
        String actors = myIntent.getStringExtra("actor");
        String lang = myIntent.getStringExtra("lang");

        name.setText("نام: " + Name);
        subject.setText("موضوع: " + Subject);
        time.setText("مدت زمان فیلم: " + Time);
        year.setText("سال تولید: " + Year);
        Director.setText("کارگردان: " + director);
        Actors.setText("بازیگران: " + actors);
        description.setText("خلاصه ی فیلم: " + des);
        language.setText("زبان: " + lang);

        Picasso.get().load(image)
                .error(R.drawable.ic_launcher_background)
                .resize(300, 500)
                .placeholder(R.drawable.indicator_corner_bg)
                .into(imageView);
        dialog();
        download();
        onlineMovie();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void dialog(){

        final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();
        boolean isFirstRun = shared.getBoolean("FIRSTRUN", true);
        if (isFirstRun){
            information_activity.super.requestAppPermissions(new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WAKE_LOCK,Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET},
                    R.string
                            .runtime_permissions_txt
                    , REQUEST_PERMISSIONS);
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }
    }
    public void download() {

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // new Download().execute(downloadURL);
               new  DownloadFile().execute(downloadURL);
            }
        });
    }

    public void onlineMovie() {
        onlineMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), OnlineMoviesActivity.class);

                intent.putExtra("URL", downloadURL);
                startActivity(intent);

            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        long StartTime;
        long EndTime;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(information_activity.this);
            this.progressDialog.setMessage("Downloading file...");
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            StartTime=System.currentTimeMillis();
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                @SuppressLint
                        ("SimpleDateFormat") String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL
                String fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1);

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;

                //External directory path to save file
                String folder = Environment.getExternalStorageDirectory() + File.separator + "Movie Trailer/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    //Log.d( "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                EndTime=System.currentTimeMillis();
                return "Downloaded at: " + folder + fileName;
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            long dataSize=message.length()/1024;
            long takenTime = EndTime - StartTime;
            long s = takenTime / 1000;
            double speed = dataSize / s;


            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
        }
    }
}




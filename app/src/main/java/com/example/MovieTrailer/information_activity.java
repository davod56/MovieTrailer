package com.example.MovieTrailer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.example.MovieTrailer.utils.Utils;
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
    private static String dirPath;
    Dialog dialog;
    ImageView imageView;
    TextView name, subject, time, year, Director, Actors, description, language,textViewProgressOne;
    Button download, onlineMovie , buttonCancelOne;
    String downloadURL,Name;
    ProgressBar progressBarOne;
    int downloadIdOne;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_activity);
        PRDownloader.initialize(getApplicationContext());

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
        //dialog();
       // download();
        onlineMovie();

        dirPath = Utils.getRootDirPath(getApplicationContext());

        //init();

        onClickListenerOne();

    }
    private void init() {


    }

    public void onClickListenerOne() {


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               dialog=new Dialog(information_activity.this);
               dialog.setContentView(R.layout.alert_dialog);
               dialog.setTitle(" ");
               buttonCancelOne = dialog.findViewById(R.id.buttonCancelOne);
               textViewProgressOne =dialog. findViewById(R.id.textViewProgressOne);
               progressBarOne =dialog. findViewById(R.id.progressBarOne);



                if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.pause(downloadIdOne);
                    return;
                }
                progressBarOne.setIndeterminate(true);
                progressBarOne.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.resume(downloadIdOne);
                    return;
                }
                //String url1="https://as9.cdn.asset.aparat.com/aparat-video/89acd7a5306fadf23953aa1d95d072b317548699-144p__25700.mp4";
               // String url="https://as3.cdn.asset.aparat.com/aparat-video/f596bf876b3fee2273d1ccbec4bbb83c17509982-144p__44337.mp4";

                downloadIdOne = PRDownloader.download(downloadURL, dirPath, Name)
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                progressBarOne.setIndeterminate(false);
                                buttonCancelOne.setEnabled(true);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                buttonCancelOne.setEnabled(true);
                                progressBarOne.setProgress(0);
                                textViewProgressOne.setText("");
                                downloadIdOne = 0;
                                progressBarOne.setIndeterminate(false);
                                dialog.dismiss();
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarOne.setProgress((int) progressPercent);
                                textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDownloadComplete() {
                                buttonCancelOne.setEnabled(true);
                                download.setVisibility(View.GONE);
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Error error) {
                                Toast.makeText(getApplicationContext(), getString(Integer.parseInt("some_error occurred")) + " " + "1", Toast.LENGTH_SHORT).show();
                                textViewProgressOne.setText("");
                                progressBarOne.setProgress(0);
                                downloadIdOne = 0;
                                buttonCancelOne.setEnabled(true);
                                progressBarOne.setIndeterminate(false);
                            }
                        });
                buttonCancelOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PRDownloader.cancel(downloadIdOne);
                    }
                });
         dialog.show();
            }

        });



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
           progressDialog = new ProgressDialog(information_activity.this);
           progressDialog.setMessage("Downloading file...");
           progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
           progressDialog.setCancelable(false);
           progressDialog.show();
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
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();

        }
    }



}




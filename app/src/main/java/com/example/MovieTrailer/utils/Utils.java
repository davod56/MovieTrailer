package com.example.MovieTrailer.utils;

import android.content.Context;
import android.os.Environment;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;

/**
 * Created by amitshekhar on 13/11/17.
 */

public final class Utils {

    private Utils() {
        // no instance
    }

    public static String getRootDirPath(Context context) {
        String folder = Environment.getExternalStorageDirectory() + File.separator + "Movie Trailer/";

        //Create androiddeft folder if it does not exist
        File directory = new File(folder);

        if (!directory.exists()) {
            directory.mkdirs();
        }
        return String.valueOf(directory);
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

}
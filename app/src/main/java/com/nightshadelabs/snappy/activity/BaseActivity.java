package com.nightshadelabs.snappy.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nightshadelabs.snappy.model.Story;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by davidshellabarger on 6/11/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static String LOG_TAG = "Snappy";
    private static int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;


    public File newFileFor(Story story) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Snappy_" + timeStamp + "_";
        File storageDir = getAlbumStorageDir("Snappy");
        if (storageDir == null) {
            throw new IOException("Need Permission");
        }
        File album = new File(storageDir.getAbsoluteFile(), story.toString());
        album.mkdirs();
        File image = new File(album, imageFileName+".jpg");
        /*File image = File.createTempFile(
                imageFileName,
                ".jpg",
                album
        );*/

        // Save a file: path for use with ACTION_VIEW intents
        Log.i(LOG_TAG, "file:" + image.getAbsolutePath());
        return image;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_DENIED) {
            askForPermission();
            return null;
        }

        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created " + file.getAbsolutePath());
        }
        return file;
    }

    private void askForPermission() {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }
}

package com.example.bill.downloadimage;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up storage reference called storageRef
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef = storageRef.child("image.jpeg");

        File localFile;

        //Download file from firebase
        try{
            localFile = File.createTempFile("image", "jpeg");
            FileDownloadTask fileDownloadTask = storageRef.getFile(localFile);
            OnSuccessListener successListener = new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("download", "success!");
                }
            };
            OnFailureListener failureListener = new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("download", "failure!");
                }
            };
            fileDownloadTask.addOnSuccessListener(successListener);
            fileDownloadTask.addOnFailureListener(failureListener);

        }

        catch(Exception e){
            Log.e("download",e.getMessage());

        }

        //Upload file to firebase
        Uri file = Uri.parse("android.resource://com.example.bill.downloadimage/drawable/eclipse");


        storageRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.d("upload", "Super extra awesome success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Log.d("upload", "Super extra sad failure");
                    }
                });
    }
}

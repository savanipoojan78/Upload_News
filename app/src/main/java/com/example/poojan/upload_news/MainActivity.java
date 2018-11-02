package com.example.poojan.upload_news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int RC_PHOTO_PICKER = 2;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private Uri filePath;
    news News;
    String publishdate;
    String downloadUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    long i;
    EditText section, date, title, trailtext, weburl, author, time;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mPhotoPickerButton = (Button) findViewById(R.id.photovalue);
        section = (EditText) findViewById(R.id.sectionvalue);
        date = (EditText) findViewById(R.id.datevalue);
        title = (EditText) findViewById(R.id.titlevalue);
        trailtext = (EditText) findViewById(R.id.trailtextvalue);
        weburl = (EditText) findViewById(R.id.weburlvalue);
        author = (EditText) findViewById(R.id.authorvalue);
        time = (EditText) findViewById(R.id.timevalue);
        Button submit = (Button) findViewById(R.id.upload);
        News = new news();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("response");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        initial();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // increase();
                uploaddata();
                //Log.e("Calling The uploadImage", News.getThumbnail());
                // Log.e("Child after Intial:----",Long.toString(i));

               // Log.e("Child after i++ Intial:", Long.toString(i));
                //getvalues();


                //Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();


            }

        });
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });
    }

//    private void getvalues() {
//        String dateh = date.getText().toString();
//        String timeh = time.getText().toString();
//        publishdate = dateh.concat("T").concat(timeh).concat("Z");
//        News.setByLine(author.getText().toString());
//        News.setWebPublicationDate(publishdate);
//        News.setWebSectionName(section.getText().toString());
//        News.setWebTitle(title.getText().toString());
//        News.setWebTrailText(trailtext.getText().toString());
//        News.setWebUrl(weburl.getText().toString());
//        News.setThumbnail(downloadUrl);
//       //String g=downloadUrl;
//        //Log.e("URL::::--",g);
//
//       // Log.e("PublishDate::------", publishdate);
//
//    }

    private void initial() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploaddata() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
                                    initial();

                                    String dateh = date.getText().toString();
                                    String timeh = time.getText().toString();
                                    publishdate = dateh.concat("T").concat(timeh).concat("Z");
                                    News.setByLine(author.getText().toString());
                                    News.setWebPublicationDate(publishdate);
                                    News.setWebSectionName(section.getText().toString());
                                    News.setWebTitle(title.getText().toString());
                                    News.setWebTrailText(trailtext.getText().toString());
                                    News.setWebUrl(weburl.getText().toString());
                                    News.setThumbnail(downloadUrl);
                                    String s = Long.toString(i);
                                    databaseReference.child(s).setValue(News);
                                    i++;
                                    Log.e("Thumbnail:---",News.getThumbnail());

                                }
                            });
                            Toast.makeText(MainActivity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }

    }
}

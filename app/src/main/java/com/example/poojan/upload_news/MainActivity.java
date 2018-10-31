package com.example.poojan.upload_news;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final int RC_PHOTO_PICKER = 2;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    news News;
    String publishdate;
    long i;
    EditText section,date,title,trailtext,weburl,author,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mPhotoPickerButton=(Button)findViewById(R.id.photovalue);
         section=(EditText)findViewById(R.id.sectionvalue);
         date=(EditText)findViewById(R.id.datevalue);
         title=(EditText)findViewById(R.id.titlevalue);
         trailtext=(EditText)findViewById(R.id.trailtextvalue);
         weburl=(EditText)findViewById(R.id.weburlvalue);
         author=(EditText)findViewById(R.id.authorvalue);
         time=(EditText)findViewById(R.id.timevalue);
        Button submit=(Button)findViewById(R.id.upload);
        News=new news();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("response");
        initial();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // increase();
                initial();
                Log.e("Child after Intial:----",Long.toString(i));
                i++;
                Log.e("Child after i++ Intial:",Long.toString(i));
                getvalues();
                String s=Long.toString(i);
                databaseReference.child(s).setValue(News);
                Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();


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
    private void getvalues()
    {
        String dateh=date.getText().toString();
        String timeh=time.getText().toString();
        publishdate=dateh.concat("T").concat(timeh).concat("Z");
        News.setByLine(author.getText().toString());
        News.setWebPublicationDate(publishdate);
        News.setWebSectionName(section.getText().toString());
        News.setWebTitle(title.getText().toString());
        News.setWebTrailText(trailtext.getText().toString());
        News.setWebUrl(weburl.getText().toString());

        Log.e("PublishDate::------",publishdate);

    }
//    private void increase()
//    {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                i=dataSnapshot.getChildrenCount();
//                Log.e("Child:----",Long.toString(i));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
    private void initial()
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i=dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

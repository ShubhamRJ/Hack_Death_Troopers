package com.example.yashodeepmahapatra.ambulancerouting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mUserDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        final String email = intent.getStringExtra("send_email");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserDatabaseReference = mFirebaseDatabase.getReference().child("user_data");
        e1 = (EditText)findViewById(R.id.name);
        e2 = (EditText)findViewById(R.id.phone);
        e3 = (EditText)findViewById(R.id.age);
        b1 = (Button)findViewById(R.id.createacc);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisteredUser ruser = new RegisteredUser(email,e1.getText().toString(),e2.getText().toString(),Integer.parseInt(e3.getText().toString()),Integer.parseInt("1"));
                mUserDatabaseReference.push().setValue(ruser);
                Log.v("DB Addition stat: ","Added user to database");
                Toast.makeText(getApplicationContext(),"Registered successfully! Please login now",Toast.LENGTH_SHORT);
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

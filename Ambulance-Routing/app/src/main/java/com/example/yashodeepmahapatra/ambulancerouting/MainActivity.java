package com.example.yashodeepmahapatra.ambulancerouting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    EditText t1,t2;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        t1 = (EditText)findViewById(R.id.editText2);
        t2 = (EditText)findViewById(R.id.editText3);
        b1 = (Button)findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = t1.getText().toString().trim();
                String password = t2.getText().toString().trim();
                if(email.isEmpty()){
                    t1.setError("Email is required");
                    t1.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    t1.setError("Email is required");
                    t1.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    t2.setError("Password is required");
                    t2.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.v("Sign in stat: ", "Sign in successful");
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, login.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Log.v("Sign in stat: ", "Wrong Password!");
                            Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                            t1.setText("");
                            t2.setText("");
                        }
                    }
                });
            }
        });

    }
    private void registerUser(){
        String email = t1.getText().toString().trim();
        String password = t2.getText().toString().trim();
        if(email.isEmpty()){
            t1.setError("Email is required");
            t1.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            t1.setError("Email is required");
            t1.requestFocus();
            return;
        }
        if(password.isEmpty()){
            t2.setError("Password is required");
            t2.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.v("Register stat: ","Redirected to register.java");
                    Toast.makeText(getApplicationContext(), "Please add a few more details",Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(MainActivity.this,Register.class);
                    j.putExtra("send_email",t1.getText().toString().trim());
                    startActivity(j);
                    finish();
                }
                else{
                    Log.v("Register stat: ","Regsitration Failed! Try Again");
                    Toast.makeText(getApplicationContext(), "Regsitration Failed! Try Again",Toast.LENGTH_SHORT).show();
                    t1.setText("");
                    t2.setText("");
                }
            }
        });
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.button2:
                registerUser();
                break;
        }
    }
}
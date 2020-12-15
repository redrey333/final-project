package com.example.finalfirstattempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText emailEt, passwordEt;
    private Button signIn;
    private TextView createNewTv;
    private ProgressDialog progDia;
    private FirebaseAuth fireBaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireBaseAuth= FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        signIn = findViewById(R.id.login);
        progDia = new ProgressDialog(this);
        createNewTv = findViewById(R.id.createNewAcc);
        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });
        createNewTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void login(){
        String email = emailEt.getText().toString();
        String pw = passwordEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter Password");
            return;
        } else if (TextUtils.isEmpty(pw)) {
            passwordEt.setError("Enter Password");
            return;
        }
        progDia.setMessage("Checking...");
        progDia.show();
        progDia.setCanceledOnTouchOutside(false);
        fireBaseAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Complete", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, dashActivity.class);
                    intent.setPackage("com.android.vending");
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
                progDia.dismiss();
            }
        });
    }
}
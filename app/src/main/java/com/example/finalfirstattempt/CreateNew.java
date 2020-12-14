package com.example.finalfirstattempt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreateNew extends AppCompatActivity {
    private EditText emailEt, passwordEt1, passwordEt2;
    private Button createNewButton;
    private TextView loginTv;
    private ProgressDialog progDia;
    private FirebaseAuth fireBaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnew);
        fireBaseAuth= FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.email);
        passwordEt1 = findViewById(R.id.password);
        passwordEt2 = findViewById(R.id.password2);
        createNewButton = findViewById(R.id.register);
        progDia = new ProgressDialog(this);
        loginTv = findViewById(R.id.signIn);
        createNewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                register();
            }
        });
        loginTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CreateNew.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void register() {
        String email = emailEt.getText().toString();
        String pw1 = passwordEt1.getText().toString();
        String pw2 = passwordEt2.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter Password");
            return;
        } else if (TextUtils.isEmpty(pw1)) {
            passwordEt1.setError("Enter Password");
            return;
        } else if (TextUtils.isEmpty(pw2)) {
            passwordEt2.setError("Confirm Password");
            return;
        }
        else if(!pw1.equals(pw2)){
            passwordEt2.setError("Password Not Identical");
            return;
        }
        else if(pw1.length()<5){
            passwordEt1.setError("Password Length Must Exceed 5 Digits");
            return;
        }
        else if(TextUtils.isEmpty(pw1)){
            passwordEt1.setError("Enter Password");
            return;
        }
        else if(!isValidEmail(email)){
            emailEt.setError("Invalid Email");
            return;
        }
        progDia.setMessage("Checking...");
        progDia.show();
        progDia.setCanceledOnTouchOutside(false);
        fireBaseAuth.createUserWithEmailAndPassword(email,pw1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    Toast.makeText(CreateNew.this, "Register Complete", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateNew.this, dashActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(CreateNew.this, "Register Failed", Toast.LENGTH_LONG).show();
                }
                progDia.dismiss();
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}

package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt1, edt2;
    private Button btn1;
    private TextView tv1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();

        edt1 = findViewById(R.id.edt_gmailId);
        edt2 = findViewById(R.id.edt_passwordId);

        btn1 = findViewById(R.id.btn_logIn);
        tv1 = findViewById(R.id.tv_call_signUpId);


        btn1.setOnClickListener(this);
        tv1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logIn:
                userLogin();
                break;
            case R.id.tv_call_signUpId:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                break;

        }

    }
    private void userLogin(){

        String email = edt1.getText().toString().trim();
        String password = edt2.getText().toString().trim();

        //checking the validity of the email
        if (email.isEmpty()){
            edt1.setError("Please enter a email address!");
            edt1.requestFocus();
            return;
        }if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt1.setError("Please enter valid email address!");
            edt1.requestFocus();
            return;
        }

        //checking the validity of the password

        if (password.isEmpty()){
            edt2.setError("Please enter password!");
            edt2.requestFocus();
            return;
        }if (password.length()<6){
            edt2.setError("Minimum length 6 password");
            edt2.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

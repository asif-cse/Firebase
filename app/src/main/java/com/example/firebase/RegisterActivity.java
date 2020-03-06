package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt1, edt2, edt3, edt4;
    private TextView tv1;
    private Button btn1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        edt1 = findViewById(R.id.edt_full_name);
        edt2 = findViewById(R.id.edt_user_name);
        edt3 = findViewById(R.id.edt_password);
        edt4 = findViewById(R.id.edt_password_confirm);
        tv1 = findViewById(R.id.tv_call_LogInId);
        btn1 = findViewById(R.id.btn_signUpId);

        tv1.setOnClickListener(this);
        btn1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signUpId:
                userSignUp();
                break;
            case R.id.tv_call_LogInId:
                startActivity(new Intent(getApplicationContext(),LogInActivity.class));
                break;
        }
    }
    private void userSignUp(){
        String userName = edt1.getText().toString().trim();
        String email = edt2.getText().toString().trim();
        String password = edt3.getText().toString().trim();
        String confirmPassword = edt4.getText().toString().trim();

        //checking the validity of the email
        if(userName.isEmpty())
        {
            edt1.setError("Enter Your name");
            edt1.requestFocus();
            return;
        }

        //checking the validity of the email
        if(email.isEmpty())
        {
            edt2.setError("Enter an email address");
            edt2.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edt2.setError("Enter a valid email address");
            edt2.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            edt3.setError("Enter a password");
            edt3.requestFocus();
            return;
        }if(password.length()<6)
        {
            edt3.setError("Minimum length 6 pasword");
            edt3.requestFocus();
            return;
        }
        //checking two password:
        if (password.equals(confirmPassword)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"Register is success",Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        if(task.getException() instanceof FirebaseAuthActionCodeException){
                            Toast.makeText(getApplicationContext(),"Register is already Register",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }else
            edt4.setError("Please enter same password");
            edt4.requestFocus();
    }
}

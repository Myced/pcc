package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{




    // UI references.
    protected EditText mEmailView;
    protected EditText mPasswordView;
    private FirebaseAuth mAuth;
    protected String mEmail, mPassword;
    ProgressDialog progressDialog;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView =  findViewById(R.id.emailSignIn);
        mPasswordView = findViewById(R.id.passwordSignIn);
        signIn = findViewById(R.id.email_sign_in_button);
        signIn.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }



    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    public void signInUserWithEmail() {

        mEmail = mEmailView.getText().toString().trim();
        mPassword = mPasswordView.getText().toString().trim();
        Log.d("Login Activity:", mEmail +" : "+ mPassword);
        progressDialog.setMessage("Login in please wait...");
        progressDialog.show();
        Log.d("Login Activity:", "showing progress dialog");

        if(isEmailValid(mEmail)){
            if(isPasswordValid(mPassword)){
                mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("Login Activity", "Login completed");
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Login Activity", "Login completed");
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
            else {
                Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show();
            }
        }
        else{

            Toast.makeText(this, "Email is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void createAccount(View view) {
        finish();
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.email_sign_in_button:
                signInUserWithEmail();
                break;

        }
    }
}


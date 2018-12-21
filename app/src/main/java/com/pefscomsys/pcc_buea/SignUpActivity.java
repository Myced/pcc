package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    protected EditText yourName, yourNumber, yourEmail, yourPassword;
    protected String mName, mNumber, mEmail, mPassword, mUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Users newUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        yourName = findViewById(R.id.username);
        yourNumber = findViewById(R.id.phonenumber);
        yourEmail = findViewById(R.id.email);
        yourPassword = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void signUpWithEmail(View view) {
        mName = yourName.getText().toString().trim();
        mNumber = yourNumber.getText().toString().trim();
        mEmail = yourEmail.getText().toString().trim();
        mPassword = yourPassword.getText().toString().trim();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(mNumber, mPassword) //pass in phone number instead of email
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Sign Up activity", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                mUserId = user.getUid();
                               newUser = new Users(mName, mUserId, mEmail, mNumber, mPassword);
                            }
                            Map<String, Object> userData = newUser.toMap();

                            Log.d("Sign up activity", userData.toString());

                            mDatabase.child("Users").child(mUserId).setValue(userData);
                            finish();
                            Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            Intent newIntent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(newIntent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Sign up activity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}

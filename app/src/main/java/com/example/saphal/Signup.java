package com.example.saphal;
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

public class Signup extends AppCompatActivity {
    EditText email, password, confirmPassword;
    Button signup;
    TextView signin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        confirmPassword = findViewById(R.id.txt_cpassword);
        signup = findViewById(R.id.btn_signup);
        signin = findViewById(R.id.txt_signin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Cpassword = confirmPassword.getText().toString().trim();

                if (Email.isEmpty()) {
                    Toast.makeText(Signup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.isEmpty()) {
                    Toast.makeText(Signup.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.length() <= 3) {
                    Toast.makeText(Signup.this, "Password must be at least 4 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Cpassword.equals(Password)) {
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Signup.this, "Signup successful. Please verify your email address.", Toast.LENGTH_SHORT).show();

                                                    } else {
                                                        Toast.makeText(Signup.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(Signup.this, "Signup Failed: " + errorMessage, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinintent = new Intent(Signup.this, loginPage.class);
                startActivity(signinintent);
            }
        });
    }
}
package com.example.saphal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {

    private EditText email;
    private MaterialButton reset, back;
    private ProgressBar progressbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.forgotPasswordEmail);
        reset = findViewById(R.id.btnReset);
        back = findViewById(R.id.btnBack);
        progressbar = findViewById(R.id.forgetPasswordProgressbar);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString().trim();

                if (emailText.isEmpty()) {
                    Toast.makeText(Forget_Password.this, "Please Enter the Email", Toast.LENGTH_SHORT).show();
                } else {
                    progressbar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(emailText).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), loginPage.class);
                                startActivity(intent);
                                Toast.makeText(Forget_Password.this, "Check Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Forget_Password.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Forget_Password.this, loginPage.class);
                startActivity(backIntent);
            }
        });
    }
}
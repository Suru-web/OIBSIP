package com.suraj.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.suraj.todo.databinding.ActivitySignInBinding;

public class sign_in extends AppCompatActivity {
    ActivitySignInBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        auth = FirebaseAuth.getInstance();
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_in.this,sign_up.class));
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = String.valueOf(binding.emailINP.getText());
                password = String.valueOf(binding.passwordINP.getText());
                if (TextUtils.isEmpty(email)) {
                    binding.emailINP.setError("Email cannot be empty");
                }
                if (TextUtils.isEmpty(password)) {
                    binding.passwordINP.setError("Password cannot be empty");
                }
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(sign_in.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Log.d("Login Exception",task.getException().toString());
                            if (task.getException().toString().contains("The supplied")){
                                binding.passwordINP.setError("Wrong password is entered");
                            } else if (task.getException().toString().contains("The email")) {
                                binding.emailINP.setError("Email Does not exist");
                            }
                        }
                    }
                });
            }
        });
    }
}
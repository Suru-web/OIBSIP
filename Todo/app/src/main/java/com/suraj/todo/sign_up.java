package com.suraj.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.suraj.todo.databinding.ActivitySignInBinding;
import com.suraj.todo.databinding.ActivitySignUpBinding;

public class sign_up extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        auth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_up.this,sign_in.class));
                finish();
            }
        });
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, confirmPassword;
                email = String.valueOf(binding.emailINP.getText());
                password = String.valueOf(binding.passwordINP.getText());
                confirmPassword = String.valueOf(binding.confirmPasswordINP.getText());
                if (TextUtils.isEmpty(email)) {
                    binding.emailINP.setError("Email cannot be empty");
                }
                if (TextUtils.isEmpty(password)) {
                    binding.passwordINP.setError("Password cannot be empty");
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    binding.confirmPasswordINP.setError("Password cannot be empty");
                }
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(sign_up.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(sign_up.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(sign_up.this, "Account cannot be created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
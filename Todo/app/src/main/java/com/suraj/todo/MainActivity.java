package com.suraj.todo;

import static java.security.AccessController.getContext;

import com.suraj.todo.Adapters.main_adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;

import com.suraj.todo.objects.main_list_object;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.suraj.todo.databinding.ActivityMainBinding;
import com.suraj.todo.databinding.ActivitySignUpBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    ArrayList<main_list_object> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setSystemBarColors();


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        main_adapter adapter = new main_adapter(binding.getRoot().getContext(), list);
        binding.notesListRecycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(binding.getRoot().getContext(), 2);
        binding.notesListRecycler.setLayoutManager(gridLayoutManager);
        setNameUser();
        setFabColor();
        binding.addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, create_tasks.class));
            }
        });


    }

    protected void setFabColor() {
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.female2)).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Get the dominant color
                int dominantColor = palette.getDominantColor(getResources().getColor(R.color.dark_sky_blue));
                binding.addNewTask.setBackgroundTintList(android.content.res.ColorStateList.valueOf(dominantColor));
                boolean isLight = ColorUtils.calculateLuminance(dominantColor) > 0.3;

                // Set the text color based on the background color
                int textColor = isLight ? getResources().getColor(R.color.dark) : getResources().getColor(R.color.white);
                binding.addNewTask.setTextColor(textColor);
                binding.addNewTask.setIconTint(android.content.res.ColorStateList.valueOf(textColor));
            }
        });
    }

    protected void setNameUser() {
        String name = Objects.requireNonNull(user).getDisplayName();
        if (name != null && name.isEmpty()) {
            name = "User";
        }
        String trimmedName = "";
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) != ' ') {
                    trimmedName = trimmedName.concat(String.valueOf(name.charAt(i)));
                } else {
                    break;
                }
            }
        }
        binding.welcomeName.setText("Hello there,\n" + trimmedName);
    }

    private void setSystemBarColors() {
        int nightModeFlags = binding.getRoot().getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            Window window = this.getWindow();
            int flags = window.getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(flags);
        }
    }
}
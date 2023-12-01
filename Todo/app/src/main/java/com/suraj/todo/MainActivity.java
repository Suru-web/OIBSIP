package com.suraj.todo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suraj.todo.Adapters.main_adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;

import com.suraj.todo.objects.main_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firestore;
    ArrayList<main_list> combinedList = new ArrayList<>();
    ArrayList<main_list> allList = new ArrayList<>();
    ArrayList<main_list> workList = new ArrayList<>();
    ArrayList<main_list> shoppingList = new ArrayList<>();
    ArrayList<main_list> tripList = new ArrayList<>();
    ArrayList<main_list> studyList = new ArrayList<>();
    ArrayList<main_list> homeList = new ArrayList<>();
    ArrayList<main_list> musicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setSystemBarColors();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        setNameUser();
        setFabColor();

        main_adapter adapter = new main_adapter(binding.getRoot().getContext(),combinedList);
        binding.notesListRecycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(binding.getRoot().getContext(), 2);
        binding.notesListRecycler.setLayoutManager(gridLayoutManager);
        binding.addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, create_tasks.class));
            }
        });
        getValuePutToAdapter(adapter);
    }

    private void getValuePutToAdapter(main_adapter adapter) {
        firestore.collection("users").document(auth.getUid()).collection("All")
                                .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                                        main_list mainList = document.toObject(main_list.class);
                                                        if (allList.isEmpty()) {
                                                            allList.add(mainList);
                                                        }
                                                        else {
                                                            boolean categoryExists = false;
                                                            for (main_list listItem : allList) {
                                                                if (listItem.getCategory().equals(mainList.getCategory())) {
                                                                    // Increment the task count for the existing category
                                                                    listItem.setTaskCount(listItem.getTaskCount() + 1);
                                                                    categoryExists = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (!categoryExists) {
                                                                // If the category doesn't exist, add a new entry to the list
                                                                allList.add(mainList);
                                                            }
                                                        }
                                                    }
                                                    adapter.notifyDataSetChanged();
                                                    combinedList.addAll(allList);
                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });
        firestore.collection("users").document(auth.getUid()).collection("Work")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                main_list mainList = document.toObject(main_list.class);
                                workList.add(mainList);
                                Log.d("----All List----",String.valueOf(allList));
                            }
                            adapter.notifyDataSetChanged();
                            combinedList.addAll(workList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    protected void setFabColor() {
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.male1)).getBitmap();
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
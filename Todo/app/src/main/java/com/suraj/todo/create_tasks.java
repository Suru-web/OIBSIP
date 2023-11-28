package com.suraj.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.suraj.todo.databinding.ActivityCreateTasksBinding;

public class create_tasks extends AppCompatActivity {
    private ActivityCreateTasksBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkUiMode();


        binding.enterTask.requestFocus();
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void checkUiMode(){
        int nightModeFlags =
                binding.getRoot().getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                setFlagColors(R.color.dark,true);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                setFlagColors(R.color.white,false);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                setFlagColors(R.color.dark,true);
                break;
        }
    }
    protected void setFlagColors(int color, boolean blackMode){
        //This code sets the status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(color));
        window.setNavigationBarColor(this.getResources().getColor(R.color.dark_sky_blue));

        //This part of the code displays the statusbar icon color to black
        if (!blackMode){
            int flags = window.getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(flags);
        }
    }

}
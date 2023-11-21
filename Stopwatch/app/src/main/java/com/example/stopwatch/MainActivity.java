package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.stopwatch.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private int ms = 0, sec = 0, min = 0;
    private boolean pausePressed = false;
    private Runnable msRunnable;
    private long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Window window = this.getWindow();
        if (!isNightMode(this)) {
            int flags = window.getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(flags);
        }

        binding.progressBar.setMax(60);
        final Handler handlerMs = new Handler();
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startTime == 0) {
                    startTime = System.currentTimeMillis();
                }
                msRunnable = new Runnable() {
                    @Override
                    public void run() {
                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = currentTime - startTime;
                        min = (int) (elapsedTime / (60 * 1000));
                        sec = (int) ((elapsedTime / 1000) % 60);
                        ms = (int) (elapsedTime % 1000);
                        binding.progressText1.setText(String.format("%02d", min));
                        binding.progressText2.setText(String.format("%02d", sec));
                        binding.progressText3.setText(String.format("%03d", ms));
                        binding.progressBar.setProgress(sec, true);
                        handlerMs.postDelayed(this, 1);
                    }
                };
                handlerMs.post(msRunnable);
                binding.pauseButton.setVisibility(View.VISIBLE);
            }
        });

        binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ms = 0;
                sec = 0;
                min = 0;
                binding.progressText3.setText("00");
                binding.progressText2.setText("00");
                binding.progressText1.setText("00");
                handlerMs.removeCallbacks(msRunnable);
                binding.pauseButton.setVisibility(View.GONE);
                binding.progressBar.setProgress(0);
                startTime = 0; // Reset startTime when stopping the timer
            }
        });

        binding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausePressed = !pausePressed;
                if (pausePressed) {
                    binding.pauseButton.setText("Resume");
                    handlerMs.removeCallbacks(msRunnable);
                    binding.stopButton.setText("Reset");
                } else {
                    binding.pauseButton.setText("Pause");
                    startTime = System.currentTimeMillis() - (min * 60 * 1000 + sec * 1000 + ms);
                    handlerMs.post(msRunnable);
                    binding.stopButton.setText("Stop");
                }
            }
        });
    }

    public boolean isNightMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}

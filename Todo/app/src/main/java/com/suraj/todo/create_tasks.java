package com.suraj.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.PopupWindow;

import com.suraj.todo.databinding.ActivityCreateTasksBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class create_tasks extends AppCompatActivity {
    private ActivityCreateTasksBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkUiMode();
        setDateAndTime();
        checkTextChange();
        binding.enterTask.requestFocus();
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.datePickerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        create_tasks.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.datePickerTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();


            }
        });
        binding.addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addNoteCardView.setVisibility(View.VISIBLE);
                binding.extraNote.requestFocus();
            }
        });
        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) create_tasks.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                binding.textInputLayoutEnterTask.clearFocus();
                binding.addCategoryCardView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setDateAndTime() {
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        binding.datePickerTV.setText(date);
    }

    private void checkTextChange() {
        binding.enterTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 25) {
                    binding.textInputLayoutEnterTask.setHelperText("Its better to keep it short and sweet..");
                } else if (s.length() < 25) {
                    binding.textInputLayoutEnterTask.setHelperTextEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected void checkUiMode() {
        int nightModeFlags =
                binding.getRoot().getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                setFlagColors(R.color.dark, true);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                setFlagColors(R.color.white, false);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                setFlagColors(R.color.dark, true);
                break;
        }
    }

    protected void setFlagColors(int color, boolean blackMode) {
        //This code sets the status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(color));
        window.setNavigationBarColor(this.getResources().getColor(R.color.dark_sky_blue));

        //This part of the code displays the statusbar icon color to black
        if (!blackMode) {
            int flags = window.getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(flags);
        }
    }

}
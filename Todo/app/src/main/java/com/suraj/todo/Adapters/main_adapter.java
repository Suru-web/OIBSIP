package com.suraj.todo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suraj.todo.R;
import com.suraj.todo.objects.main_list_object;

import java.util.ArrayList;

public class main_adapter extends RecyclerView.Adapter<main_adapter.MyViewHolder> {
    Context context;
    static ArrayList<main_list_object> list;
    public main_adapter(Context context, ArrayList<main_list_object> list){
        this.context = context;
        main_adapter.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull main_adapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private  MyViewHolder(View itemview){
            super(itemview);
        }
    }
}

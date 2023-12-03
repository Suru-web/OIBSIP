package com.suraj.todo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suraj.todo.R;
import com.suraj.todo.objects.item_list;

import java.util.ArrayList;

public class sub_list_adapter extends RecyclerView.Adapter<sub_list_adapter.viewHolder> {
    Context context;
    static ArrayList<item_list> list;
    public sub_list_adapter(Context context,ArrayList<item_list> list){
        this.context = context;
        sub_list_adapter.list = list;
    }
    @NonNull
    @Override
    public sub_list_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_list_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sub_list_adapter.viewHolder holder, int position) {
        item_list itemList = list.get(position);
        holder.task.setText(String.valueOf(itemList.getTask()));
        holder.date.setText(String.valueOf(itemList.getDate()));
        holder.month.setText(String.valueOf(itemList.getMonth()));
        holder.year.setText(String.valueOf(itemList.getYear()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView task,date,month,year;
        public viewHolder(View itemView){
            super(itemView);
            task = itemView.findViewById(R.id.taskTitle);
            date = itemView.findViewById(R.id.taskDate);
            month = itemView.findViewById(R.id.taskMonth);
            year = itemView.findViewById(R.id.taskYear);
        }
    }
}

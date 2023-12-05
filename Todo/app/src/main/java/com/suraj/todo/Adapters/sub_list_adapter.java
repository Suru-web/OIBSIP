package com.suraj.todo.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.suraj.todo.R;
import com.suraj.todo.objects.item_list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        String date = itemList.getDate()+"/"+itemList.getMonth()+"/"+itemList.getYear();
        holder.date.setText(date);
        if (itemList.isCompleted()){
            holder.cardView.setAlpha(0.7F);
            holder.task.setTextAppearance(R.style.CHECKBOX_TEXT);
            holder.date.setTextAppearance(R.style.COMPLETED_TASK);
            holder.status.setText(R.string.completed);
            holder.status.setTextAppearance(R.style.COMPLETED_TASK);
            holder.status.setVisibility(View.VISIBLE);
            holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.checkBox.setChecked(true);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection(itemList.getCateg()).document(itemList.getDocID()).update("completed",true);
                    itemList.setCompleted(true);
                    holder.cardView.setAlpha(0.7F);
                    holder.task.setTextAppearance(R.style.CHECKBOX_TEXT);
                    holder.date.setTextAppearance(R.style.COMPLETED_TASK);
                    holder.status.setText(R.string.completed);
                    holder.status.setTextAppearance(R.style.COMPLETED_TASK);
                    holder.status.setVisibility(View.VISIBLE);
                    holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection(itemList.getCateg()).document(itemList.getDocID()).update("completed",false);
                    itemList.setCompleted(false);
                    holder.cardView.setAlpha(1F);
                    holder.task.setTextAppearance(R.style.TEXT_COLOR);
                    holder.date.setTextAppearance(R.style.TEXT_COLOR);
                    holder.status.setVisibility(View.GONE);
                    holder.task.setPaintFlags(holder.task.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView task,date,status;
        CardView cardView;
        CheckBox checkBox;
        public viewHolder(View itemView){
            super(itemView);
            task = itemView.findViewById(R.id.taskTitle);
            date = itemView.findViewById(R.id.taskDate);
            status = itemView.findViewById(R.id.taskDoneOrDelayed);
            checkBox = itemView.findViewById(R.id.taskCompleteCheckBox);
            cardView = itemView.findViewById(R.id.cardViewList);
        }
    }
}

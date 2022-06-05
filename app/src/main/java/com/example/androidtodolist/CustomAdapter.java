package com.example.androidtodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtodolist.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Task> mData;
    private ItemClickListener mClickListener;

    CustomAdapter(ArrayList<Task> data,ItemClickListener mClickListener) {
        this.mData = data;
        this.mClickListener = mClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TaskViewHolder holder = (TaskViewHolder) viewHolder;
        holder.title.setText(mData.get(position).getTitle());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView taskView;
        String  id;
        TextView title;
        TextView description;
        TextView category; // name of category
        TextView created;
        TextView doneDate;
        TextView toDoDate;
        CheckBox done;
        Button deleteButton;
        Button editButton;
        Boolean notificationStatus;
        Boolean hidden;
        ToggleButton notificationButton;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskView = itemView.findViewById(R.id.cv);
            title = itemView.findViewById(R.id.task_title);
            category = itemView.findViewById(R.id.category);
            description = itemView.findViewById(R.id.content);
            done = itemView.findViewById(R.id.check);
            created = itemView.findViewById(R.id.date2);
            doneDate = itemView.findViewById(R.id.done2);
            toDoDate = itemView.findViewById(R.id.deadline2);
            deleteButton = itemView.findViewById(R.id.editButton);
            editButton = itemView.findViewById(R.id.deleteButton);
            notificationButton = itemView.findViewById(R.id.notificationButton);
            taskView.setOnClickListener(this);
            title.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
            done.setOnClickListener(this);

            MainActivity mainactivity = (MainActivity) itemView.getContext();

        }




        @Override
        public void onClick(View view) {
        }
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.task_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

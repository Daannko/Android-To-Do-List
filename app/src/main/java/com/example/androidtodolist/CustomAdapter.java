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

import java.nio.file.Watchable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static ArrayList<Task> mData;
    private static ItemClickListener mClickListener;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    CustomAdapter(ArrayList<Task> data,ItemClickListener mClickListener) {
        mData = data;
        CustomAdapter.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new TaskViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


        TaskViewHolder holder = (TaskViewHolder) viewHolder;
        holder.title.setText(mData.get(position).getTitle());
        holder.category.setText(mData.get(position).getCategory());
        holder.description.setText(mData.get(position).getDescription());
        if(mData.get(position).getDone())
        {
            holder.done.setChecked(true);
        }
        if(mData.get(position).getDoneDate() == null)
        {
            holder.doneDate.setText("");
        }
        else
        {
            holder.doneDate.setText(mData.get(position).getDoneDate());
        }
        holder.toDoDate.setText(mData.get(position).getToDoDate());
        holder.created.setText(mData.get(position).getCreated());
        if(holder.done.isChecked())
        {
            holder.doneDate.setText(mData.get(position).getDoneDate());
        }
        else
        {
            holder.doneDate.setText("");
        }

        if (mData.get(position).getNotificationStatus())
        {
            holder.notificationButton.setText("ON");
            holder.notificationButton.setBackgroundResource(R.drawable.onoffbutton);
            holder.notificationButton.setChecked(true);
        }
        else
        {
            holder.notificationButton.setText("OFF");
            holder.notificationButton.setBackgroundResource(R.drawable.onoffbutton);
            holder.notificationButton.setChecked(false);

        }

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
        TextView category;
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

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(done.isChecked())
                    {
                        mData.get(getAdapterPosition()).setDone(true);
                        mData.get(getAdapterPosition()).setDoneDate(new Date());
                        doneDate.setText(mData.get(getAdapterPosition()).getDoneDate());
                    }
                    else
                    {
                        mData.get(getAdapterPosition()).setDone(false);
                        mData.get(getAdapterPosition()).setDoneDate(null);
                        doneDate.setText("");
                    }
                    mainactivity.database.editTask(mData.get(getAdapterPosition()));
                }
            });


        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view,getAdapterPosition());
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

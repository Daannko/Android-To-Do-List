package com.example.androidtodolist;

import android.app.Dialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtodolist.databinding.ActivityMainBinding;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ItemClickListener {

    private ActivityMainBinding binding;
    CustomAdapter adapter;
    ArrayList<Task> tasks;
    CustomAdapter.ItemClickListener itemClickListener;
    Database database;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // BAZA DANYCH
        database = new Database(this);
        ////


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddDialog();
            }
        });

        loadTasksFromDatabase();

        setUpAdapter();

    }

    public void loadTasksFromDatabase()
    {
        try
        {
            tasks = database.getTasks();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        if (tasks.isEmpty()) {

            Toast.makeText(binding.getRoot().getContext(), "No tasks!", Toast.LENGTH_SHORT).show();
        } /*else {
            isEmptyTextView.setText("");
        }*/
        //Collections.sort(listOfTasks, new CustomComparator());

        setUpAdapter();
    }

    public void setUpAdapter()
    {
        RecyclerView rv = findViewById(R.id.MyRecycleViewer);
        rv.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(lm);

        adapter = new CustomAdapter(tasks, MainActivity.this);

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view2 = view.findViewById(R.id.expand_view);
                if(view2.getVisibility() != View.VISIBLE)
                {
                    view2.setVisibility(View.VISIBLE);
                }
                else
                {
                    view2.setVisibility(View.GONE);
                }
            }
        });

        rv.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createEditDialog(Task oldTask)
    {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_list_item_edit);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        EditText title = dialog.findViewById(R.id.titleTextInput);
        EditText  description = dialog.findViewById(R.id.descriptionTextInput);
        Spinner spinner = dialog.findViewById(R.id.categorySpinner);
        DatePicker datePicker =  dialog.findViewById(R.id.calendarView);
        Button button = dialog.findViewById(R.id.dialogSaveButton);
        TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        title.setText(oldTask.getTitle());
        description.setText(oldTask.getDescription());

        String oldDate = oldTask.getToDoDate();
        String[] oldDateInfo = oldDate.split(" :");
        datePicker.updateDate(Integer.parseInt(oldDateInfo[2]),Integer.parseInt(oldDateInfo[1])-1,Integer.parseInt(oldDateInfo[0]));
        timePicker.setHour(Integer.parseInt(oldDateInfo[3]));
        timePicker.setMinute(Integer.parseInt(oldDateInfo[4]));

        TypedArray typedArray = getResources().obtainTypedArray(R.array.categories);

        for(int i = 0 ; i < typedArray.getIndexCount() ; i++) {
            if(oldTask.getCategory().equals(typedArray.getString(i)))
            {
                spinner.setSelection(i);
                break;
            }
        }


        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {


                if(title != null)
                {

                    try {
                        Task task = new Task();
                        task.setTitle(title.getText().toString());
                        task.setDescription(description.getText().toString());
                        task.setCreated(new Date());
                        task.setDone(false);
                        task.setNotificationStatus(false);
                        task.setHidden(false);
                        task.setId(oldTask.getId());
                        task.setCategory(spinner.getSelectedItem().toString());
                        String toDoDate = datePicker.getDayOfMonth()
                                +"-"+(datePicker.getMonth()+1)
                                +"-"+datePicker.getYear()
                                +" "+timePicker.getHour()
                                +":"+timePicker.getMinute();
                        task.setToDoDate(sdf.parse(toDoDate));

                        database.upda(task);

                        loadTasksFromDatabase();

                        dialog.dismiss();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void createAddDialog()
    {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_list_item_edit);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        EditText title = dialog.findViewById(R.id.titleTextInput);
        EditText  description = dialog.findViewById(R.id.descriptionTextInput);
        Spinner spinner = dialog.findViewById(R.id.categorySpinner);
        DatePicker datePicker =  dialog.findViewById(R.id.calendarView);
        Button button = dialog.findViewById(R.id.dialogSaveButton);
        TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);


        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {


                if(title != null)
                {

                    try {
                        Task task = new Task();
                        task.setTitle(title.getText().toString());
                        task.setDescription(description.getText().toString());
                        task.setCreated(new Date());
                        task.setDone(false);
                        task.setNotificationStatus(false);
                        task.setHidden(false);
                        task.setCategory(spinner.getSelectedItem().toString());
                        String toDoDate = datePicker.getDayOfMonth()
                                +"-"+(datePicker.getMonth()+1)
                                +"-"+datePicker.getYear()
                                +" "+timePicker.getHour()
                                +":"+timePicker.getMinute();
                        task.setToDoDate(sdf.parse(toDoDate));

                        database.addTask(task);

                        loadTasksFromDatabase();

                        dialog.dismiss();


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onItemClick(View v, int position) {

        switch (v.getId())
        {
            case R.id.deleteButton:
                database.deleteTask(tasks.get(position));
                tasks.remove(position);
                adapter.notifyItemRemoved(position);
                if(tasks.isEmpty())
                {
                    System.out.println("NIE MA TASKÓW WYSWIETL TO GDZIEś :)");
                }
                break;

            case 2131230852:
                LinearLayout linearLayout = v.findViewById(R.id.expand_view);
                TextView textView = v.findViewById(R.id.notExpandedTaskDateInput);
                if(linearLayout.getVisibility() != View.VISIBLE)
                {
                    textView.setTextColor(getResources().getColor(R.color.white));
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    textView.setTextColor(getResources().getColor(R.color.greyish));
                    linearLayout.setVisibility(View.GONE);
                }
            case R.id.editButton:
                createEditDialog(tasks.get(position));
                break;
        }
    }




}
package com.example.androidtodolist;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtodolist.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

    Task task = new Task();
        task.setDescription("hehehehehehehhehehEHehehehHEHEHEHEheheheh");
        task.setCategory("Home");
        task.setTitle("UwU xdd");
        task.setCreated(new Date());
        task.setDone(false);
        task.setNotificationStatus(true);
        task.setId("0");
        Date tommorow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
        task.setToDoDate(tommorow);

       // database.addTask(task);

        loadTasksFromDatabase();

        setUpAdapter();

        setSupportActionBar(binding.toolbar);
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
        if (tasks.size() == 0) {

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
        rv.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(binding.getRoot().getContext(),tasks.get(position).getTitle(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
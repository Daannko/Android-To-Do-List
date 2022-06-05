package com.example.androidtodolist;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtodolist.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ItemClickListener {

    private ActivityMainBinding binding;
    CustomAdapter adapter;
    ArrayList<Task> tasks;
    CustomAdapter.ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.get(0).setTitle("TEST");


        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.MyRecycleViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(tasks,itemClickListener);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);




        setSupportActionBar(binding.toolbar);
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
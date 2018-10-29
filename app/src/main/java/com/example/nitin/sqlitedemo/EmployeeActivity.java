package com.example.nitin.sqlitedemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    List<DataModel> dataModels;
    DBHandler openHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataModels = new ArrayList<DataModel>();
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        openHelper = new DBHandler(EmployeeActivity.this);
        dataModels = openHelper.getdata();
        recycleAdapter = new RecycleAdapter(EmployeeActivity.this, dataModels);

        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ID = item.getItemId();
        if (ID == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

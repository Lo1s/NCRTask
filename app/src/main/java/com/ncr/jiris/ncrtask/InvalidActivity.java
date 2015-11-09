package com.ncr.jiris.ncrtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class InvalidActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> mInvalidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_invalid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mInvalidData = getIntent().getStringArrayListExtra(MyConstants.INVALID_ARRAYLIST);
        adapter = new InvalidListAdapter(mInvalidData);
        recyclerView.setAdapter(adapter);
    }

}
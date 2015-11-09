package com.ncr.jiris.ncrtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FrequentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> mFrequentData;
    private TextView textViewMostFrequent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequent);

        Intent passedIntent = getIntent();

        textViewMostFrequent = (TextView) findViewById(R.id.textView_mostFrequent);
        textViewMostFrequent.setText("Most Frequent Set: "
                + passedIntent.getIntegerArrayListExtra(MyConstants.MOST_FREQUENT_LIST).toString());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_frequent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mFrequentData = passedIntent.getStringArrayListExtra(MyConstants.FREQUENT_ARRAYLIST);
        adapter = new FrequentListAdapter(mFrequentData);
        recyclerView.setAdapter(adapter);
    }

}

package com.dhcc.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        
    }

    private void initView() {
        List<Bean> list = new ArrayList<>();
        list.add(new Bean("Annnie", 9));
        list.add(new Bean("Jinx", 16));
        list.add(new Bean("Lux", 13));

        mRecyclerView = (RecyclerView) findViewById(R.id.rcv);
        TestAdapter testAdapter = new TestAdapter();
        testAdapter.setList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        testAdapter.setOnItemClickListener(mItemClickListener);
        mRecyclerView.setAdapter(testAdapter);

    }

    private TestAdapter.OnItemClickListener mItemClickListener = new TestAdapter.OnItemClickListener() {
        @Override
        public void onButtonClick(View view, int position) {
            Log.e("Button", "Button=====" + position);
        }

        @Override
        public void onTextClick(View view, int position) {
            Log.e("Text", "Text=====" + position);
        }

        @Override
        public void onCheckChange(View view, int position) {
            Log.e("Check", "Check=====" + position);
        }
    };

}

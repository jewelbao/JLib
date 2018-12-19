package com.jewel.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jewel.lib.android.recyclerView.RecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> data = new ArrayList<>();
    private List<String> clz = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data.add("FileUtil示例");
        clz.add(FileUtilActivity.class.getName());
        data.add("FileUtil示例");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewUtil.setupListView(recyclerView, new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                TextView textView = new TextView(viewGroup.getContext());
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                textView.setPadding(0, 40, 0 , 40);
                textView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                final RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(textView) {

                };
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int clickedPos = viewHolder.getLayoutPosition() % data.size();
                        gotoActivity(clz.get(clickedPos));
                    }
                });
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((TextView) viewHolder.itemView).setText(data.get(i));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        }, android.R.color.darker_gray);
    }

    private void gotoActivity(String clz) {
        Intent intent = new Intent();
        intent.setClassName(this, clz);
        startActivity(intent);
    }
}

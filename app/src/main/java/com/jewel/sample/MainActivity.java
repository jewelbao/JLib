package com.jewel.sample;

import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jewel.lib.android.recyclerView.RecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> data = new ArrayList<>();
    private List<String> clz = new ArrayList<>();

    private void addActivities() {
        data.add("FileUtil示例");
        clz.add(FileUtilActivity.class.getName());
        data.add("AppUtil示例");
        clz.add(AppUtilActivity.class.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addActivities();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewUtil.setupListView(recyclerView, new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                TextView textView = new TextView(viewGroup.getContext());
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                textView.setPadding(0, 40, 0, 40);
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


    public void test(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            CIRdeal.transmitKey(getApplicationContext(), "MENU", "0x00000040");
        }
    }

    private void gotoActivity(String clz) {
        Intent intent = new Intent();
        intent.setClassName(this, clz);
        startActivity(intent);
    }

    public static class CIRdeal {
        private static ConsumerIrManager consumerIrManager;

        //引导码
        private static int startH = 9000;
        private static int startL = 4500;

        private static int high8 = 560;
        //0：1125
        private static int low0 = 565;
        //1：2250
        private static int low1 = 1690;

        //用户编码高八位80
        private static String userH = "00000001";
        //用户编码低八位18
        private static String userL = "10001000";

        //38kHz
        private static int carrierFrequency = 38000;
        private static int[] pattern;

        private static List<Integer> list = new ArrayList<>();

        /**
         * 正常发码：引导码（9ms+4.5ms）+用户编码（低八位）+用户编码（高八位）+键数据码+键数据反码
         */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public static void transmitKey(Context context, String key, String key2) {
            if (checkCIR(context)) {
                list.clear();
                //引导码
                list.add(startH);
                list.add(startL);
                //用户编码
                change(userH);
                change(userL);
                //键数据码
                change(key);
                //键数据反码
                change(key2);
                //发射时数据少一位？？？
                change("1");

                int[] pattern = {
                        1000, 500, 1500, 1000,
                        1000, 500, 1500, 1000,
                        1000, 500, 1500, 1000,
                        1000, 500, 1500, 1000,
                        1000, 500, 1500, 1000,};

//                int size = list.size();
//                pattern = new int[size];
//                for (int i = 0; i < size; i++) {
//                    pattern[i] = list.get(i);
//                }
                consumerIrManager.transmit(carrierFrequency, pattern);
            }
        }

        /**
         * 二进制转成电平
         *
         * @param code
         */
        private static void change(String code) {
            int len = code.length();
            String part;
            for (int i = 0; i < len; i++) {
                list.add(high8);
                part = code.substring(i, i + 1);
                if (part.equals("0"))
                    list.add(low0);
                else
                    list.add(low1);
            }
        }

        /**
         * 检测手机是否有红外功能
         */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private static boolean checkCIR(Context context) {
            if (null == consumerIrManager) {
                // 获取系统的红外遥控服务
                consumerIrManager = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
            }
            if (consumerIrManager == null || !consumerIrManager.hasIrEmitter()) {
                Toast.makeText(context, "未找到红外发射器！", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Toast.makeText(context, "存在红外发射器！", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

}

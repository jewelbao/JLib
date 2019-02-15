package com.jewel.sample;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.jewel.lib.android.AppUtil;
import com.jewel.lib.android.HandlerUtil;
import com.jewel.lib.android.recyclerView.RecyclerViewUtil;
import com.jewel.lib.java.StringUtil;

public class AppUtilActivity extends Activity {

    private SimpleAdapter<AppUtil.AppInfo> simpleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        simpleAdapter = new SimpleAdapter<AppUtil.AppInfo>(R.layout.layout_application) {
            @Override
            public void bindView(SimpleViewHolder viewHolder, AppUtil.AppInfo data, int position) {
                viewHolder
                        .setText(R.id.tv_name, data.isSystem() ? StringUtil.get(getApplicationContext(), R.string.system, data.getName()) : data.getName())
                        .setText(R.id.tv_install, data.getInstallLocation() == AppUtil.AppInfo.INSTALL_EXTERNAL ? R.string.external : R.string.internal)
                        .setImageDrawable(R.id.iv_launcher, data.getLauncherIcon());
            }
        };
        RecyclerViewUtil.setupListView(recyclerView, simpleAdapter, android.R.color.darker_gray);

        HandlerUtil.runOnUiThreadDelay(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 2000);
    }

    public void getData() {
        try {
            simpleAdapter.setData(AppUtil.getAppList(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

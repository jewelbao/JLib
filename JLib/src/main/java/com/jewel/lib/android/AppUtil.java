package com.jewel.lib.android;

import android.annotation.TargetApi;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.jewel.lib.TAG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class AppUtil {


    public static List<AppInfo> getAppList(Context context) throws PackageManager.NameNotFoundException {
        List<AppInfo> appInfoList = new ArrayList<>();

        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        if (packages != null && !packages.isEmpty()) {
            for (PackageInfo info : packages) {
                Log.e(TAG.TAG, pm.getApplicationLabel(pm.getApplicationInfo(info.packageName, PackageManager.GET_META_DATA)).toString());
                AppInfo appInfo = new AppInfo();
                ApplicationInfo applicationInfo = info.applicationInfo;
                String packageName = info.packageName;
                appInfo.name = getApplicationName(pm, packageName);
                if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
                    // APP is currently installed on external/removable/unprotected storage.
                    appInfo.installLocation = AppInfo.INSTALL_EXTERNAL;
                } else {
                    appInfo.installLocation = AppInfo.INSTALL_INTERNAL;
                }
                appInfo.isSystem = (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM;
                appInfo.launcherIcon = pm.getApplicationIcon(packageName);
                appInfo.packageName = packageName;
                appInfo.versionName = info.versionName;
                appInfo.version = info.versionCode;
                appInfo.firstInstallTime = info.firstInstallTime;
                appInfo.lastUpdateTime = info.lastUpdateTime;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    appInfo.installLocation = info.installLocation;
                }
                appInfoList.add(appInfo);
            }
        }
        return appInfoList;
    }


    // see:https://blog.csdn.net/qq_23373271/article/details/73277390
    public static void xx(Context context) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            StorageStatsManager statsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);
            if (statsManager != null) {
                statsManager.getFreeBytes(UUID.fromString(""));
            }
        }
    }

    public static String getApplicationName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        return getApplicationName(pm, packageName);
    }

    private static String getApplicationName(PackageManager pm, String packageName) {
        try {
            return pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "unKnow";
    }

    public static class AppInfo {

        public static final int INSTALL_INTERNAL = 1;
        public static final int INSTALL_EXTERNAL = 2;

        private Drawable launcherIcon;
        private String name;
        private String packageName;
        private String versionName;
        private long version;
        private long firstInstallTime;
        private long lastUpdateTime;
        private boolean isSystem;
        private int installLocation;
        private boolean isRunning;

        public String getName() {
            return name;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getVersionName() {
            return versionName;
        }

        public long getVersion() {
            return version;
        }

        public long getFirstInstallTime() {
            return firstInstallTime;
        }

        public Drawable getLauncherIcon() {
            return launcherIcon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public boolean isRunning() {
            return isRunning;
        }

        /**
         * The install location requested by the package. From the
         * {@link android.R.attr#installLocation} attribute, one of
         * {@link PackageInfo#INSTALL_LOCATION_AUTO}, {@link PackageInfo#INSTALL_LOCATION_INTERNAL_ONLY},
         * {@link PackageInfo#INSTALL_LOCATION_PREFER_EXTERNAL}
         *
         * @return
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public int getInstallLocation() {
            return installLocation;
        }
    }
}

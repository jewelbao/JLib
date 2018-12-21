package com.jewel.lib.android;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/09/25
 */
public final class PermissionUtil {

    private PermissionUtil() {
    }

    private static volatile PermissionUtil instance = new PermissionUtil();
    private List<String> disallowPermissions = new ArrayList<>();

    public static PermissionUtil getInstance() {
        return instance;
    }

    public void checkPermissions(Activity context, String[] permissions, Runnable allowPermissionRunnable) {
        for (String permission : permissions) {
            checkPermission(context, permission);
        }

        if (!disallowPermissions.isEmpty()) {
            //Pop-up dialog box receiving permission 
            ActivityCompat.requestPermissions(context, disallowPermissions.toArray(new String[]{}), 1);
        } else {
            if(allowPermissionRunnable != null) {
                allowPermissionRunnable.run();
            }
        }
    }

    private void checkPermission(Activity context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkPermission = PermissionChecker.checkSelfPermission(context.getApplicationContext(), permission);
            if (PackageManager.PERMISSION_GRANTED != checkPermission) {
                disallowPermissions.add(permission);
            }
        }
    }

    public void checkPermissionResult(@NonNull String[] permissions, @NonNull int[] grantResults, Runnable allowPermissionRunnable, Runnable disallowPermissionRunnable) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                disallowPermissions.remove(permissions[i]);
            }
        }

        if (disallowPermissions.isEmpty()) {
            if (allowPermissionRunnable != null) {
                allowPermissionRunnable.run();
            }
        } else {
            if (disallowPermissionRunnable != null) {
                disallowPermissionRunnable.run();
            }
        }
    }
}

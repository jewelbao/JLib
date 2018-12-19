package com.jewel.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jewel.lib.java.FileUtil;

import java.io.File;

public class FileUtilActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file);
    }

    public void createFile(View view) {
        FileUtil.createFile(getExternalCacheDir().getPath() + File.separator + "files"+ File.separator + "file"+ File.separator  +"createFile.txt");
    }

    public void deleteFile(View view) {
        FileUtil.delete(getExternalCacheDir().getPath() + File.separator  + "files");
    }

    public void deleteFileForce(View view) {
        FileUtil.deleteForce(getExternalCacheDir().getPath() + File.separator + "files");
    }

    public void rename(View view) {
        FileUtil.rename(getExternalCacheDir().getPath() + File.separator + "createFile.txt", "newName.txt");
    }

    public void mkDirs(View view) {
        FileUtil.makeDirs(getExternalCacheDir().getPath() + File.separator + "dirs"+ File.separator + "dir");
    }
}


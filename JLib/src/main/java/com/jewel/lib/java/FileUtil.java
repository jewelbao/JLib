package com.jewel.lib.java;

import android.util.Log;

import com.jewel.lib.TAG;

import java.io.File;
import java.io.IOException;

public final class FileUtil {

    /**
     * 创建文件，如果该文件的目录不存在，则创建上级目录
     *
     * @param filePath 文件路径
     * @return 返回true表示文件创建成功或已存在。
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                boolean mkDirsResult = makeDirs(file.getParent());
                if (!mkDirsResult) {
                    Log.d(TAG.TAG, "create File[" + filePath + "] fail because it's parent dir created failed");
                    return false;
                }
            }
            try {
                boolean createFileResult = file.createNewFile();
                Log.d(TAG.TAG, "create File[" + filePath + "] result " + createFileResult);
                return createFileResult;
            } catch (IOException e) {
                Log.e(TAG.TAG, "create File[" + filePath + "]cause exception : " + e.getLocalizedMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            Log.d(TAG.TAG, "File[" + filePath + "] had exists!");
            return true;
        }
    }

    /**
     * 创建目录，如果该目录存在上级目录，则同样创建
     *
     * @param dirPath 目录路径
     * @return 返回true表示创建成功
     */
    public static boolean makeDirs(String dirPath) {
        File folder = new File(dirPath);
        if (!folder.exists()) {
            boolean mkDirsResult = folder.mkdirs();
            Log.d(TAG.TAG, "makeDirs[" + dirPath + "] result " + mkDirsResult);
            return mkDirsResult;
        }
        Log.d(TAG.TAG, "Dirs[" + dirPath + "] had exists!");
        return true;
    }

    /**
     * 删除文件，如果该文件为目录且存在下级目录或文件，则会删除失败
     *
     * @param filePath 文件路径
     * @return 返回true表示文件删除成功或文件不存在。
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleteFileResult = file.delete();
            Log.d(TAG.TAG, "deleted File[" + filePath + "] result " + deleteFileResult);
            return deleteFileResult;
        }
        Log.d(TAG.TAG, "File[" + filePath + "] is not exists");
        return true;
    }


    /**
     * 谨慎操作！强制删除文件，如果该文件为目录且存在下级目录或文件，将会一起删除
     *
     * @param filePath 文件路径
     * @return 返回true表示文件或该文件目录下所有文件删除成功
     */
    public static boolean deleteForce(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                boolean deleteDirsResult = file.delete();
                Log.d(TAG.TAG, "deleted file[" + filePath + "] result " + deleteDirsResult);
                return deleteDirsResult;
            }
            for (File childFile : childFiles) {
                deleteForce(childFile.getPath());
            }
            boolean deleteDirsResult = file.delete();
            Log.d(TAG.TAG, "deleted file[" + filePath + "] result " + deleteDirsResult);
            return deleteDirsResult;
        }
        Log.d(TAG.TAG, "file[" + filePath + "] is not exists");
        return true;
    }

    /**
     * 重命名
     *
     * @param filePath 文件路径
     * @param newName  新文件名
     * @return 返回true表示文件命名成功。
     */
    public static boolean rename(String filePath, String newName) {
        File file = new File(filePath);
        File newFile = new File(file.getParent(), newName);
        boolean renameFileResult = file.renameTo(newFile);
        Log.d(TAG.TAG, "rename File[" + filePath + "] result " + renameFileResult);
        Log.d(TAG.TAG, "newFile Name[" + file.getName() + "] ");
        return renameFileResult;
    }
}

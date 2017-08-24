package com.work.snaildan.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.work.snaildan.activity.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by zhangdan on 2017/8/24.
 */
public class DataBackupRecover extends AsyncTask<String, Integer, Integer> {
    private static final String COMMAND_BACKUP = "backupDatabase";
    private static final String COMMAND_RESTORE = "restoreDatabase";
    private Context mContext;
    private ProgressDialog moreDialog;
    private int i;
    private int doType;
    private int result;
    private String resultStr;

    public DataBackupRecover(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        // 创建ProgressDialog对象
        moreDialog = new ProgressDialog(this.mContext);
        // 设置进度条风格，风格为圆形，旋转的
        moreDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 标题
        moreDialog.setTitle("提示");
        // 设置ProgressDialog提示信息
        moreDialog.setMessage("数据处理进度......");
        // 设置ProgressDialog标题图标
        moreDialog.setIcon(R.drawable.budget_warning_icon);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        moreDialog.setIndeterminate(false);
        // 设置ProgressDialog 进度条进度
        moreDialog.setProgress(100);
        // 让ProgressDialog显示
        moreDialog.show();
    }

    protected void onProgressUpdate(Integer... values) {
        moreDialog.setProgress(i);
    }

    @Override
    protected void onPostExecute(Integer result) {
        moreDialog.cancel();
        if (doType == 1) {
            if (result == 1) {
                resultStr = "数据备份完成！";
            } else {
                resultStr = "数据备份失败！";
            }
            Toast.makeText(this.mContext, resultStr, Toast.LENGTH_SHORT).show();
        } else {
            if (result == 1) {
                resultStr = "数据恢复完成！";
            } else {
                resultStr = "数据恢复失败！";
            }
            Toast.makeText(this.mContext, resultStr, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(String... params) {
        //获得正在使用的数据库路径，sdcard 目录下的 /dlion/db_dlion.db
        //File dbFile = mContext.getDatabasePath(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/dlion/db_dlion.db");
        //默认路径是 /data/data/(包名)/databases/*.db
        File dbFile = mContext.getDatabasePath("bookmarks_db.db");
        File DirName = this.mContext.getFilesDir();
        File exportDir = new File(DirName.getAbsolutePath(), "bookmarksBackup");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File backup = new File(exportDir, dbFile.getName());
        String command = params[0];

        for (i = 0; i < 100; i = i + 13) {
            publishProgress(i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (command.equals(COMMAND_BACKUP)) {
            doType = 1;
            try {
                backup.createNewFile();
                fileCopy(dbFile, backup);
                Log.i("-------backup", "ok");
                result = 1;
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("-------backup", "fail");
                result = 0;
                return result;
            }
        } else if (command.equals(COMMAND_RESTORE)) {
            doType = 0;
            try {
                fileCopy(backup, dbFile);
                Log.i("-------restore", "success");
                result = 1;
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("-------restore", "fail");
                result = 0;
                return result;
            }
        } else {
            return null;
        }
    }

    //文件copy
    private void fileCopy(File dbFile, File backup) throws IOException {
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
}

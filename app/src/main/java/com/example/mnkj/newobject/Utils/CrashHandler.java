package com.example.mnkj.newobject.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.mnkj.newobject.Activity.LoginActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例  
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的Context对象  
    private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            long current = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(current));
            Intent intent = new Intent();
            intent.setClass(mContext, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            File file = new File(Environment.getExternalStorageDirectory(), "error.txt");
            PrintWriter pw;
            try {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                pw.println(time);
                pw.println();
                ex.printStackTrace(pw);
                ex.printStackTrace();
                pw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                Log.e(TAG, "dump crash info faild");
            }
            mContext.startActivity(intent);
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息  
        new Thread() {
            @Override
            public void run() {
                //在此可执行其它操作，如获取设备信息、执行异常登出请求、保存错误日志到本地或发送至服务端

                // 1.获取当前程序的版本号. 版本的id
                String versioninfo = getVersionInfo();
                // 2.获取手机的硬件信息.  
                String mobileInfo = getMobileInfo();
                // 3.把错误的堆栈信息 获取出来   
                String errorinfo = getErrorInfo(ex);
                //文件保存本地
                PictureFun.SavaJson(versioninfo + "\n\n\n\n"
                        + mobileInfo + "\n\n\n\n\n" +
                        errorinfo);
                Log.v("error", errorinfo);
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();


            }
        }.start();
        return true;
    }

    /**
     * 获取错误的信息
     *
     * @param arg1
     * @return
     */
    private String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    /**
     * 获取手机的硬件信息
     *
     * @return
     */
    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        //通过反射获取系统的硬件信息   
        try {

            java.lang.reflect.Field[] fields = Build.class.getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                //暴力反射 ,获取私有的信息   
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取手机的版本信息
     *
     * @return
     */
    private String getVersionInfo() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }

}

package com.pdaxrom.cctools;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pdaxrom.utils.Utils;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class StartActivity extends Activity {

    private Button btn;
    private String workDir;
    private String[] argv;
    private String[] envp;
    private int[] pId;
    private FileDescriptor mFd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        ///  workDir=storage/sdcard0/CCTools/Examples
        ///  argv[0]=/system/bin/sh
        ///  envp=
        //   0    PWD=/storage/sdcard0/CCTools/Examples
        //       TMPDIR=/storage/sdcard0/CCTools/tmp
        //       PATH=/data/data/com.pdaxrom.cctools/root/cctools/bin:/data/data/com.pdaxrom.cctools/root/cctools/sbin:/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin
        //       ANDROID_ASSETS=/system/app
				/*       ANDROID_BOOTLOGO=1
						 ANDROID_DATA=/data/data/com.pdaxrom.cctools/root/cctools/var/dalvik
						 ANDROID_ROOT=/system
						 CCTOOLSDIR=/data/data/com.pdaxrom.cctools/root/cctools
						 CCTOOLSRES=/data/app/com.pdaxrom.cctools-1.apk
						 LD_LIBRARY_PATH=/data/data/com.pdaxrom.cctools/root/cctools/lib:/system/lib:/vendor/lib
						 HOME=/data/data/com.pdaxrom.cctools/root/cctools/home
						 TMPEXEDIR=/data/data/com.pdaxrom.cctools/root/tmp
					12	 PS1=""     */
        workDir = "/storage/emulated/0";
        argv = new String[]{"/system/bin/sh"};
        envp = new String[]{"PWD=/storage/sdcard0/CCTools/Examples",
        "TMPDIR=/storage/sdcard0/CCTools/tmp",
        "PATH=/data/data/com.pdaxrom.cctools/root/cctools/bin:/data/data/com.pdaxrom.cctools/root/cctools/sbin:/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin",
        "ANDROID_ASSETS=/system/app",
        "ANDROID_BOOTLOGO=1",
        "ANDROID_DATA=/data/data/com.pdaxrom.cctools/root/cctools/var/dalvik",
        "ANDROID_ROOT=/system",
        "CCTOOLSDIR=/data/data/com.pdaxrom.cctools/root/cctools",
        "CCTOOLSRES=/data/app/com.pdaxrom.cctools-1.apk",
        "LD_LIBRARY_PATH=/data/data/com.pdaxrom.cctools/root/cctools/lib:/system/lib:/vendor/lib",
        "HOME=/data/data/com.pdaxrom.cctools/root/cctools/home",
        "TMPEXEDIR=/data/data/com.pdaxrom.cctools/root/tmp",
        "PS1=''",

        };
        pId = new int[1];


        btn = (Button) findViewById(R.id.btn_startforkprocess);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               MyThread threa=new MyThread();
                threa.start();
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    public void run() {
                        Toast.makeText(StartActivity.this, mFd.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("zhu", mFd.toString());
                    }
                },3000);

            }
        });


    }
    public class MyThread extends Thread {
        public void run() {

            mFd = Utils.createSubProcess(workDir, argv[0], argv, envp, pId);
            //mFd.

            Utils.setPtyUTF8Mode(mFd, true);
            Utils.setPtyWindowSize(mFd, 64, 128, 0, 0);
            BufferedReader procout = new BufferedReader(new InputStreamReader(new FileInputStream(mFd)));


        }

    }


}

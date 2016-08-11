package com.education.online.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by 可爱的蘑菇 on 2016/3/13.
 */
public class DialogUtil {

    public static void showInfoDailog(Context con, String title, String content){
        DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
            }
        };
        showInfoDialog(con, title, content, listener);
    }


    public static void showInfoDialog(Context con, String title, String content, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder=new AlertDialog.Builder(con);  //先得到构造器
        builder.setTitle(title); //设置标题
        builder.setMessage(content); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", listener);
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    public static void showSelectDialog(Context con, String title, String[] item, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder4=new AlertDialog.Builder(con);  //先得到构造器
        builder4.setTitle(title); //设置标题
        builder4.setItems(item, listener);
        builder4.create().show();
    }
}

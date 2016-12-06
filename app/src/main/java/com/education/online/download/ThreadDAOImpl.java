package com.education.online.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class ThreadDAOImpl implements ThreadDAO{
    private DBHelper mHelper = null;
    public ThreadDAOImpl(Context context){
        mHelper = new DBHelper(context);
    }
    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(complete,url,start,end,finished,courseid,courseimg) values(?,?,?,?,?,?,?)",
                new Object[]{threadInfo.getComplete(),threadInfo.getUrl(),threadInfo.getStart(),threadInfo.getEnd(),
                        threadInfo.getEnd(),threadInfo.getCourseid(), threadInfo.getCourseimg()});
        db.close();
    }

    @Override
    public void deleteThread(String url) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url=?",
                new Object[]{url});
        db.close();

    }

    @Override
    public void updateThread(String url,int finished) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished=? where url=?",
                new Object[]{finished,url});
        db.close();
    }

    public void updateThreadComplete(String url,int complete) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("update thread_info set complete=? where url=?",
                new Object[]{complete,url});
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url, int complete) {
        List<ThreadInfo> list = new ArrayList<ThreadInfo>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url=? and complete=?", new String[]{url, complete+""});
        while(cursor.moveToNext()){
            ThreadInfo thread = new ThreadInfo();
            thread.setComplete(cursor.getInt(cursor.getColumnIndex("complete")));
            thread.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            thread.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            thread.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            thread.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            thread.setCourseid(cursor.getString(cursor.getColumnIndex("courseid")));
            thread.setCourseimg(cursor.getString(cursor.getColumnIndex("courseimg")));
            list.add(thread);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExtists(String url) {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url=?", new String[]{url});
        boolean extists = cursor.moveToNext();
        cursor.close();
        db.close();

        return extists;
    }
}

package com.education.online.bean;

import com.education.online.util.LogUtil;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/7.
 */
public class CourseTimeBean {
    private String longtime="";
    private String hour="";
    private String min="";
    private String datetime="";

    public  String getDisplayTxt(){
        String txt="";
        SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:MM");
        SimpleDateFormat timeEndFormat=new SimpleDateFormat("HH:MM");
        String startTime=datetime+" "+hour+":"+min;
        Date date = null;
        try {
            date = timeFormat.parse(startTime);
            startTime=timeFormat.format(date);
            LogUtil.i("date", "start: "+startTime);
            long add_mins=(long) (Float.valueOf(longtime)*60);
            LogUtil.i("date", "add_mins: "+add_mins);
            long end_time=date.getTime()+add_mins*60*1000;
            date.setTime(end_time);
            txt=" - "+timeEndFormat.format(date);
            LogUtil.i("date", "end:  "+timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime+txt;
    }

    public String getLongtime() {
        return longtime;
    }

    public void setLongtime(String longtime) {
        this.longtime = longtime;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}

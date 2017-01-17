package com.education.online.act.video;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.CourseBean;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.dialog.SelectorPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.CourseUpdate;
import com.education.online.util.ActUtil;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class LiveTelecast extends BaseFrameAct {

    private TextView Today;
    private TextView Day2, Day3, Day4, Day5, Day6, Day7;
    private HttpHandler handler;
    private ArrayList<TextView> txts=new ArrayList<>();
    private boolean siNew=false;
    private int pos=1;
    private CourseUpdate onlineCourse;
    private CourseUpdate[] lists=new CourseUpdate[7];

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getlistByDate)){
                    ArrayList<CourseBean> items= JSON.parseObject(JsonUtil.getString(jsonData, "course_info"),
                            new TypeReference<ArrayList<CourseBean>>(){});

                    onlineCourse.addCourses(items, false);
                }
            }
        });
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_telecats);

        _setHeaderTitle("全部科目");
        initView();
        initHandler();

        Today.callOnClick();
    }

    private void initView() {

        Today = (TextView) findViewById(R.id.Today);
        txts.add(Today);
        Day2 = (TextView) findViewById(R.id.Day2);
        txts.add(Day2);
        Day3 = (TextView) findViewById(R.id.Day3);
        txts.add(Day3);
        Day4 = (TextView) findViewById(R.id.Day4);
        txts.add(Day4);
        Day5 = (TextView) findViewById(R.id.Day5);
        txts.add(Day5);
        Day6 = (TextView) findViewById(R.id.Day6);
        txts.add(Day6);
        Day7 = (TextView) findViewById(R.id.Day7);
        txts.add(Day7);

        for (int i=0;i<txts.size();i++){
            TextView txt=txts.get(i);
            txt.setTag(i);
            if(i!=0) {
                txt.setText(ActUtil.getChangedDateByMonthDay(Calendar.DAY_OF_YEAR, i));
            }
            txt.setOnClickListener(listener);
        }
    }

    View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            int pressPos=(int) view.getTag();
            if(pos!=pressPos) {
                txts.get(pos).setTextColor(getResources().getColor(R.color.hard_gray));
                pos = pressPos;
                siNew = true;
                txts.get(pos).setTextColor(getResources().getColor(R.color.normal_blue));

                onlineCourse=lists[pos];
                if(onlineCourse!=null) {
                    openFragment(R.id.fragment_list, onlineCourse);
                }else {
                    lists[pos]=new OnlineCoursePage();
                    onlineCourse=lists[pos];
                    openFragment(R.id.fragment_list, onlineCourse);
                    handler.getlistByDate(ActUtil.getChangedDate(Calendar.DAY_OF_YEAR, pos));
                }
            }
        }
    };
}

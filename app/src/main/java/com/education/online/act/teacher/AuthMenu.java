package com.education.online.act.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.CommentsAdapter;
import com.education.online.bean.CourseTimeBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.bean.TeacherAuth;
import com.education.online.bean.TeacherBean;
import com.education.online.fragment.BaseFragment;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.DialogUtil;
import com.education.online.view.SelectWeekdayDialog;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/29.
 */
public class AuthMenu extends BaseFrameAct {




    HttpHandler handler;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getValidateView)){

                    Intent i=new Intent(AuthMenu.this, TeacherAuthPage.class);
                    i.putExtra("jsonData", jsonData);
                    startActivity(i);
                }else if(method.equals(Method.updateTeacher)){

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_auth_menu);

        _setHeaderTitle("生效成为老师");
        initHandler();
        initView();
    }

    private void initView() {
        findViewById(R.id.baseInfoLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthMenu.this, TeacherInfoEdit.class));
            }
        });
        findViewById(R.id.degreeLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.getValidateView();
            }
        });
    }

    /**
     * Created by Administrator on 2016/8/25.
     */


}

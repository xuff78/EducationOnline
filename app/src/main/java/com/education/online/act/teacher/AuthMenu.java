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

    private TeacherAuth status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_auth_menu);

        _setHeaderTitle("生效成为老师");
        status= JSON.parseObject(getIntent().getStringExtra("jsonData"), TeacherAuth.class);
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
                Intent i=getIntent();
                i.setClass(AuthMenu.this, TeacherAuthPage.class);
                startActivity(i);
            }
        });
        setInfo(((TextView)findViewById(R.id.hintTxt1)), status.getIs_ext_validate());
        TextView hintTxt2=(TextView)findViewById(R.id.hintTxt2);
        if(status.getIs_edu_bg_validate().equals("1")&&status.getIs_id_validate().equals("1")&&status.getIs_specialty_validate().equals("1")
                &&status.getIs_tc_validate().equals("1")&&status.getIs_unit_validate().equals("1"))
            hintTxt2.setText("通过");
        else
            hintTxt2.setText("去完善");
    }

    private void setInfo(TextView txt, String status) {
        if(status.equals("3")){

        }else {
            txt.setTextColor(getResources().getColor(R.color.normal_red));
            txt.setBackgroundResource(R.drawable.shape_normalredline_with_corner);
            if (status.equals("0")) {
                txt.setText("待审核");
            }else if (status.equals("1")) {
                txt.setText("通过");
            }else if (status.equals("2")) {
                txt.setText("拒绝");
            }else
                txt.setText("去完善");
        }
    }

    View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.baseInfoLayout:
                    startActivity(new Intent(AuthMenu.this, TeacherInfoEdit.class));
                    break;
                case R.id.degreeLayout:
                    Intent i=getIntent();
                    i.setClass(AuthMenu.this, TeacherAuthPage.class);
                    startActivity(i);
                    break;
            }
        }
    };

}

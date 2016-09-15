package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.view.MenuPopup;



/**
 * Created by Administrator on 2016/9/13.
 */

public class IllegalReport extends BaseFrameAct {


    private TextView courseName, setPhoneNum,complaintreason, submitbtn;
    private EditText complaintdetail;
    private RelativeLayout complaintReasonDrop;
    private LinearLayout teacherandorg,addpictures;
    private ImageView addpicture;
    private MenuPopup popup;
    private String[] reportTpe = {"人身攻击","语言谩骂","态度不好"};
    private int type = 0;
    private View searchlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.illegalreport);
        _setHeaderTitle("违规举报");
        init();
    }
    public void init(){
        teacherandorg = (LinearLayout) findViewById(R.id.teacherandorg);
        courseName = (TextView) findViewById(R.id.courseName);
        setPhoneNum = (TextView) findViewById(R.id.setPhoneNum);
        submitbtn = (TextView) findViewById(R.id.submitbtn);
        complaintdetail = (EditText) findViewById(R.id.complaintdetail);
        complaintReasonDrop= (RelativeLayout) findViewById(R.id.complaintReasonDrop);
        complaintreason= (TextView) findViewById(R.id.complaintreason);
        addpictures= (LinearLayout) findViewById(R.id.addpictures);
        searchlayout= findViewById(R.id.searchlayout);
        addpicture = (ImageView) findViewById(R.id.addpicture);
        popup = new MenuPopup(IllegalReport.this, reportTpe, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                complaintreason.setText(reportTpe[i]);
            }
        });

        complaintReasonDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.showPopupWindow(searchlayout);
            }
        });








    }
}

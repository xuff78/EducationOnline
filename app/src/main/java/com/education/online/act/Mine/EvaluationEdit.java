package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.view.MenuPopup;

/**
 * Created by 可爱的蘑菇 on 2016/9/17.
 */
public class EvaluationEdit extends BaseFrameAct {


    private TextView courseName, setPhoneNum,complaintreason, submitbtn;
    private EditText complaintdetail;
    private RelativeLayout complaintReasonDrop;
    private LinearLayout teacherandorg,addpictures;
    private ImageView addpicture;
    private MenuPopup popup;
    private View searchlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evalution_edit);
        _setHeaderTitle("违规举报");
        init();
    }

    public void init() {
        teacherandorg = (LinearLayout) findViewById(R.id.teacherandorg);
    }
}

package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.util.ToastUtils;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CourseMemberEdit  extends BaseFrameAct {

    private EditText minNum, maxNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_member_edit);

        _setHeaderTitle("开班人数");
        _setRightHomeText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minTxt=minNum.getText().toString().trim();
                String maxTxt=maxNum.getText().toString().trim();
                if(minTxt.length()==0||maxTxt.length()==0){
                    ToastUtils.displayTextShort(CourseMemberEdit.this, "请填写完整信息");
                    return;
                }
                Double min=Double.valueOf(minTxt);
                Double max=Double.valueOf(maxTxt);
                if(min>max){
                    ToastUtils.displayTextShort(CourseMemberEdit.this, "最高人数不能小于最低人数");
                    return;
                }
                Intent i=new Intent();
                i.putExtra("min", minTxt);
                i.putExtra("max", maxTxt);
                setResult(0x13, i);
                finish();
            }
        });
        initView();
    }

    private void initView() {
        TextView right_text= (TextView) findViewById(R.id.right_text);
        right_text.setTextColor(getResources().getColor(R.color.normal_red));
        minNum= (EditText) findViewById(R.id.minNum);
        maxNum= (EditText) findViewById(R.id.maxNum);
    }
}

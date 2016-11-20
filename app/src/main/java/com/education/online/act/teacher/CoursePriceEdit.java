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
public class CoursePriceEdit  extends BaseFrameAct {

    private EditText cPrice, oPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_price_edit);

        _setHeaderTitle("设定价格");

        _setRightHomeText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cprice=cPrice.getText().toString().trim();
                String oprice=oPrice.getText().toString().trim();
                if(cprice.length()==0||oprice.length()==0){
                    ToastUtils.displayTextShort(CoursePriceEdit.this, "请填写完整信息");
                    return;
                }
                Double cp=Double.valueOf(cprice);
                Double op=Double.valueOf(oprice);
                if(cp>op){
                    ToastUtils.displayTextShort(CoursePriceEdit.this, "原价不能小于现价");
                    return;
                }
                Intent i=new Intent();
                i.putExtra("cp", cprice);
                i.putExtra("op", oprice);
                setResult(0x12, i);
                onBackPressed();
            }
        });
        initView();
    }

    private void initView() {
        TextView right_text= (TextView) findViewById(R.id.right_text);
        right_text.setTextColor(getResources().getColor(R.color.normal_red));
        cPrice= (EditText) findViewById(R.id.cPrice);
        oPrice= (EditText) findViewById(R.id.oPrice);
        if(getIntent().hasExtra("Price"))
            cPrice.setText(getIntent().getStringExtra("Price"));
        if(getIntent().hasExtra("Original_price"))
            oPrice.setText(getIntent().getStringExtra("Original_price"));
    }
}

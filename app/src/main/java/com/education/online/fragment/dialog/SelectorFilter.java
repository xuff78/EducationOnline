package com.education.online.fragment.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.FilterAll;
import com.education.online.bean.FilterInfo;
import com.education.online.fragment.BaseFragment;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.AutoFitLinearLayout;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/8/21.
 */
public class SelectorFilter extends BaseFragment implements View.OnClickListener {

    private ImageView pressView = null;
    private LinearLayout detailLayout;
    private ArrayList<FilterInfo> filters=new ArrayList<>();
    private ArrayList<ArrayList<TextView>> txtViews=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selector_filter, container, false);

        initView(view);
        return view;
    }

    private void initView(View v) {
        v.findViewById(R.id.confirmBtn).setOnClickListener(this);
        v.findViewById(R.id.resetBtn).setOnClickListener(this);
        detailLayout= (LinearLayout) v.findViewById(R.id.detailLayout);

        filters=((FilterAll)getArguments().getSerializable(FilterAll.Name)).getList();
        LinearLayout.LayoutParams llptitle = new LinearLayout.LayoutParams(-2, -2);
        llptitle.topMargin = ImageUtil.dip2px(getActivity(), 15);
        llptitle.bottomMargin = ImageUtil.dip2px(getActivity(), 15);

        for(int j=0;j<filters.size();j++) {
            final FilterInfo info=filters.get(j);

            TextView title = new TextView(getActivity());
            title.setTextSize(13);
            title.setTextColor(Color.GRAY);
            title.setText(info.getTypeName());
            detailLayout.addView(title, llptitle);


            String[] itemInfo=info.getItemInfo();
            int itemWidth = (ScreenUtil.getWidth(getActivity()) - ImageUtil.dip2px(getActivity(), 85)) / 4;
            int itemHeight = ImageUtil.dip2px(getActivity(), 30);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(itemWidth, itemHeight);
            llp.rightMargin = ImageUtil.dip2px(getActivity(), 15);
            LinearLayout linelayout = new LinearLayout(getActivity());
            final ArrayList<TextView> selectionTxts=new ArrayList<>();

            for(int i=0;i<itemInfo.length;i++){
                final int k=i;
                TextView txt=new TextView(getActivity());
                txt.setTextSize(13);
                txt.setGravity(Gravity.CENTER);
                txt.setBackgroundResource(R.drawable.shape_corner_blackline);
                txt.setTextColor(Color.GRAY);
                txt.setText(itemInfo[i]);
                linelayout.addView(txt, llp);
                selectionTxts.add(txt);
                if(i%4==3||i==itemInfo.length-1){
                    detailLayout.addView(linelayout);
                    linelayout=new LinearLayout(getActivity());
                    linelayout.setPadding(0, llp.rightMargin,0,0);
                }
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(info.getSelection()!=-1) {
                            TextView txt = selectionTxts.get(info.getSelection());
                            txt.setBackgroundResource(R.drawable.shape_corner_blackline);
                            txt.setTextColor(Color.GRAY);
                        }
                        TextView selectTxt= (TextView) view;
                        selectTxt.setBackgroundResource(R.drawable.shape_orangedline_with_corner);
                        selectTxt.setTextColor(getResources().getColor(R.color.dark_orange));
                        info.setSelection(k);
                    }
                });
            }
            if(info.getSelection()!=-1){
                TextView txt=selectionTxts.get(info.getSelection());
                txt.setBackgroundResource(R.drawable.shape_orangedline_with_corner);
                txt.setTextColor(getResources().getColor(R.color.dark_orange));
            }
            detailLayout.addView(linelayout);
            txtViews.add(selectionTxts);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirmBtn:
                break;
            case R.id.resetBtn:
                break;
        }
    }
}

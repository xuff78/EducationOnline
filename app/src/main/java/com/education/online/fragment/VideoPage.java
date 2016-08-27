package com.education.online.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.adapter.CommentsAdapter;
import com.education.online.adapter.CourseAdapter;
import com.education.online.adapter.DetailsAdapter;
import com.education.online.adapter.DirectoryAdapter;

/**
 * Created by Administrator on 2016/8/25.
 */

public class VideoPage extends BaseFragment implements View.OnClickListener {

    private boolean Ispaid = false;
    private TextView paytips, payBtn;
    private LinearLayout details, directory, comments;
    private TextView textdetails, textdirectory, textcomments;
    private View viewdetails, viewdirectory, viewcomments;
    private ImageView roundLeftBack;
    private RecyclerView recyclerList;
    private View lastSelectedview;
    private int lastSelectedPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_detail_main, container, false);

        initView(view);
        recyclerList.setAdapter(new DetailsAdapter(getActivity(), ""));
        return view;
    }

    public void setPaidStatus(boolean flag) {
        this.Ispaid = flag;
    }


    private void initView(View v) {
        recyclerList = (RecyclerView) v.findViewById(R.id.courseRecycleView);
        details = (LinearLayout) v.findViewById(R.id.details);
        details.setOnClickListener(this);
        directory = (LinearLayout) v.findViewById(R.id.directory);
        directory.setOnClickListener(this);
        comments = (LinearLayout) v.findViewById(R.id.comments);
        comments.setOnClickListener(this);

        paytips = (TextView) v.findViewById(R.id.payTips);
        payBtn = (TextView) v.findViewById(R.id.payBtn);
        roundLeftBack = (ImageView) v.findViewById(R.id.roundLeftBack);

        textdetails = (TextView) v.findViewById(R.id.textdetails);
        textdirectory = (TextView) v. findViewById(R.id.textdirectory);
        textcomments = (TextView) v.findViewById(R.id.textcomments);

        viewdetails = v.findViewById(R.id.viewdetails);
        viewdirectory = v. findViewById(R.id.viewdirectory);
        viewcomments = v.findViewById(R.id.viewcomments);
        // get layout Manager

        //set Status
        if (Ispaid == false) {
            //do sth
            payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do sth
                }
            });
        } else {
            paytips.setVisibility(View.INVISIBLE);
            payBtn.setVisibility(View.INVISIBLE);
        }

    //
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        lastSelectedview = details;
        lastSelectedPosition=0;

    }
    public void setStatusFalse(int pos){
        switch (pos)
        {
            case 0:
                textdetails.setTextColor(getResources().getColor(R.color.light_gray));
                viewdetails.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textdirectory.setTextColor(getResources().getColor(R.color.light_gray));
                viewdirectory.setVisibility(View.INVISIBLE);
                break;
            case 2:
                textcomments.setTextColor(getResources().getColor(R.color.light_gray));
                viewcomments.setVisibility(View.INVISIBLE);
                break;
        }
    }



    @Override
    public void onClick(View view) {
        if (view != lastSelectedview) {
            setStatusFalse(lastSelectedPosition);
            switch (view.getId()) {
                case R.id.details:
                    recyclerList.setAdapter(new DetailsAdapter(getActivity(), ""));
                    lastSelectedview= details;
                    lastSelectedPosition=0;
                    textdetails.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewdetails.setVisibility(View.VISIBLE);
                    break;
                case R.id.directory:
                    recyclerList.setAdapter(new DirectoryAdapter(getActivity(), ""));
                    lastSelectedview= directory;
                    lastSelectedPosition=1;
                    textdirectory.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewdirectory.setVisibility(View.VISIBLE);

                    break;
                case R.id.comments:
                    recyclerList.setAdapter(new CommentsAdapter(getActivity(), ""));
                    lastSelectedview= comments;
                    lastSelectedPosition=2;
                    textcomments.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewcomments.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}

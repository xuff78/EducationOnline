package com.education.online.fragment.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.act.Mine.HelpandFeedback;
import com.education.online.act.Mine.MyCourseMuti;
import com.education.online.act.Mine.MyEvaluation;
import com.education.online.act.Mine.MyOrderUser;
import com.education.online.act.Mine.MyWallet;
import com.education.online.act.Mine.Settings;
import com.education.online.act.Mine.UserInfoEdit;
import com.education.online.act.Mine.AskAndAnswer;
import com.education.online.fragment.BaseFragment;

/**
 * Created by 可爱的蘑菇 on 2016/9/10.
 */
public class MyCenterMain extends BaseFragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mycenter_layout, container, false);

        initView(view);
        return view;
    }

    private void initView(View v) {
        v.findViewById(R.id.myCourseLayout).setOnClickListener(this);
        v.findViewById(R.id.myDownloadLayout).setOnClickListener(this);
        v.findViewById(R.id.myOrderLayout).setOnClickListener(this);
        v.findViewById(R.id.descTxt).setOnClickListener(this);

        v.findViewById(R.id.myWalletLayout).setOnClickListener(this);
        v.findViewById(R.id.mySchoolLayout).setOnClickListener(this);
        v.findViewById(R.id.interestingLayout).setOnClickListener(this);
        v.findViewById(R.id.myQuestionLayout).setOnClickListener(this);
        v.findViewById(R.id.myCollectionLayout).setOnClickListener(this);
        v.findViewById(R.id.settingLayout).setOnClickListener(this);
        v.findViewById(R.id.helpLayout).setOnClickListener(this);
        v.findViewById(R.id.myCommentLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent();
        switch (view.getId()) {
            case R.id.myCourseLayout:
                i.setClass(getActivity(), MyCourseMuti.class);
                i.putExtra("Type", 0);
                startActivity(i);
                break;
            case R.id.myDownloadLayout:
                i.setClass(getActivity(), MyCourseMuti.class);
                i.putExtra("Type", 2);
                startActivity(i);
                break;
            case R.id.myOrderLayout:
                startActivity(new Intent(getActivity(), MyOrderUser.class));
                break;
            case R.id.myWalletLayout:
                startActivity(new Intent(getActivity(), MyWallet.class));
                break;
            case R.id.mySchoolLayout:
                break;
            case R.id.myQuestionLayout:
                startActivity(new Intent(getActivity(), AskAndAnswer.class));
                break;
            case R.id.myCollectionLayout:
                i.setClass(getActivity(), MyCourseMuti.class);
                i.putExtra("Type", 1);
                startActivity(i);
                break;
            case R.id.settingLayout:
                startActivity(new Intent(getActivity(), Settings.class));
                break;
            case R.id.helpLayout:
                startActivity(new Intent(getActivity(), HelpandFeedback.class));
                break;
            case R.id.myCommentLayout:
                startActivity(new Intent(getActivity(), MyEvaluation.class));
                break;
            case R.id.interestingLayout:
                break;
            case R.id.descTxt:
                startActivity(new Intent(getActivity(), UserInfoEdit.class));
                break;
        }
    }
}

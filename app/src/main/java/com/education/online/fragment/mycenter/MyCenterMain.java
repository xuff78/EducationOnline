package com.education.online.fragment.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.avos.avoscloud.AVException;
import com.avoscloud.leanchatlib.model.Room;
import com.education.online.R;
import com.education.online.act.Mine.HelpandFeedback;
import com.education.online.act.Mine.MyCourseMuti;
import com.education.online.act.Mine.MyDownload;
import com.education.online.act.Mine.MyEvaluation;
import com.education.online.act.Mine.MyOrderUser;
import com.education.online.act.Mine.MyQuestion;
import com.education.online.act.Mine.MyWallet;
import com.education.online.act.Mine.Settings;
import com.education.online.act.Mine.UserInfoEdit;
import com.education.online.act.Mine.AskAndAnswer;
import com.education.online.act.Mine.MyInteresting;
import com.education.online.act.chat.ConversationManager;
import com.education.online.act.discovery.MessageMain;
import com.education.online.bean.HomePageInfo;
import com.education.online.bean.LoginInfo;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.UserInfo;
import com.education.online.fragment.BaseFragment;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/10.
 */
public class MyCenterMain extends BaseFragment implements View.OnClickListener{

    private HttpHandler handler;
    private ImageLoader imageLoader;
    private ImageView teacherImg;
    private TextView nameTxt,messageStatus;
    private ConversationManager conversationManager = ConversationManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mycenter_layout, container, false);
        imageLoader=ImageLoader.getInstance();
        initView(view);
        initHandler();
        return view;
    }

    private void initView(View v) {
        messageStatus= (TextView) v.findViewById(R.id.messageStatus);
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
        v.findViewById(R.id.messageIcon).setOnClickListener(this);

        teacherImg= (ImageView) v.findViewById(R.id.teacherImg);
        nameTxt= (TextView) v.findViewById(R.id.nameTxt);
    }

    @Override
    public void onResume() {
        super.onResume();
        imageLoader.displayImage(ImageUtil.getImageUrl(SharedPreferencesUtil.getString(getActivity(), Constant.Avatar)), teacherImg);
        nameTxt.setText(SharedPreferencesUtil.getString(getActivity(), Constant.NickName));
        updateConversationList();
    }

    private void updateConversationList() {
        if(conversationManager!=null)
            conversationManager.findAndCacheRooms(new Room.MultiRoomsCallback() {
                @Override
                public void done(List<Room> roomList, AVException exception) {
                    if (exception==null) {
                        int news=0;
                        for(int i=0;i<roomList.size();i++){
                            Room room=roomList.get(i);
                            news+=room.getUnreadCount();
                        }
                        if(news>0) {
                            messageStatus.setVisibility(View.VISIBLE);
                            if(news>99)
                                messageStatus.setText("99");
                            else
                                messageStatus.setText(news+"");
                        }else
                            messageStatus.setVisibility(View.GONE);
                    }
                }
            });
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
                i.setClass(getActivity(), MyDownload.class);
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
                startActivity(new Intent(getActivity(), MyQuestion.class));
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
                handler.getSubjectList();
                break;
            case R.id.descTxt:
                startActivity(new Intent(getActivity(), UserInfoEdit.class));
                break;
            case R.id.messageIcon:
                startActivity(new Intent(getActivity(), MessageMain.class));
                break;
        }
    }

    private void initHandler() {
        handler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.getSubjectList)){
                    Intent i=new Intent(getActivity(), MyInteresting.class);
                    i.putExtra("jsonData", jsonData);
                    startActivity(i);
                }
            }
        });
    }
}

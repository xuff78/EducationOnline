package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMLocationMessage;
import com.avoscloud.leanchatlib.activity.ChatActivity;
import com.avoscloud.leanchatlib.controller.MessageHelper;
import com.avoscloud.leanchatlib.event.ImageItemClickEvent;
import com.avoscloud.leanchatlib.event.InputBottomBarLocationClickEvent;
import com.avoscloud.leanchatlib.event.LocationItemClickEvent;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.education.online.R;
import com.education.online.act.discovery.ChatSetting;
import com.education.online.util.Constant;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/11/17.
 */
public class CM_MessageChatAct extends ChatActivity {
    public static final int LOCATION_REQUEST = 100;
    public static final int QUIT_GROUP_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        findViewById(R.id.back_imagebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.back_home_imagebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CM_MessageChatAct.this, ChatSetting.class);
                startActivity(intent);
            }
        });

        TextView title= (TextView) findViewById(R.id.header_title_tv);
        title.setText(getIntent().getStringExtra("Name"));

        LeanchatUser user = (LeanchatUser) AVUser.getCurrentUser();
        String avatar= (String) user.get("avatar");
        if(avatar==null)
            avatar="";
        String username= (String) user.get("username");
        setNameAndAvatar(username,avatar);

    }

    String titleName="";
    public void setHeaderTitle(String name){
        titleName=name;
    }

    @Override
    protected void onResume() {
        NotificationUtils.cancelNotification(this);
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOCATION_REQUEST:
                    final double latitude = intent.getDoubleExtra(Constant.Lat, 0);
                    final double longitude = intent.getDoubleExtra(Constant.Lon, 0);
                    final String address = intent.getStringExtra(Constant.Addr);
                    if (!TextUtils.isEmpty(address)) {
                        AVIMLocationMessage locationMsg = new AVIMLocationMessage();
                        locationMsg.setLocation(new AVGeoPoint(latitude, longitude));
                        locationMsg.setText(address);
                        sendMessage(locationMsg);
                    } else {
                        showToast("未能获得地理位置");
                    }
                    break;
                case QUIT_GROUP_REQUEST:
                    finish();
                    break;
            }
        }
    }

    public void onEvent(InputBottomBarLocationClickEvent event) {
//        CM_MessageLocationActivity.startToSelectLocationForResult(this, LOCATION_REQUEST);
        Intent intent=new Intent(this, CM_TargetLocationAct.class);
        intent.putExtra(CM_TargetLocationAct.LocationSelection, true);
        startActivityForResult(intent, LOCATION_REQUEST);
    }

    public void onEvent(LocationItemClickEvent event) {
        if (null != event && null != event.message && event.message instanceof AVIMLocationMessage) {
            AVIMLocationMessage locationMessage = (AVIMLocationMessage) event.message;
//            CM_MessageLocationActivity.startToSeeLocationDetail(this, locationMessage.getLocation().getLatitude(),
//                    locationMessage.getLocation().getLongitude());
            Intent intent=new Intent(this, CM_TargetLocationAct.class);
            intent.putExtra(Constant.Lat, locationMessage.getLocation().getLatitude());
            intent.putExtra(Constant.Lon, locationMessage.getLocation().getLongitude());
            startActivity(intent);
        }
    }

    public void onEvent(ImageItemClickEvent event) {
        if (null != event && null != event.message && event.message instanceof AVIMImageMessage) {
            AVIMImageMessage imageMessage = (AVIMImageMessage)event.message;
            ArrayList<String> imgs=new ArrayList<>();
            String uri= MessageHelper.getFilePath(imageMessage);
            if(imageMessage.getFileUrl()!=null&&imageMessage.getFileUrl().length()>0)
                imgs.add(imageMessage.getFileUrl());
            else if(uri!=null&&uri.length()>0)
                imgs.add(uri);
            else
                return;
            Intent i = new Intent(CM_MessageChatAct.this, TouchImageView.class);
            i.putExtra("image", imgs.get(0));
            i.putExtra("pos", 0);
            startActivity(i);
//            ImageBrowserActivity.go(this, MessageHelper.getFilePath(imageMessage), imageMessage.getFileUrl());
        }
    }

    public void KeyBoardCancle() {

        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

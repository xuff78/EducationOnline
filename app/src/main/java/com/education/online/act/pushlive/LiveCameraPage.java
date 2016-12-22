package com.education.online.act.pushlive;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avoscloud.leanchatlib.activity.AVBaseActivity;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.controller.MessageAgent;
import com.avoscloud.leanchatlib.event.ImTypeMessageEvent;
import com.avoscloud.leanchatlib.event.ImTypeMessageResendEvent;
import com.avoscloud.leanchatlib.event.InputBottomBarEvent;
import com.avoscloud.leanchatlib.event.InputBottomBarRecordEvent;
import com.avoscloud.leanchatlib.event.InputBottomBarTextEvent;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.avoscloud.leanchatlib.xlist.XListView;
import com.education.online.R;
import com.education.online.adapter.LiveChatAdapter;
import com.education.online.bean.CourseDetailBean;
import com.education.online.util.SharedPreferencesUtil;
import com.upyun.hardware.Config;
import com.upyun.hardware.PushClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class LiveCameraPage extends AVBaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private SurfaceRenderView surface;
    private PushClient mClient;
    private ImageView mBtToggle;
    private ImageView mBtSetting;
    private ImageView mBtconvert;
    private ImageView mImgFlash;
    private Config config;
    private String mNotifyMsg;
    private XListView pulltorefresh;
    protected LiveChatAdapter itemAdapter;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 101;
//    protected AVIMConversation conversation;
    protected AVIMConversation imConversation;
    protected MessageAgent messageAgent;
    private TextView liveInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.live_camera);

        initView();
        CourseDetailBean courseBean= (CourseDetailBean) getIntent().getSerializableExtra(CourseDetailBean.Name);
        mClient = new PushClient(surface);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                mNotifyMsg = ex.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, mNotifyMsg);
                        mClient.stopPush();
                        mBtToggle.setAlpha(255);
                    }
                });
            }
        });
        // check permission for 6.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        surface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mClient != null) {
                        //mClient.focusOnTouch();
                    }
                }
                return true;
            }
        });

        config = new Config.Builder().build();
        config.url=config.url+courseBean.getCourse_id();

        initByIntent(getIntent());
    }

    private void initView() {
        liveInfo = (TextView) findViewById(R.id.liveInfo);
        surface = (SurfaceRenderView) findViewById(R.id.sv_camera);
        mBtToggle = (ImageView) findViewById(R.id.bt_toggle);
        mBtSetting = (ImageView) findViewById(R.id.bt_setting);
        mBtconvert = (ImageView) findViewById(R.id.btn_camera_switch);
        mImgFlash = (ImageView) findViewById(R.id.btn_flashlight_switch);
        mBtToggle.setOnClickListener(this);
        mBtSetting.setOnClickListener(this);
        mBtconvert.setOnClickListener(this);
        mImgFlash.setOnClickListener(this);
        mImgFlash.setEnabled(true);
        pulltorefresh = (XListView) findViewById(com.avoscloud.leanchatlib.R.id.refreshListView);
        pulltorefresh.setPullRefreshEnable(true);
        pulltorefresh.setPullLoadEnable(false);
        pulltorefresh.setShowTrans(true);
        itemAdapter = new LiveChatAdapter(this);
        pulltorefresh.setAdapter(itemAdapter);
        findViewById(R.id.chatLayoutController).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (config != null) {
            mClient.setConfig(config);
        }
        changeSurfaceSize(surface, mClient.getConfig());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_toggle:
                if (mClient.isStart()) {
                    mClient.stopPush();
                    Log.e(TAG, "stop");
                    mBtToggle.setAlpha(255);
                } else {
                    try {
                        mClient.startPush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                        return;
                    }
                    Log.e(TAG, "start");
                    mBtToggle.setAlpha(155);
                }
                break;
            case R.id.bt_setting:
                mClient.stopPush();
                mBtToggle.setAlpha(255);
//                startActivity(new Intent(this, SettingActivity.class));
                break;

            case R.id.btn_camera_switch:
                boolean converted = mClient.covertCamera();
                if (converted) {
                    mImgFlash.setEnabled(!mImgFlash.isEnabled());
                }
                break;

            case R.id.btn_flashlight_switch:
                mClient.toggleFlashlight();
                break;
            case R.id.chatLayoutController:
                if(pulltorefresh.isShown()){
                    pulltorefresh.setVisibility(View.GONE);
                }else{
                    pulltorefresh.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void changeSurfaceSize(SurfaceRenderView surface, Config config) {
        int width = 1280;
        int height = 720;

        switch (config.resolution) {
            case HIGH:
                width = 1280;
                height = 720;
                break;
            case NORMAL:
                width = 640;
                height = 480;
                break;
            case LOW:
                width = 320;
                height = 240;
                break;
        }

//        ViewGroup.LayoutParams lp = surface.getLayoutParams();
//
//        lp.width = height;
//        lp.height = width;
//        surface.setLayoutParams(lp);
        surface.setVideoSize(height, width);
    }

    // check permission
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "granted camera permission");
            } else {
                Log.e(TAG, "no camera permission");
            }
        } else if (requestCode == REQUEST_CODE_PERMISSION_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "granted record audio permission");
            } else {
                Log.d(TAG, "no record audio permission");
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initByIntent(intent);
    }

    private void initByIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (null != extras) {
            if (extras.containsKey(Constants.MEMBER_ID)) {
                getConversation(extras.getString(Constants.MEMBER_ID));
            } else if (extras.containsKey(Constants.CONVERSATION_ID)) {
                String conversationId = extras.getString(Constants.CONVERSATION_ID);
                updateConversation(AVIMClient.getInstance(ChatManager.getInstance().getSelfId()).getConversation(conversationId));
            } else {}
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationUtils.cancelNotification(this);
        if (null != imConversation) {
            NotificationUtils.addTag(imConversation.getConversationId());
        }
        pulltorefresh.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                AVIMMessage message = itemAdapter.getFirstMessage();
                if (null == message) {
                    pulltorefresh.stopRefresh();
                    Toast.makeText(LiveCameraPage.this, "已加载完成", 500).show();
                } else {
                    imConversation.queryMessages(message.getMessageId(), message.getTimestamp(), 20, new AVIMMessagesQueryCallback() {
                        @Override
                        public void done(final List<AVIMMessage> list, AVIMException e) {
                            pulltorefresh.stopRefresh();
                            if (filterException(e)) {
                                if (null != list && list.size() > 0) {
                                    itemAdapter.addMessageList(list);
                                    itemAdapter.notifyDataSetChanged();
                                    pulltorefresh.setSelection(list.size()-1);
                                }else{
                                    Toast.makeText(LiveCameraPage.this, "已加载完成", 500).show();
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void onLoadMore() {

            }
        });
    }

    protected void updateConversation(AVIMConversation conversation) {
        if (null != conversation) {
//            this.conversation = conversation;
            setConversation(conversation);
            showUserName(ConversationHelper.typeOfConversation(conversation) != ConversationType.Single);
//      initActionBar(ConversationHelper.titleOfConversation(conversation));
            setHeaderTitle(ConversationHelper.titleOfConversation(conversation));
        }
    }

    public void  setHeaderTitle(String name){};

    /**
     * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
     * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
     */
    private void getConversation(final String memberId) {
        ChatManager.getInstance().fetchConversationWithUserId(memberId, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation conversation, AVIMException e) {
                if (filterException(e)) {
                    ChatManager.getInstance().getRoomsTable().insertRoom(conversation.getConversationId());
                    updateConversation(conversation);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != imConversation) {
            NotificationUtils.removeTag(imConversation.getConversationId());
        }
    }

    @Override
    protected void onDestroy() {
        if (mClient != null) {
            mClient.stopPush();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void setConversation(AVIMConversation conversation) {
        imConversation = conversation;
        pulltorefresh.setEnabled(true);
        liveInfo.setText(conversation.getMembers().size()+"人在观看");
        fetchMessages();
        NotificationUtils.addTag(conversation.getConversationId());
        messageAgent = new MessageAgent(conversation);
    }

    public void showUserName(boolean isShow) {
        itemAdapter.showUserName(isShow);
    }

    /**
     * 拉取消息，必须加入 conversation 后才能拉取消息
     */
    private void fetchMessages() {
        imConversation.queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (filterException(e)) {
                    itemAdapter.setMessageList(list);
                    pulltorefresh.setAdapter(itemAdapter);
//                    itemAdapter.notifyDataSetChanged();
                    scrollToBottom();
                }
            }
        });
    }

    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    public void onEvent(ImTypeMessageEvent event) {
        if (null != imConversation && null != event &&
                imConversation.getConversationId().equals(event.conversation.getConversationId())) {
            itemAdapter.addMessage(event.message);
            itemAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 重新发送已经发送失败的消息
     */
    public void onEvent(ImTypeMessageResendEvent event) {
        if (null != imConversation && null != event &&
                null != event.message &&  imConversation.getConversationId().equals(event.message.getConversationId())) {
            if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == event.message.getMessageStatus()
                    && imConversation.getConversationId().equals(event.message.getConversationId())) {
                imConversation.sendMessage(event.message, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        itemAdapter.notifyDataSetChanged();
                    }
                });
                itemAdapter.notifyDataSetChanged();
            }
        }
    }

    private void scrollToBottom() {
        pulltorefresh.setSelection(itemAdapter.getCount());
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
//            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    protected void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}


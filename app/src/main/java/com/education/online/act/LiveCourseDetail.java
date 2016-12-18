package com.education.online.act;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.avoscloud.leanchatlib.activity.AVBaseActivity;
import com.avoscloud.leanchatlib.activity.ChatActivity;
import com.avoscloud.leanchatlib.activity.InputBottomBar;
import com.avoscloud.leanchatlib.adapter.ChatAdapter;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.controller.MessageAgent;
import com.avoscloud.leanchatlib.event.EmptyEvent;
import com.avoscloud.leanchatlib.event.ImTypeMessageEvent;
import com.avoscloud.leanchatlib.event.ImTypeMessageResendEvent;
import com.avoscloud.leanchatlib.event.InputBottomBarEvent;
import com.avoscloud.leanchatlib.event.InputBottomBarRecordEvent;
import com.avoscloud.leanchatlib.event.InputBottomBarTextEvent;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.avoscloud.leanchatlib.utils.PathUtils;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.avoscloud.leanchatlib.utils.ProviderPathUtils;
import com.avoscloud.leanchatlib.utils.Utils;
import com.avoscloud.leanchatlib.xlist.XListView;
import com.education.online.R;
import com.education.online.bean.CourseDetailBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.upyun.upplayer.widget.UpVideoView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/12/2.
 */
public class LiveCourseDetail extends AVBaseActivity implements InputBottomBar.EditorListener{

    //  protected ChatFragment chatFragment;
    protected AVIMConversation conversation;
    private static final int TAKE_CAMERA_REQUEST = 2;
    private static final int GALLERY_REQUEST = 0;
    private static final int GALLERY_KITKAT_REQUEST = 3;

    protected AVIMConversation imConversation;
    protected MessageAgent messageAgent;

    private UpVideoView videoView;
    private View videoBottomLayout;
    String LiveCourseUrl="rtmp://pull.live.iqkui.com/live/";
    protected ChatAdapter itemAdapter;
    protected InputBottomBar inputBottomBar;

    protected String localCameraPath = PathUtils.getPicturePathByCurrentTime();
    private XListView pulltorefresh;
    private View chatListLayout;
    private Map<String, Object> attr=new HashMap<>();
    private int chatListFullscreenWidth=0;
    private int videoHeight=0, videoWidth=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_detail_layout);
        initView();
        initByIntent(getIntent());
    }

    public void setNameAndAvatar(String nickname, String avatar){
        attr.put("avatar", avatar);
        attr.put("nickname", nickname);
    }

    public void initView() {

        videoWidth = ScreenUtil.getWidth(this);
        videoHeight = (int) (((float) videoWidth) * 9 / 16);
        chatListFullscreenWidth = ImageUtil.dip2px(this, 240);
        CourseDetailBean bean= (CourseDetailBean) getIntent().getSerializableExtra(CourseDetailBean.Name);
        videoView= (UpVideoView) findViewById(R.id.upVideoView);
        RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(-1, videoHeight);
        videoView.setLayoutParams(rlp);
        videoView.setVideoPath(LiveCourseUrl+bean.getCourse_id());
        MediaController controller=new MediaController(this);
        videoView.setMediaController(controller);
        videoView.start();
        controller.show();

        videoBottomLayout=findViewById(R.id.videoBottomLayout);
        chatListLayout=findViewById(R.id.chatListLayout);
        pulltorefresh = (XListView) findViewById(com.avoscloud.leanchatlib.R.id.refreshListView);
        pulltorefresh.setPullRefreshEnable(true);
        pulltorefresh.setPullLoadEnable(false);
        inputBottomBar = (InputBottomBar) findViewById(com.avoscloud.leanchatlib.R.id.fragment_chat_inputbottombar);
        inputBottomBar.setEditlistener(this);
        itemAdapter = new ChatAdapter(this);
        pulltorefresh.setAdapter(itemAdapter);

        String titleTxt=getIntent().getStringExtra("Name");
        TextView title= (TextView) findViewById(com.avoscloud.leanchatlib.R.id.header_title_tv);
        if(titleTxt!=null&&titleTxt.length()>0)
            title.setText(titleTxt);
        else
            title.setText("聊天");

        LeanchatUser user = (LeanchatUser) AVUser.getCurrentUser();
        String avatar= (String) user.get("avatar");
        if(avatar==null)
            avatar="";
        String username= (String) user.get("username");
        setNameAndAvatar(username,avatar);
        findViewById(R.id.back_imagebtn).setOnClickListener(listener);
        findViewById(R.id.back_home_imagebtn).setOnClickListener(listener);
        findViewById(R.id.softPanBtn).setOnClickListener(listener);
        findViewById(R.id.chatLayoutController).setOnClickListener(listener);
    }

    View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back_imagebtn:
                    onBackPressed();
                    break;
                case R.id.back_home_imagebtn:
                    videoView.fullScreen(LiveCourseDetail.this);
                    break;
                case R.id.softPanBtn:

                    break;
                case R.id.chatLayoutController:
                    if(chatListLayout.isShown()){
                        chatListLayout.setVisibility(View.GONE);
                    }else{
                        chatListLayout.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            videoBottomLayout.setVisibility(View.VISIBLE);
            inputBottomBar.setVisibility(View.GONE);
            videoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(chatListFullscreenWidth, -1);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rlp.addRule(RelativeLayout.BELOW, R.id.app_header_layout_value);
            rlp.addRule(RelativeLayout.ABOVE, R.id.videoBottomLayout);
            chatListLayout.setLayoutParams(rlp);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            videoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, videoHeight));
            videoBottomLayout.setVisibility(View.GONE);
            inputBottomBar.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(-1, -1);
            rlp.addRule(RelativeLayout.BELOW, R.id.upVideoView);
            rlp.addRule(RelativeLayout.ABOVE, R.id.fragment_chat_inputbottombar);
            chatListLayout.setLayoutParams(rlp);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if (videoView.isFullState()) {
            //退出全屏
            videoView.exitFullScreen(this);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.release(true);
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
                    Toast.makeText(LiveCourseDetail.this, "已加载完成", 500).show();
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
                                    Toast.makeText(LiveCourseDetail.this, "已加载完成", 500).show();
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

    protected void initActionBar(String title) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            if (title != null) {
                actionBar.setTitle(title);
            }
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            LogUtils.i("action bar is null, so no title, please set an ActionBar style for activity");
        }
    }

    public void onEvent(EmptyEvent emptyEvent) {}

    protected void updateConversation(AVIMConversation conversation) {
        if (null != conversation) {
            this.conversation = conversation;
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void setConversation(AVIMConversation conversation) {
        imConversation = conversation;
        pulltorefresh.setEnabled(true);
        inputBottomBar.setTag(imConversation.getConversationId());
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
     * 输入事件处理，接收后构造成 AVIMTextMessage 然后发送
     * 因为不排除某些特殊情况会受到其他页面过来的无效消息，所以此处加了 tag 判断
     */
    public void onEvent(InputBottomBarTextEvent textEvent) {
        if (null != imConversation && null != textEvent) {
            if (!TextUtils.isEmpty(textEvent.sendContent) && imConversation.getConversationId().equals(textEvent.tag)) {
                sendText(textEvent.sendContent);
            }
        }
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

    public void onEvent(InputBottomBarEvent event) {
        if (null != imConversation && null != event && imConversation.getConversationId().equals(event.tag)) {
            switch (event.eventAction) {
                case InputBottomBarEvent.INPUTBOTTOMBAR_IMAGE_ACTION:
                    selectImageFromLocal();
                    break;
                case InputBottomBarEvent.INPUTBOTTOMBAR_CAMERA_ACTION:
                    selectImageFromCamera();
                    break;
            }
        }
    }

    public void onEvent(InputBottomBarRecordEvent recordEvent) {
        if (null != imConversation && null != recordEvent &&
                !TextUtils.isEmpty(recordEvent.audioPath) &&
                imConversation.getConversationId().equals(recordEvent.tag)) {
            sendAudio(recordEvent.audioPath);
        }
    }

    public void selectImageFromLocal() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getResources().getString(com.avoscloud.leanchatlib.R.string.chat_activity_select_picture)),
                    GALLERY_REQUEST);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_KITKAT_REQUEST);
        }
    }

    public void selectImageFromCamera() {
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(localCameraPath));
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKE_CAMERA_REQUEST);
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST:
                case GALLERY_KITKAT_REQUEST:
                    if (data == null) {
//                        toast("return intent is null");
                        return;
                    }
                    Uri uri;
                    if (requestCode == GALLERY_REQUEST) {
                        uri = data.getData();
                    } else {
                        //for Android 4.4
                        uri = data.getData();
                        final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        this.getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    }
                    String localSelectPath = ProviderPathUtils.getPath(this, uri);
                    inputBottomBar.hideMoreLayout();
                    sendImage(localSelectPath);
                    break;
                case TAKE_CAMERA_REQUEST:
                    try {
                        inputBottomBar.hideMoreLayout();
                        sendImage(localCameraPath);
                    }catch (Exception e){
                        Toast.makeText(this, "获取图片失败，请检查存储卡", 500).show();
                    }
                    break;
            }
        }
    }

    //TODO messageAgent
    private void sendText(String content) {
        AVIMTextMessage message = new AVIMTextMessage();
        message.setText(content);
        message.setAttrs(attr);
        sendMessage(message);
    }

    private void sendImage(String imagePath) {
        AVIMImageMessage imageMsg = null;
        final String newPath = PathUtils.getChatFilePath(Utils.uuid());
        PhotoUtils.compressImage(imagePath, newPath, this);
        try {
            imageMsg = new AVIMImageMessage(newPath);
            imageMsg.setAttrs(attr);
            sendMessage(imageMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAudio(String audioPath) {
        try {
            AVIMAudioMessage audioMessage = new AVIMAudioMessage(audioPath);
            audioMessage.setAttrs(attr);
            sendMessage(audioMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(AVIMTypedMessage message) {
        itemAdapter.addMessage(message);
        itemAdapter.notifyDataSetChanged();
        scrollToBottom();
        imConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                itemAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onEditorOpen() {
        scrollToBottom();
    }
}

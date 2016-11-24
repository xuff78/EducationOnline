package com.avoscloud.leanchatlib.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.avoscloud.leanchatlib.R;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.adapter.MultipleItemAdapter;
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
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.avoscloud.leanchatlib.utils.PathUtils;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.avoscloud.leanchatlib.utils.ProviderPathUtils;
import com.avoscloud.leanchatlib.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/9/18.
 */
public class AVChatActivity extends AVBaseActivity {

//  protected ChatFragment chatFragment;
  protected AVIMConversation conversation;
  private static final int TAKE_CAMERA_REQUEST = 2;
  private static final int GALLERY_REQUEST = 0;
  private static final int GALLERY_KITKAT_REQUEST = 3;

  protected AVIMConversation imConversation;
  protected MessageAgent messageAgent;

  protected MultipleItemAdapter itemAdapter;
  protected RecyclerView recyclerView;
  protected LinearLayoutManager layoutManager;
  protected SwipeRefreshLayout refreshLayout;
  protected InputBottomBar inputBottomBar;

  protected String localCameraPath = PathUtils.getPicturePathByCurrentTime();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
//    chatFragment = (ChatFragment)getFragmentManager().findFragmentById(R.id.fragment_chat);
    initView();
    initByIntent(getIntent());
  }

  public void initView() {
    recyclerView = (RecyclerView) findViewById(R.id.fragment_chat_rv_chat);
    refreshLayout = (SwipeRefreshLayout)findViewById(R.id.fragment_chat_srl_pullrefresh);
    refreshLayout.setEnabled(false);
    inputBottomBar = (InputBottomBar) findViewById(R.id.fragment_chat_inputbottombar);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    itemAdapter = new MultipleItemAdapter();
    recyclerView.setAdapter(itemAdapter);

  }

  @Override
  protected void onResume() {
    super.onResume();
    if (null != imConversation) {
      NotificationUtils.addTag(imConversation.getConversationId());
    }
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        AVIMMessage message = itemAdapter.getFirstMessage();
        if (null == message) {
          refreshLayout.setRefreshing(false);
        } else {
          imConversation.queryMessages(message.getMessageId(), message.getTimestamp(), 20, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
              refreshLayout.setRefreshing(false);
              if (filterException(e)) {
                if (null != list && list.size() > 0) {
                  itemAdapter.addMessageList(list);
                  itemAdapter.notifyDataSetChanged();

                  layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
                }
              }
            }
          });
        }
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

  public void setHeaderTitle(String name){};

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
    super.onResume();
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
    refreshLayout.setEnabled(true);
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
          recyclerView.setAdapter(itemAdapter);
          itemAdapter.notifyDataSetChanged();
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
      scrollToBottom();
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

//TODO
//  public void onEvent(MessageEvent messageEvent) {
//    final AVIMTypedMessage message = messageEvent.getMessage();
//    if (message.getConversationId().equals(conversation
//      .getConversationId())) {
//      if (messageEvent.getType() == MessageEvent.Type.Come) {
//        new CacheMessagesTask(this, Arrays.asList(message)) {
//          @Override
//          void onPostRun(List<AVIMTypedMessage> messages, Exception e) {
//            if (filterException(e)) {
//              addMessageAndScroll(message);
//            }
//          }
//        }.execute();
//      } else if (messageEvent.getType() == MessageEvent.Type.Receipt) {
//        //Utils.i("receipt");
//        AVIMTypedMessage originMessage = findMessage(message.getMessageId());
//        if (originMessage != null) {
//          originMessage.setMessageStatus(message.getMessageStatus());
//          originMessage.setReceiptTimestamp(message.getReceiptTimestamp());
//          adapter.notifyDataSetChanged();
//        }
//      }
//    }
//  }

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
      startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.chat_activity_select_picture)),
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
    layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
  }

  protected boolean filterException(Exception e) {
    if (e != null) {
      e.printStackTrace();
//      toast(e.getMessage());
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
            toast("return intent is null");
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
    sendMessage(message);
  }

  private void sendImage(String imagePath) {
    AVIMImageMessage imageMsg = null;
    final String newPath = PathUtils.getChatFilePath(Utils.uuid());
    PhotoUtils.compressImage(imagePath, newPath, this);
    try {
      imageMsg = new AVIMImageMessage(newPath);
      sendMessage(imageMsg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void sendAudio(String audioPath) {
    try {
      AVIMAudioMessage audioMessage = new AVIMAudioMessage(audioPath);
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
}
package com.avoscloud.leanchatlib.controller;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avoscloud.leanchatlib.event.ImTypeMessageEvent;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.avoscloud.leanchatlib.utils.NotificationUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zhangxiaobo on 15/4/20.
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

  private Context context;

  public MessageHandler(Context context) {

    this.context = context;
  }

  @Override
  public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    if (message == null || message.getMessageId() == null) {
      LogUtils.d("may be SDK Bug, message or message id is null");
      return;
    }

    if (!ConversationHelper.isValidConversation(conversation)) {
      LogUtils.d("receive msg from invalid conversation");
    }

    if (ChatManager.getInstance().getSelfId() == null) {
      LogUtils.d("selfId is null, please call setupManagerWithUserId ");
      client.close(null);
    } else {
      if (!client.getClientId().equals(ChatManager.getInstance().getSelfId())) {
        client.close(null);
      } else {
        ChatManager.getInstance().getRoomsTable().insertRoom(message.getConversationId());
        if (!message.getFrom().equals(client.getClientId())) {
          if (NotificationUtils.isShowNotification(conversation.getConversationId())) {
            sendNotification(message, conversation);
          }
          ChatManager.getInstance().getRoomsTable().increaseUnreadCount(message.getConversationId());
        }
        sendEvent(message, conversation);
      }
    }
  }

  @Override
  public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    super.onMessageReceipt(message, conversation, client);
  }

  /**
   * 因为没有 db，所以暂时先把消息广播出去，由接收方自己处理
   * 稍后应该加入 db
   * @param message
   * @param conversation
   */
  private void sendEvent(AVIMTypedMessage message, AVIMConversation conversation) {
    ImTypeMessageEvent event = new ImTypeMessageEvent();
    event.message = message;
    event.conversation = conversation;
    EventBus.getDefault().post(event);
  }

  private void sendNotification(AVIMTypedMessage message, AVIMConversation conversation) {
    if (null != conversation && null != message) {

      AVUser user = AVUserCacheUtils.getCachedUser(message.getFrom());
      String notificationContent = null != user ? (String)user.get("username") : "";
//              message instanceof AVIMTextMessage ?
//        ((AVIMTextMessage) message).getText() : context.getString(R.string.unspport_message_type);

      String title = ""; //(null != user ? (String)user.get("username") : "");
//      if(title.length()==0)
        title="收到新消息";

      Class mLoadClass=null;
        if(isTopActivy("com.education.online", context)) {
          try {
            mLoadClass = context.getClassLoader().loadClass("com.ih.coffee.act.CM_MessageListAct");
          } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }else{
          try {
            mLoadClass = context.getClassLoader().loadClass("com.education.online.act.FirstPage");
          } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }

      Intent intent =  new Intent(context, mLoadClass);
      intent.putExtra(Constants.CONVERSATION_ID, conversation.getConversationId());
      intent.putExtra(Constants.MEMBER_ID, message.getFrom());
//      if (ConversationHelper.typeOfConversation(conversation) == ConversationType.Single) {
//        intent.putExtra(Constants.NOTOFICATION_TAG, Constants.NOTIFICATION_SINGLE_CHAT);
//      } else {
//        intent.putExtra(Constants.NOTOFICATION_TAG, Constants.NOTIFICATION_SINGLE_CHAT);
//      }
      NotificationUtils.showNotificationForActivity(context, title, notificationContent, null, intent);
    }
  }

  public boolean isTopActivy(String cmdName, Context context){
    ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
    String cmpNameTemp = null;
    if(null != runningTaskInfos){
      cmpNameTemp=(runningTaskInfos.get(0).topActivity).toString();
    }
    if(null == cmpNameTemp)return false;
    return cmpNameTemp.contains(cmdName);
  }
}

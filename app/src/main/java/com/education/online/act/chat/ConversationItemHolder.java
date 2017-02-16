package com.education.online.act.chat;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.controller.MessageHelper;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.model.Room;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.avoscloud.leanchatlib.viewholder.CommonViewHolder;
import com.education.online.R;
import com.education.online.bean.UserInfo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;
/**
 * Created by wli on 15/10/8.
 */
public class ConversationItemHolder extends CommonViewHolder {

  ImageView recentAvatarView;
  TextView recentNameView;
  TextView recentMsgView;
  TextView recentTimeView;
  TextView recentUnreadView;
  private HttpHandler mHandler;
  private Activity act;
  private String usercode;

  public ConversationItemHolder(ViewGroup root, Activity act) {
    super(root.getContext(), root, R.layout.conversation_item);
    this.act=act;
    initView();
  }

  public void initView() {
    recentAvatarView = (ImageView)itemView.findViewById(R.id.iv_recent_avatar);
    recentNameView = (TextView)itemView.findViewById(R.id.recent_time_text);
    recentMsgView = (TextView)itemView.findViewById(R.id.recent_msg_text);
    recentTimeView = (TextView)itemView.findViewById(R.id.recent_teim_text);
    recentUnreadView = (TextView)itemView.findViewById(R.id.recent_unread);
  }


  @Override
  public void bindData(Object o) {
    final Room room = (Room) o;
    AVIMConversation conversation = room.getConversation();
    if (null != conversation) {
      if (ConversationHelper.typeOfConversation(conversation) == ConversationType.Single) {
        usercode= ConversationHelper.otherIdOfConversation(conversation);
        LeanchatUser user = AVUserCacheUtils.getCachedUser(usercode);
        if (null != user) {
          ImageLoader.getInstance().displayImage(user.getAvatarUrl(), recentAvatarView, PhotoUtils.avatarImageOptions);
          recentNameView.setText((String) user.get("username"));
        }else{
          initHandler();
          mHandler.getUserInfoNoDialog(usercode);
        }
      } else {
        recentAvatarView.setImageResource(R.mipmap.icon_group);
        recentNameView.setText(conversation.getName());
      }

      setLastMessage(room.getLastMessage());

      int num = room.getUnreadCount();
      if (num > 0) {
        recentUnreadView.setVisibility(View.VISIBLE);
        recentUnreadView.setText(num + "");
      } else {
        recentUnreadView.setVisibility(View.GONE);
      }

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ConversationItemClickEvent itemClickEvent =  new ConversationItemClickEvent();
          itemClickEvent.conversationId = room.getConversationId();
          EventBus.getDefault().post(itemClickEvent);
        }
      });
    }
  }

  private void setLastMessage(AVIMMessage msg){
    if (msg != null) {
      Date date = new Date(msg.getTimestamp());
      SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
      recentTimeView.setText(format.format(date));
      recentMsgView.setText(MessageHelper.outlineOfMsg((AVIMTypedMessage) msg));
    }
  }


  private void initHandler() {
    mHandler = new HttpHandler(act, new CallBack(act) {
      @Override
      public void doSuccess(String method, String jsonData) throws JSONException {
        super.doSuccess(method, jsonData);
        if(method.equals(Method.getUserInfo)){
          UserInfo userinfo= JSON.parseObject(jsonData, UserInfo.class);

          LeanchatUser user = AVUser.newAVUser(LeanchatUser.class, null);
          user.put("avatar", ImageUtil.getImageUrl(userinfo.getAvatar()));
          user.put("username", userinfo.getNickname());
          AVUserCacheUtils.cacheUser(usercode, user);
          recentNameView.setText(userinfo.getNickname());
          ImageLoader.getInstance().displayImage(user.getAvatarUrl(), recentAvatarView, PhotoUtils.avatarImageOptions);
        }
      }
    });
  }
}

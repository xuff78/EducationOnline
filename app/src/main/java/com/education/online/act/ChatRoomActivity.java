package com.education.online.act;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.education.online.R;
import com.education.online.leanchat.utils.ConversationUtils;

import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;

/**
 * Created by lzw on 15/4/24.
 */
public class ChatRoomActivity extends LCIMConversationActivity {

  private AVIMConversation conversation;

  public static final int QUIT_GROUP_REQUEST = 200;

  @Override
  protected void onResume() {
    super.onResume();
  }


  @Override
  protected void updateConversation(AVIMConversation conversation) {
    super.updateConversation(conversation);
    this.conversation = conversation;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case QUIT_GROUP_REQUEST:
          finish();
          break;
      }
    }
  }

  @Override
  protected void getConversation(String memberId) {
    super.getConversation(memberId);
    ConversationUtils.createSingleConversation(memberId, new AVIMConversationCreatedCallback() {
      @Override
      public void done(AVIMConversation avimConversation, AVIMException e) {
        updateConversation(avimConversation);
      }
    });
  }
}
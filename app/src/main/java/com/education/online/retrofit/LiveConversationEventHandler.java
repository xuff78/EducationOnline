package com.education.online.retrofit;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;
import com.education.online.bean.ConversationEvent;
import com.education.online.util.LogUtil;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/2/15.
 */

public class LiveConversationEventHandler extends AVIMConversationEventHandler {


    @Override
    public void onMemberLeft(AVIMClient avimClient, AVIMConversation avimConversation, List<String> list, String s) {

        ConversationEvent event=new ConversationEvent(list, ConversationEvent.EventType.add, avimConversation);
        EventBus.getDefault().post(event);
        LogUtil.i("live", "member left");
    }

    @Override
    public void onMemberJoined(AVIMClient avimClient, AVIMConversation avimConversation, List<String> list, String s) {
        ConversationEvent event=new ConversationEvent(list, ConversationEvent.EventType.left, avimConversation);
        EventBus.getDefault().post(event);
        LogUtil.i("live", "member add");
    }

    @Override
    public void onKicked(AVIMClient avimClient, AVIMConversation avimConversation, String s) {

    }

    @Override
    public void onInvited(AVIMClient avimClient, AVIMConversation avimConversation, String s) {

    }
}

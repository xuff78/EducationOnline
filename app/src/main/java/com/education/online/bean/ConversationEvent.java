package com.education.online.bean;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.Conversation;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class ConversationEvent {

    public enum EventType {
         add, left
    }

    private List<String> memberName;
    private EventType type;
    private AVIMConversation conversation;

    public ConversationEvent(List<String> memberName, EventType type, AVIMConversation conversation) {
        this.memberName = memberName;
        this.type = type;
        this.conversation = conversation;
    }

    public List<String> getMemberName() {
        return memberName;
    }

    public void setMemberName(List<String> memberName) {
        this.memberName = memberName;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public AVIMConversation getConversation() {
        return conversation;
    }

    public void setConversation(AVIMConversation conversation) {
        this.conversation = conversation;
    }
}

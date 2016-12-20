package com.education.online.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avoscloud.leanchatlib.controller.MessageHelper;
import com.avoscloud.leanchatlib.viewholder.ChatItemAudioHolder;
import com.avoscloud.leanchatlib.viewholder.ChatItemHolder;
import com.avoscloud.leanchatlib.viewholder.ChatItemImageHolder;
import com.avoscloud.leanchatlib.viewholder.ChatItemLocationHolder;
import com.avoscloud.leanchatlib.viewholder.ChatItemTextHolder;
import com.avoscloud.leanchatlib.viewholder.CommonViewHolder;
import com.education.online.view.LiveChatItemHolder;
import com.education.online.view.LiveChatTxtItemHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */
public class LiveChatAdapter extends BaseAdapter {


    private final int ITEM_LEFT = 100;
    private final int ITEM_LEFT_TEXT = 101;
    private final int ITEM_LEFT_IMAGE = 102;
    private final int ITEM_LEFT_AUDIO = 103;
    private final int ITEM_LEFT_LOCATION = 104;

    private final int ITEM_RIGHT = 200;
    private final int ITEM_RIGHT_TEXT = 201;
    private final int ITEM_RIGHT_IMAGE = 202;
    private final int ITEM_RIGHT_AUDIO = 203;
    private final int ITEM_RIGHT_LOCATION = 204;

    // 时间间隔最小为十分钟
    private final static long TIME_INTERVAL = 1000 * 60 * 3;
    private boolean isShowUserName = true;

    private List<AVIMMessage> messageList = new ArrayList<>();
    private Context con;
    public LiveChatAdapter(Context con) {this.con=con;}

    public void setMessageList(List<AVIMMessage> messages) {
        messageList.clear();
        if (null != messages) {
            messageList.addAll(messages);
        }
    }

    public void addMessageList(List<AVIMMessage> messages) {
        messageList.addAll(0, messages);
    }

    public void addMessage(AVIMMessage message) {
        messageList.addAll(Arrays.asList(message));
    }

    public AVIMMessage getFirstMessage() {
        if (null != messageList && messageList.size() > 0) {
            return messageList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        RecyclerView.ViewHolder holder=null;
        switch (getItemViewType(position)) {
            case ITEM_LEFT_TEXT:
                holder = new LiveChatItemHolder(con, null, true);
                break;
            case ITEM_LEFT_IMAGE:
                holder = new ChatItemImageHolder(con, null, true);
                break;
            case ITEM_LEFT_AUDIO:
                holder = new ChatItemAudioHolder(con, null, true);
                break;
            case ITEM_LEFT_LOCATION:
                holder = new ChatItemLocationHolder(con, null, true);
                break;
            case ITEM_RIGHT_TEXT:
                holder = new LiveChatItemHolder(con, null, false);
                break;
            case ITEM_RIGHT_IMAGE:
                holder = new ChatItemImageHolder(con, null, false);
                break;
            case ITEM_RIGHT_AUDIO:
                holder = new ChatItemAudioHolder(con, null, false);
                break;
            case ITEM_RIGHT_LOCATION:
                holder = new ChatItemLocationHolder(con, null, false);
                break;
            default:
                holder = new ChatItemTextHolder(con, null, true);
                break;
        }
        ((CommonViewHolder) holder).bindData(messageList.get(position));
        if (holder instanceof ChatItemHolder) {
            ((ChatItemHolder)holder).showTimeView(false);//shouldShowTime(position));
            ((ChatItemHolder)holder).showUserName(isShowUserName);
        }
        return holder.itemView;
    }

    public int getItemViewType(int position) {
        //TODO 如果是自定义的数据类型该如何
        AVIMMessage message = messageList.get(position);
        if (null != message && message instanceof AVIMTypedMessage) {
            AVIMTypedMessage typedMessage = (AVIMTypedMessage) message;
            boolean isMe = MessageHelper.fromMe(typedMessage);
            if (typedMessage.getMessageType() == AVIMReservedMessageType.TextMessageType.getType()) {
                return isMe ? ITEM_RIGHT_TEXT : ITEM_LEFT_TEXT;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.AudioMessageType.getType()) {
                return isMe ? ITEM_RIGHT_AUDIO : ITEM_LEFT_AUDIO;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.ImageMessageType.getType()) {
                return isMe ? ITEM_RIGHT_IMAGE : ITEM_LEFT_IMAGE;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.LocationMessageType.getType()) {
                return isMe ? ITEM_RIGHT_LOCATION : ITEM_LEFT_LOCATION;
            } else {
                return isMe ? ITEM_RIGHT : ITEM_LEFT;
            }
        }
        return 8888;
    }

    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = messageList.get(position - 1).getTimestamp();
        long curTime = messageList.get(position).getTimestamp();
        return curTime - lastTime > TIME_INTERVAL;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void showUserName(boolean isShow) {
        isShowUserName = isShow;
    }
}

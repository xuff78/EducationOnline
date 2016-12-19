package com.education.online.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.event.ImTypeMessageResendEvent;
import com.avoscloud.leanchatlib.event.LeftChatItemClickEvent;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.avoscloud.leanchatlib.utils.Utils;
import com.avoscloud.leanchatlib.viewholder.CommonViewHolder;
import com.education.online.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/12/19.
 */
public class LiveChatItemHolder extends CommonViewHolder {

    protected boolean isLeft;

    protected AVIMMessage message;
    protected ImageView avatarView;
    protected TextView timeView;
    protected TextView nameView;
    protected LinearLayout conventLayout;
    protected FrameLayout statusLayout;
    protected ProgressBar progressBar;
    protected TextView statusView;
    protected ImageView errorView;

    public LiveChatItemHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, R.layout.chat_item_left);
        this.isLeft = isLeft;
        initView();
    }

    public void initView() {
        avatarView = (ImageView)itemView.findViewById(R.id.chat_left_iv_avatar);
        timeView = (TextView)itemView.findViewById(R.id.chat_left_tv_time);
        nameView = (TextView)itemView.findViewById(R.id.chat_left_tv_name);
        conventLayout = (LinearLayout)itemView.findViewById(R.id.chat_left_layout_content);
        statusLayout = (FrameLayout)itemView.findViewById(R.id.chat_left_layout_status);
        statusView = (TextView)itemView.findViewById(R.id.chat_left_tv_status);
        progressBar = (ProgressBar)itemView.findViewById(R.id.chat_left_progressbar);
        errorView = (ImageView)itemView.findViewById(R.id.chat_left_tv_error);
    }

    @Override
    public void bindData(Object o) {
        message = (AVIMMessage)o;
//        long gap = System.currentTimeMillis() - message.getTimestamp();
//        timeView.setText(Utils.millisecsToDateString(message.getTimestamp()));
        timeView.setVisibility(View.GONE);

        LeanchatUser user = AVUserCacheUtils.getCachedUser(message.getFrom());
        if (null != user) {
            nameView.setText((String)user.get("username"));
            ImageLoader.getInstance().displayImage(user.getAvatarUrl(), avatarView, PhotoUtils.avatarImageOptions);
        } else {
            try {
                //TODO 加载完应该回调刷新 UI
                AVUserCacheUtils.cacheUsers(Arrays.asList(message.getFrom()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        switch (message.getMessageStatus()) {
            case AVIMMessageStatusFailed:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                break;
            case AVIMMessageStatusSent:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                break;
            case AVIMMessageStatusSending:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                break;
            case AVIMMessageStatusNone:
            case AVIMMessageStatusReceipt:
                statusLayout.setVisibility(View.GONE);
                break;
        }

        ChatManager.getInstance().getRoomsTable().clearUnread(message.getConversationId());
    }

    public void onErrorClick(View view) {
        ImTypeMessageResendEvent event = new ImTypeMessageResendEvent();
        event.message = message;
        EventBus.getDefault().post(event);
    }

    public void onNameClick(View view) {
        LeftChatItemClickEvent clickEvent = new LeftChatItemClickEvent();
        clickEvent.userId = nameView.getText().toString();
        EventBus.getDefault().post(clickEvent);
    }

    public void showTimeView(boolean isShow) {
        timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void showUserName(boolean isShow) {
        nameView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}

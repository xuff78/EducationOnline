package com.education.online.act.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVConstants;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.event.ImTypeMessageEvent;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.model.Room;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.avoscloud.leanchatlib.utils.Constants;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.CM_MessageChatAct;
import com.education.online.act.SearchAct;
import com.education.online.act.chat.ConversationItemClickEvent;
import com.education.online.act.chat.ConversationListAdapter;
import com.education.online.act.chat.ConversationManager;
import com.education.online.adapter.VideoMainAdapter;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 可爱的蘑菇 on 2016/9/29.
 */
public class MessageMain extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList, searchResultList;
    protected ConversationListAdapter<Room> itemAdapter, itemAdapter2;
    private ConversationManager conversationManager;
    private EditText usercodeEdt;
    private HttpHandler httpHandler;
    private boolean firstOpen=true;

    public void initiHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getUserInfo)){
                    Intent intent=new Intent(MessageMain.this, Studentintroduction.class);
                    intent.putExtra("jsonData", jsonData);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_top_menu);

        firstOpen=SharedPreferencesUtil.getValue(this, "FirstOpen", true);
        initiHandler();
        _setHeaderTitle("消息");
        initView();
    }

    private void requestConversation() {
        if(firstOpen) {
            ChatManager chatManager = ChatManager.getInstance();
            if (chatManager.getSelfId() != null) {
                AVIMConversationQuery query = chatManager.getConversationQuery();
                List<String> member = new ArrayList<>();
                member.add(chatManager.getSelfId());
                query.containsMembers(member);
                query.limit(20);
                query.findInBackground(new AVIMConversationQueryCallback() {

                    @Override
                    public void done(List<AVIMConversation> list, AVIMException e) {
                        for (AVIMConversation con : list) {
                            ChatManager.getInstance().getRoomsTable().insertRoom(con.getConversationId());
                        }
                        updateConversationList();
                    }
                });
            }
        }else{
            updateConversationList();
        }
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.fragment_conversation_srl_view);
        searchResultList=(RecyclerView)findViewById(R.id.searchResultList);
        conversationManager = ConversationManager.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        LinearLayoutManager searchListLLM = new LinearLayoutManager(this);
        searchListLLM.setOrientation(LinearLayoutManager.VERTICAL);
        searchResultList.setLayoutManager(searchListLLM);

        itemAdapter = new ConversationListAdapter<Room>(this);
        recyclerList.setAdapter(itemAdapter);
        itemAdapter2 = new ConversationListAdapter<Room>(this);
        searchResultList.setAdapter(itemAdapter2);
        EventBus.getDefault().register(this);
        findViewById(R.id.systemMessageLayout).setOnClickListener(this);
        findViewById(R.id.myFavorite).setOnClickListener(this);
        usercodeEdt= (EditText) findViewById(R.id.usercodeEdt);
        usercodeEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_UNSPECIFIED||actionId==EditorInfo.IME_ACTION_SEARCH){
                    String word=usercodeEdt.getText().toString().trim();
                    if(word.length()>0){
                        httpHandler.getUserInfo(word);
                    }else{
                        ToastUtils.displayTextShort(MessageMain.this, "请输入AskID");
                    }
                }
                return false;
            }
        });
        usercodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0){
                    String  word=editable.toString().trim().toLowerCase();
                    List<Room> findRooms=new ArrayList<Room>();
                    List<Room> sortedRooms = itemAdapter.getDataList();
                    for (Room room:sortedRooms){
                        AVIMConversation conversation = room.getConversation();
                        if (null != conversation) {
                            if (ConversationHelper.typeOfConversation(conversation) == ConversationType.Single) {
                                LeanchatUser user = AVUserCacheUtils.getCachedUser(ConversationHelper.otherIdOfConversation(conversation));
                                if (null != user) {
                                    if(((String)user.get("username")).toLowerCase().contains(word))
                                        findRooms.add(room);
                                }
                            }
                        }else{
                            String name=ConversationHelper.nameOfConversation(conversation);
                            if(name.toLowerCase().contains(word))
                                findRooms.add(room);
                        }
                    }
                    itemAdapter2.setDataList(findRooms);
                    itemAdapter2.notifyDataSetChanged();
                    searchResultList.setVisibility(View.VISIBLE);
                }else
                    searchResultList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.systemMessageLayout:
                Intent intent=new Intent(MessageMain.this, SystemMessagePage.class);
                intent.putExtra(AVConstants.PUSH_INTENT_KEY, 1);
                startActivity(intent);
                break;
            case R.id.myFavorite:
                startActivity(new Intent(MessageMain.this, MyFavoriteList.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestConversation();
    }

    public void onEvent(ConversationItemClickEvent event) {
        Intent intent = new Intent(this, CM_MessageChatAct.class);
        intent.putExtra(Constants.CONVERSATION_ID, event.conversationId);
        startActivity(intent);
    }

    public void onEvent(ImTypeMessageEvent event) {
        updateConversationList();
    }

    private void updateConversationList() {
        conversationManager.findAndCacheRooms(new Room.MultiRoomsCallback() {
            @Override
            public void done(List<Room> roomList, AVException exception) {
                if (filterException(exception)) {

                    updateLastMessage(roomList);
//                    cacheRelatedUsers(roomList);

                    List<Room> sortedRooms = sortRooms(roomList);
                    itemAdapter.setDataList(sortedRooms);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void updateLastMessage(final List<Room> roomList) {
//        if(firstOpen){
            AVIMClient.setMessageQueryCacheEnable(false);
//        }
        for (final Room room : roomList) {
            AVIMConversation conversation = room.getConversation();
            if (null != conversation) {
                conversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
                    @Override
                    public void done(AVIMMessage avimMessage, AVIMException e) {
                        if (filterException(e) && null != avimMessage) {
                            if(!MessageMain.this.isFinishing()) {
                                room.setLastMessage(avimMessage);
                                int index = roomList.indexOf(room);
                                itemAdapter.notifyItemChanged(index);
                            }
                        }
                    }
                });
            }
        }
        AVIMClient.setMessageQueryCacheEnable(true);
        firstOpen=false;
    }

    /*private void cacheRelatedUsers(List<Room> rooms) {
        List<String> needCacheUsers = new ArrayList<String>();
        for(Room room : rooms) {
            AVIMConversation conversation = room.getConversation();
            if (ConversationHelper.typeOfConversation(conversation) == ConversationType.Single) {
                needCacheUsers.add(ConversationHelper.otherIdOfConversation(conversation));
            }
        }
        AVUserCacheUtils.cacheUsers(needCacheUsers, new AVUserCacheUtils.CacheUserCallback() {
            @Override
            public void done(Exception e) {
                itemAdapter.notifyDataSetChanged();
            }
        });
    }*/

    private List<Room> sortRooms(final List<Room> roomList) {
        List<Room> sortedList = new ArrayList<Room>();
        if (null != roomList) {
            sortedList.addAll(roomList);
            Collections.sort(sortedList, new Comparator<Room>() {
                @Override
                public int compare(Room lhs, Room rhs) {
                    long value = lhs.getLastModifyTime() - rhs.getLastModifyTime();
                    if (value > 0) {
                        return -1;
                    } else if (value < 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        return sortedList;
    }
}

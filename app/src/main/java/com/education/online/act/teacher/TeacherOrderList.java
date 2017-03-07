package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.adapter.TeacherOrderListAdapter;
import com.education.online.adapter.UserOrderAdapter;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.OrderDetailBean;
import com.education.online.bean.TeacherOrderBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.SimpleAdapterCallback;
import com.education.online.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public class TeacherOrderList extends BaseFrameAct implements View.OnClickListener {

    private RecyclerView recyclerList;
    private TextView selectTypeView, selectStatus, statusAll, statusFinish, statusToPay, statusToComment;
    HttpHandler handler;
    private String status = null;
    private String course_type = null;
    private int page = 1;
    private boolean onloading=false, complete=false;
    private TeacherOrderListAdapter adapter;
    private List<TeacherOrderBean> orders = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) {
                if (method.equals(Method.getTeacherOrderList)) {
                    String orderInfo = JsonUtil.getString(jsonData, "sale_situation");
                    List<TeacherOrderBean> addOrders = JSON.parseObject(orderInfo, new TypeReference<List<TeacherOrderBean>>(){});
                    orders.addAll(addOrders);
                    int totalpage= JsonUtil.getJsonInt(jsonData, "page_total");
                    if(totalpage==page){
                        adapter.setLoadingHint("");
                        complete=true;
                    }else
                        page++;
                    adapter.notifyDataSetChanged();
                    onloading=false;
                }
            }


            @Override
            public void onFailure(String method, JsonMessage jsonMessage, String json) {
                onloading=false;
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                onloading=false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        _setHeaderTitle("我的订单");
        initHandler();
        initView();
        handler.getTeacherOrderList(page);
    }

    private void initView() {

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter = new TeacherOrderListAdapter(this, orders, loader, this);
        recyclerList.setAdapter(adapter);
        recyclerList.addOnScrollListener(recyclerListener);
    }


    RecyclerView.OnScrollListener recyclerListener=new RecyclerView.OnScrollListener() {

        int lastVisibleItem=0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//            LogUtil.i("test", "listScrollY: "+listScrollY);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(adapter!=null)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if(!onloading){
                        if(!complete){
                            onloading = true;
                            handler.getSystemMessage(page);
                            adapter.setLoadingHint("正在加载");
                        }else
                            adapter.setLoadingHint("");
                    }
                }
        }
    };

    @Override
    public void onClick(View view) {
        TeacherOrderBean bean=orders.get((Integer) view.getTag());
        Intent i=new Intent(this, TeacherOrderDetail.class);
        i.putExtra(TeacherOrderBean.Name, bean);
        startActivity(i);
    }
}

package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.WalletHistoryAdapter;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.WalletInfoBean;
import com.education.online.bean.WalletLogBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class MyWalletHistory extends BaseFrameAct {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerList;
    private HttpHandler httpHandler;
    private WalletInfoBean walletInfoBean;
    private WalletHistoryAdapter adapter;
    private int page = 1;
    private String pageSize = "20";
    private List<WalletLogBean> walletLogBeanList = new ArrayList<>();
    private boolean onloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        _setHeaderTitle("明细");
        initView();
        initHandler();
        httpHandler.getWalletInfo(pageSize,String.valueOf(page));
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
         layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter = new WalletHistoryAdapter(MyWalletHistory.this,walletLogBeanList);
        recyclerList.setAdapter(adapter);

    }
    private void initHandler(){
        httpHandler = new HttpHandler(MyWalletHistory.this, new CallBack(MyWalletHistory.this){
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getWalletInfo)){
                    walletInfoBean = JsonUtil.getWalletInfo(jsonData);
                    Log.d("getWalletInfo", "获取钱包详情成功");
                    walletLogBeanList.addAll(walletInfoBean.getWallet_log());
                    adapter.notifyDataSetChanged();
                    page++;
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                if(method.equals(Method.getWalletInfo)){
                    onloading = false;
                    adapter.setLoadingHint("加载失败");
                }
            }
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                if (method.equals(Method.getQuestionList)) {
                    onloading = false;
                    adapter.setLoadingHint("加载失败");
                }
            }
        });

    }

    RecyclerView.OnScrollListener srcollListener = new RecyclerView.OnScrollListener() {

        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                if (!onloading) {
                    if (true) {
                        onloading = true;
                        adapter.setLoadingHint("正在加载");
                    } else
                        adapter.setLoadingHint("加载完成");
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };
}

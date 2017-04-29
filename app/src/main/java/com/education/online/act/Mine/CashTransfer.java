package com.education.online.act.Mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.order.OrderPay;
import com.education.online.act.order.WalletPay;
import com.education.online.adapter.TransHistoryAdapter;
import com.education.online.adapter.WalletHistoryAdapter;
import com.education.online.bean.AccountInfo;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.TeacherOrderBean;
import com.education.online.bean.TransferHistory;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2017/4/18.
 */

public class CashTransfer extends BaseFrameAct implements View.OnClickListener{

    private RelativeLayout modifypwd;
    private AccountInfo info;
    private EditText amountEdt;
    private int page=1;
    private RecyclerView recyclerList;
    private LinearLayoutManager layoutManager;
    private List<TransferHistory> datalist=new ArrayList<>();
    private TransHistoryAdapter adapter;
    private TextView walletBalance;
    private boolean onloading=false, complete=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_layout);
        _setHeaderTitle("提现");
        _setRightHomeText("解除绑定  ", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CashTransfer.this, WalletPay.class);
                intent.putExtra("noHint", true);
                startActivityForResult(intent, 0x20);
            }
        });
        initHandler();
        info= (AccountInfo) getIntent().getSerializableExtra("AccountInfo");
        init();
        handler.getTransferList(page);
        handler.getWalletInfo("20","1");
    }
    public void init(){
        amountEdt=(EditText)findViewById(R.id.amountEdt);
        walletBalance=(TextView)findViewById(R.id.walletBalance);
        ImageView typeIcon=(ImageView)findViewById(R.id.typeIcon);
        TextView typeTxt=(TextView)findViewById(R.id.typeTxt);
        TextView accountTxt=(TextView)findViewById(R.id.accountTxt);
        findViewById(R.id.confirmBtn).setOnClickListener(this);
        if(info.getAccount_type().equals("ALI")) {
            typeIcon.setImageResource(R.mipmap.icon_pay_ali);
            typeTxt.setText(info.getAccount_name());
            accountTxt.setVisibility(View.VISIBLE);
        }

        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter = new TransHistoryAdapter(this, datalist);
        recyclerList.setAdapter(adapter);
        recyclerList.addOnScrollListener(recyclerListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmBtn:
                if(ActUtil.isCash(amountEdt.getText().toString(), this)) {
                    Intent intent = new Intent();
                    intent.setClass(CashTransfer.this, WalletPay.class);
                    intent.putExtra("transfer", amountEdt.getText().toString());
                    startActivityForResult(intent, 0x10);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x10&&resultCode==0x11){
            String psw=data.getStringExtra("psw");
            handler.transfer(ActUtil.twoDecimal(amountEdt.getText().toString()), info.getAccount_type(), psw);
        }else if(requestCode==0x20&&resultCode==0x11){
            String psw=data.getStringExtra("psw");
            handler.unboundAccount(psw, info.getAccount_type());
        }
    }


    private HttpHandler handler;
    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, final String jsonData) throws JSONException {
                if (method.equals(Method.transfer)) {
                    DialogUtil.showInfoDialog(CashTransfer.this, "提示", "提现成功", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setResult(Constant.refreshData);
                            finish();
                        }
                    });
                }else if (method.equals(Method.unboundAccount)) {
                    DialogUtil.showInfoDialog(CashTransfer.this, "提示", "已解除账号绑定", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                            Intent i=new Intent(CashTransfer.this, TransferType.class);
                            startActivity(i);
                        }
                    });
                }else if (method.equals(Method.getTransferList)) {
                    String orderInfo = JsonUtil.getString(jsonData, "transfer_info");
                    List<TransferHistory> addDatas = JSON.parseObject(orderInfo, new TypeReference<List<TransferHistory>>(){});
                    datalist.addAll(addDatas);
                    int totalpage= JsonUtil.getJsonInt(jsonData, "page_total");
                    if(totalpage==page){
                        adapter.setLoadingHint("");
                        complete=true;
                    }else
                        page++;
                    adapter.notifyDataSetChanged();
                    onloading=false;
                }else if(method.equals(Method.getWalletInfo)){
                    JSONObject jsonObject = new JSONObject(jsonData);
                    if (!jsonObject.isNull("balance")){
                        String balance = jsonObject.getString("balance");
                        walletBalance.setText("￥"+balance);
                    }
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage, String jsonData) {
                if(method.equals(Method.getTransferList)){
                    onloading = false;
                    adapter.setLoadingHint("加载失败");
                }else
                    super.onFailure(method, jsonMessage, jsonData);
            }
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                if (method.equals(Method.getTransferList)) {
                    onloading = false;
                    adapter.setLoadingHint("加载失败");
                }else
                    super.onHTTPException(method, jsonMessage);
            }
        });
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
                            handler.getTransferList(page);
                            adapter.setLoadingHint("正在加载");
                        }else
                            adapter.setLoadingHint("");
                    }
                }
        }
    };
}

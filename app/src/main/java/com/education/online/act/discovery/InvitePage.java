package com.education.online.act.discovery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.ToastUtils;
import com.google.zxing.WriterException;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2016/9/29.
 */
public class InvitePage extends BaseFrameAct implements View.OnClickListener{

    private IWXAPI wxApi;
    private Bitmap qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friend);

        _setHeaderTitle("推荐给朋友");
        wxApi = WXAPIFactory.createWXAPI(this, Constant.WXAppId, true);
        wxApi.registerApp(Constant.WXAppId);

        initView();
    }

    private void initView() {
        ImageView iv=(ImageView)findViewById(R.id.qrcodeImg);
        try {
            qrcode = ImageUtil.Create2DCode(InvitePage.this,"jffdslkfjs83293jksand");
            iv.setImageBitmap(qrcode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        //https://github.com/kyleduo/SwitchButton
        findViewById(R.id.wxShareFriend).setOnClickListener(this);
        findViewById(R.id.wxShareTimeline).setOnClickListener(this);
    }

    private WXMediaMessage getWXMediaMessage(Context con, String title, String content, String url, Bitmap icon) {
        Bitmap thumb=icon;
        if(icon==null)
            thumb= BitmapFactory.decodeResource(con.getResources(), R.mipmap.applogo);
        WXWebpageObject web=new WXWebpageObject();
        web.webpageUrl=url;
        WXMediaMessage wxmsg=new WXMediaMessage(web);
        wxmsg.title=title;
        wxmsg.description=content;
        wxmsg.thumbData=Bitmap2Bytes(thumb);
        return wxmsg;
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    public void onClick(View v) {
        if(!wxApi.isWXAppInstalled()){
            ToastUtils.displayTextLong(InvitePage.this, "没有安装微信");
            return;
        }
        SendMessageToWX.Req request=new SendMessageToWX.Req();
        switch (v.getId()){
            case R.id.wxShareFriend:
                request.scene=SendMessageToWX.Req.WXSceneSession;
                break;
            case R.id.wxShareTimeline:
                request.scene=SendMessageToWX.Req.WXSceneTimeline;
                break;
        }
        request.message=getWXMediaMessage(this, "向学","爱学不学，就这么任性", "http://www.baidu.com", null);
        wxApi.sendReq(request);
    }

    @Override
    protected void onDestroy() {
        wxApi.unregisterApp();
        super.onDestroy();
        if(qrcode!=null&&!qrcode.isRecycled())
            qrcode.recycle();
    }
}

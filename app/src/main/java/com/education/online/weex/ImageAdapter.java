package com.education.online.weex;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

import java.lang.reflect.Field;

/**
 * Created by lixinke on 16/6/1.
 */
public class ImageAdapter implements IWXImgLoaderAdapter {


  @Override
  public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
    //实现你自己的图片下载。
    //本地图片统一规则：drawable://act_clothes_icon
    if (!TextUtils.isEmpty(url)) {
      if (url.startsWith("drawable://")) {
        getImageBydrawableName(view, url);//获取drawable图片
        return;
      }
    }
    ImageLoader imageLoader=ImageUtil.initImageLoader(view.getContext());
    imageLoader.displayImage(url, view);//获取网络图片
  }

  /**
   * 通过反射获取drawable图片
   * @param view
   * @param url
   */
  private void getImageBydrawableName(View view, String url) {
    String urls[] = url.split("//");
    String drawableName = "";
    if(urls != null && urls.length >1) {
      drawableName = urls[1];
    }
    try {
      Field f = R.drawable.class.getField(drawableName);
      view.setBackgroundResource(f.getInt(f));
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}

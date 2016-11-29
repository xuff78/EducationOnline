package com.avoscloud.leanchatlib.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import com.avoscloud.leanchatlib.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lzw on 15/4/24.
 */
public class PhotoUtils {
  public static DisplayImageOptions avatarImageOptions = new DisplayImageOptions.Builder()
      .showImageOnLoading(R.drawable.chat_default_user_avatar)
      .showImageForEmptyUri(R.drawable.chat_default_user_avatar)
      .showImageOnFail(R.drawable.chat_default_user_avatar)
      .cacheInMemory(true)
      .cacheOnDisc(true)
      .considerExifParams(true)
      .imageScaleType(ImageScaleType.EXACTLY)
      .bitmapConfig(Bitmap.Config.RGB_565)
      .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
          //.displayer(new RoundedBitmapDisplayer(20))
          //.displayer(new FadeInBitmapDisplayer(100))// 淡入
      .build();

    public static DisplayImageOptions normalImageOptions = new DisplayImageOptions.Builder()
      .showImageOnLoading(R.drawable.chat_common_empty_photo)
      .showImageForEmptyUri(R.drawable.chat_common_empty_photo)
      .showImageOnFail(R.drawable.chat_common_empty_photo)
      .cacheInMemory(true)
      .cacheOnDisc(true)
      .considerExifParams(true)
      .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
      .bitmapConfig(Bitmap.Config.RGB_565)
      .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
          //.displayer(new RoundedBitmapDisplayer(20))
      .displayer(new FadeInBitmapDisplayer(100))// 淡入
      .build();

  public static void displayImageCacheElseNetwork(ImageView imageView,
                                                  String path, String url) {
    ImageLoader imageLoader = ImageLoader.getInstance();
    if (path != null) {
      File file = new File(path);
      if (file.exists()) {
        imageLoader.displayImage("file://" + path, imageView, normalImageOptions);
        return;
      }
    }
    imageLoader.displayImage(url, imageView, normalImageOptions);
  }

  public static String compressImage(String path, String newPath, Activity act) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);
    int inSampleSize = 1;
    int maxSize = 3000;
    if (options.outWidth > maxSize || options.outHeight > maxSize) {
      int widthScale = (int) Math.ceil(options.outWidth * 1.0 / maxSize);
      int heightScale = (int) Math.ceil(options.outHeight * 1.0 / maxSize);
      inSampleSize = Math.max(widthScale, heightScale);
    }
    options.inJustDecodeBounds = false;
    options.inSampleSize = inSampleSize;
    Bitmap bitmap = BitmapFactory.decodeFile(path, options);
//    int w = bitmap.getWidth();
//    int h = bitmap.getHeight();
//    int newW = w;
//    int newH = h;
//    if (w > maxSize || h > maxSize) {
//      if (w > h) {
//        newW = maxSize;
//        newH = (int) (newW * h * 1.0 / w);
//      } else {
//        newH = maxSize;
//        newW = (int) (newH * w * 1.0 / h);
//      }
//    }
//    Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newW, newH, false);


    int degree = readPictureDegree(path);
    Matrix matrix = new Matrix();
    matrix.setRotate(degree);
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    DisplayMetrics dm = new DisplayMetrics();
    act.getWindowManager().getDefaultDisplay().getMetrics(dm);
    float scale;
    if(width>height){
      scale= dm.widthPixels/(float)width;
    }else
      scale=dm.heightPixels/(float)height;
    matrix.postScale(scale, scale);
    final Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

    FileOutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream(newPath);
      newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
    } catch (FileNotFoundException e) {
      LogUtils.logException(e);
    } finally {
      try {
        outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    recycle(newBitmap);
    recycle(bitmap);
    return newPath;
  }

  public static void recycle(Bitmap bitmap) {
    // 先判断是否已经回收
    if (bitmap != null && !bitmap.isRecycled()) {
      // 回收并且置为null
      bitmap.recycle();
    }
    System.gc();
  }

  public static int readPictureDegree(String filepath) {
    int degree = 0;
    ExifInterface exif = null;
    try {
      exif = new ExifInterface(filepath);
    } catch (IOException ex) {
    }
    if (exif != null) {
      int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
      if (orientation != -1) {
        switch(orientation) {
          case ExifInterface.ORIENTATION_ROTATE_90:
            degree = 90;
            break;
          case ExifInterface.ORIENTATION_ROTATE_180:
            degree = 180;
            break;
          case ExifInterface.ORIENTATION_ROTATE_270:
            degree = 270;
            break;
        }
      }
    }
    return degree;
  }

  public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
            bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    final RectF rectF = new RectF(rect);
    final float roundPx = pixels;

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);

    return output;
  }
}

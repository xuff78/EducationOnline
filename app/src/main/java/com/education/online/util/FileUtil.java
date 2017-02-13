package com.education.online.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class FileUtil {

	private static final String[][] MIME_MapTable={
			{".3gp", "video/3gpp"},
			{".apk", "application/vnd.android.package-archive"},
			{".asf", "video/x-ms-asf"},
			{".avi", "video/x-msvideo"},
			{".bin", "application/octet-stream"},
			{".bmp", "image/bmp"},
			{".c", "text/plain"},
			{".class", "application/octet-stream"},
			{".conf", "text/plain"},
			{".cpp", "text/plain"},
			{".doc", "application/msword"},
			{".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
			{".xls", "application/vnd.ms-excel"},
			{".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
			{".exe", "application/octet-stream"},
			{".gif", "image/gif"},
			{".gtar", "application/x-gtar"},
			{".gz", "application/x-gzip"},
			{".h", "text/plain"},
			{".htm", "text/html"},
			{".html", "text/html"},
			{".jar", "application/java-archive"},
			{".java", "text/plain"},
			{".jpeg", "image/jpeg"},
			{".jpg", "image/jpeg"},
			{".js", "application/x-javascript"},
			{".log", "text/plain"},
			{".m3u", "audio/x-mpegurl"},
			{".m4a", "audio/mp4a-latm"},
			{".m4b", "audio/mp4a-latm"},
			{".m4p", "audio/mp4a-latm"},
			{".m4u", "video/vnd.mpegurl"},
			{".m4v", "video/x-m4v"},
			{".mov", "video/quicktime"},
			{".mp2", "audio/x-mpeg"},
			{".mp3", "audio/x-mpeg"},
			{".mp4", "video/mp4"},
			{".mpc", "application/vnd.mpohun.certificate"},
			{".mpe", "video/mpeg"},
			{".mpeg", "video/mpeg"},
			{".mpg", "video/mpeg"},
			{".mpg4", "video/mp4"},
			{".mpga", "audio/mpeg"},
			{".msg", "application/vnd.ms-outlook"},
			{".ogg", "audio/ogg"},
			{".pdf", "application/pdf"},
			{".png", "image/png"},
			{".pps", "application/vnd.ms-powerpoint"},
			{".ppt", "application/vnd.ms-powerpoint"},
			{".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
			{".prop", "text/plain"},
			{".rc", "text/plain"},
			{".rmvb", "audio/x-pn-realaudio"},
			{".rtf", "application/rtf"},
			{".sh", "text/plain"},
			{".tar", "application/x-tar"},
			{".tgz", "application/x-compressed"},
			{".txt", "text/plain"},
			{".wav", "audio/x-wav"},
			{".wma", "audio/x-ms-wma"},
			{".wmv", "audio/x-ms-wmv"},
			{".wps", "application/vnd.ms-works"},
			{".xml", "text/plain"},
			{".z", "application/x-compress"},
			{".zip", "application/x-zip-compressed"},
			{"", "*/*"}
	};

	/**
	 * 打开文件
	 * @param file
	 */
	public static void openFile(Context con, File file){

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), type);
		con.startActivity(intent); //这里最好try一下，有可能会报错。 //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。

	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * @param file
	 */
	public static String getMIMEType(File file) {

		String type="*/*";
		String fName = file.getName();
//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if(dotIndex < 0){
			return type;
		}
/* 获取文件的后缀名*/
		String end=fName.substring(dotIndex,fName.length()).toLowerCase();
		if(end=="")return type;
//在MIME和文件类型的匹配表中找到对应的MIME类型。
		for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if(end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	// 存储Bitmap
	public static boolean saveBitmap(Bitmap bmp, String id, Context context) {
		boolean isfinish = true;
		File file = getFile(id + ".png", context);
		if (!file.isDirectory()) {
			try {
				FileOutputStream out = new FileOutputStream(file);
				isfinish = bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				isfinish = false;
				e.printStackTrace();
			}
		}
		return isfinish;
	}

	public static boolean saveBitmap(Bitmap bmp, String id, Context context, int rate) {
		boolean isfinish = true;
		File file = getFile(id + ".png", context);
		if (!file.isDirectory()) {
			try {
				FileOutputStream out = new FileOutputStream(file);
				isfinish = bmp.compress(Bitmap.CompressFormat.PNG, rate, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				isfinish = false;
				e.printStackTrace();
			}
		}
		return isfinish;
	}

	public static File getFile(String fileName, Context con) {
		File file;
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			isExist(ImageUtil.getSavePath(con));
			file = new File(ImageUtil.getSavePath(con), fileName);
		} else
			file = new File(con.getCacheDir(), fileName);
		return file;
	}

	/**
	 * 删除文件夹以及目录下的文件
	 * @param   filePath 被删除目录的文件路径
	 * @return  目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String filePath) {
		boolean flag = false;
		//如果filePath不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		File dirFile = new File(filePath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();
		//遍历删除文件夹下的所有文件(包括子目录)
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				//删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) break;
			} else {
				//删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) break;
			}
		}
		if (!flag) return false;
		//删除当前空目录
		return dirFile.delete();
	}

	/**
	 * 删除单个文件
	 * @param   filePath    被删除文件的文件名
	 * @return 文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			return file.delete();
		}
		return false;
	}
	
	/**
	 * 判断文件夹是否存在,如果不存在则创建文件夹
	 */
	public static void isExist(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}

	public static boolean copyFile(File sfile, File tid) {
		LogUtil.d("test", "download finish!!!");
		if (!sfile.exists())
			return false;
		if (tid.exists())
			tid.delete();

		BufferedInputStream reader = null;
		BufferedOutputStream writer = null;
		try {
			reader = new BufferedInputStream(new FileInputStream(sfile));
			writer = new BufferedOutputStream(new FileOutputStream(tid, false));
			byte[] buff = new byte[8192];
			int numChars;
			while ((numChars = reader.read(buff, 0, buff.length)) != -1) {
				writer.write(buff, 0, numChars);
			}
		} catch (IOException ex) {
			return false;
		} finally {
			try {
				if (reader != null) {
					writer.close();
					reader.close();
				}
			} catch (IOException ex) {
			}
		}
		return true;
	}
	
	public static Bitmap getSavedDrawable(String id, Context con) {
		File file = getFile(id + ".png", con);
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inSampleSize = 4;
		return BitmapFactory.decodeFile(file.toString());//, options);
	}

	public static void releaseImage(ImageView img) {
		if (img != null) {
			Bitmap bm = ((BitmapDrawable) img.getDrawable()).getBitmap();
			if (bm != null)
				bm.recycle();
			img.setImageDrawable(null);
		}
	}

	// 解压缩包
	public static int openZip(Context context, String dataBasePath, String zipName) {
		int flag = 0;
		try {
			AssetManager am = context.getAssets();
			ZipInputStream zis = new ZipInputStream(am.open(zipName));
			FileOutputStream fos = new FileOutputStream(dataBasePath);
			zis.getNextEntry();// 读取下一个 ZIP 文件条目并将流定位到该条目数据的开始处。
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}

			fos.flush();
			fos.close();
			zis.close();
			flag = 1;
		} catch (Exception e) {
			flag = -1;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取指定文件大小
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception
	{
		long size = 0;
		if (file.exists()){
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		}
		else{
			file.createNewFile();
			LogUtil.e("获取文件大小","文件不存在!");
		}
		return size;
	}

	/**
	 * 获取指定文件夹
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f)
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++){
			if (flist[i].isDirectory()){
				size = size + getFileSizes(flist[i]);
			}
			else{
				try {
					size =size + getFileSize(flist[i]);
				} catch (Exception e) {
					e.printStackTrace();
					size=0;
				}
			}
		}
		return size;
	}
}

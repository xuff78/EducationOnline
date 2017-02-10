package com.education.online.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class FileUtil {

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

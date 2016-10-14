package com.education.online.act.upyun;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;

public class UploadTask extends AsyncTask<Object, String, String> {

	private static final String TEST_API_KEY = "EnpdhpTAVSaOaqMkWoJ+mVp+zTA="; // 测试使用的表单api验证密钥
	private static final String BUCKET = "longforlearn"; // 存储空间
	private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 1000 * 5 * 10; // 过期时间，必须大于当前时间
	public static final String UPLOAD_URL = "http://longforlearn.b0.upaiyun.com";
	public static final String THUMBNAIL = "!infopic140";

	private static final String SOURCE_FILE = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator + "IMAG1104.jpg"; // 来源文件
	
//	private CustomProgressDialogForXF mPrgDlg;

	public interface UploadCallBack {
		public void onSuccess(String result);

		public void onFailed();
	};

	private UploadCallBack mCallBack;

	public UploadTask(UploadCallBack mCallBack) {
		this.mCallBack = mCallBack;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Object... params) {
		String string = null;
		publishProgress("图片上传中...");
		try {
			// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
			String SAVE_KEY = File.separator + (String) params[1];

			// 取得base64编码后的policy
			String policy = UpYunUtils.makePolicy(SAVE_KEY, EXPIRATION, BUCKET);

			// 根据表单api签名密钥对policy进行签名
			// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
			String signature = UpYunUtils.signature(policy + "&" + TEST_API_KEY);
			
			// 上传文件到对应的bucket中去。
			string = Uploader.upload(policy, signature, BUCKET, (File) params[0]);

		} catch (UpYunException e) {
			e.printStackTrace();
		}

		return string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		publishProgress();
		if (result != null) {
			mCallBack.onSuccess(result);
		} else {
			mCallBack.onFailed();
		}
	}
	
//	@Override
//	protected void onProgressUpdate(String... values) {
//		super.onProgressUpdate(values);
//
//		try {
//			// 进度条不存在时创建
//			if (mPrgDlg == null) {
//				mPrgDlg = new CustomProgressDialogForXF(GlbsActivity.GLOBAL_CONTEXT, "图片上传中...");
//			}
//
//			if (values == null || (values != null && values.length == 0)) {
//				// 取消进度条显示
//				mPrgDlg.dismiss();
//			} else {
////				// 设置进度条显示内容并显示进度条
//////				mPrgDlg.setMessage(values[0]);
////				if (values[0].equals(Constantparams.method_getCommonInfo)
////						|| values[0].equals(Constantparams.method_checkVersion)
////						|| values[0].equals(Constantparams.method_pollingTicketStatus)) {
////				} else {
//						mPrgDlg.show();
////				}
//			}
//		} catch (Exception e) {
//			LogUtil.ePrint(e);
//		}
//
//	}
//
//	@Override
//	protected void onCancelled() {
//		if (mPrgDlg != null) {
//			mPrgDlg.dismiss();
//		}
//	}

}

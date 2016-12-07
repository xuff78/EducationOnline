package com.education.online.download;

import java.io.File;

public interface DownloadListener {

	void onStart(int fileByteSize);
	
	void onPause();

	void onProgress(int receivedBytes);

	void onFail();

	void onSuccess(File file);
	
	void onCancel();
}

package com.education.online.bean;

import java.io.Serializable;

/**
 * 支付成功提示界面
 * 
 * @author lilin1@infohold.com.cn
 */
public class VersionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String Intent_Key = "PayResult";
	/** 更新类型, 0-不更新,1-建议更新,2-强制更新,3-系统维护 */
	private String update_type = "";
	/** 版本号 */
	private String version_code = "";
	/** 客户端版本号 */
	private String version = "";
	/** 版本说明 */
	private String version_desc = "";
	/** 下载地址 */
	private String url = "";

	public String getUpdate_type() {
		return update_type;
	}

	public void setUpdate_type(String update_type) {
		this.update_type = update_type;
	}

	public String getVersion_code() {
		return version_code;
	}

	public void setVersion_code(String version_code) {
		this.version_code = version_code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion_desc() {
		return version_desc;
	}

	public void setVersion_desc(String version_desc) {
		this.version_desc = version_desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

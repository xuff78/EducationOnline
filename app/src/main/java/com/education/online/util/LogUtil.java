/**
 * 
 */
package com.education.online.util;

import android.os.Debug;
import android.util.Log;

/**
 * log class.<br>
 * Can set the switch logging closure and open. <br>
 * In addition, to avoid the use of system when the class,<br>
 * if Log Log contents for null times wrong happens.<br>
 * 
 */
public class LogUtil {

	/**
	 * Control log output <br>
	 * open:true<br>
	 * close:false<br>                                   
	 */
	public static boolean logIsOpen = false ;

	public static boolean exceptionLogIsOpne = false;

	public static void d(String tag, String msg) {
		if (logIsOpen) {
			Log.d(tag, "[ " + msg + " ]");
		}
	}

	public static void e(String tag, String msg) {
		if (logIsOpen) {
			Log.e(tag, "[ " + msg + " ]");
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (logIsOpen) {
			Log.e(tag, "[ " + msg + " ]", tr);
		}
	}

	public static void i(String tag, String msg) {
		if (logIsOpen) {
			Log.i(tag, "[ " + msg + " ]");
		}
	}

	public static void v(String tag, String msg) {
		if (logIsOpen) {
			Log.v(tag, "[ " + msg + " ]");
		}
	}

	public static void w(String tag, String msg) {
		if (logIsOpen) {
			Log.w(tag, "[ " + msg + " ]");
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (logIsOpen) {
			Log.w(tag, msg, tr);
		}
	}

	// Performance test
	public static final boolean TRACEVIEW_DBG = false;

	/**
	 * traceview performance test in main thread
	 * 
	 * @param name
	 */
	public static void DebugStart(String name) {
		if (LogUtil.TRACEVIEW_DBG) {
			Debug.startMethodTracing(name);
		}
	}

	public static void DebugStop() {
		if (LogUtil.TRACEVIEW_DBG) {
			Debug.stopMethodTracing();
		}
	}

	public static void ePrint(Exception e) {
		if (exceptionLogIsOpne) {
			e.printStackTrace();
		}

	}
}

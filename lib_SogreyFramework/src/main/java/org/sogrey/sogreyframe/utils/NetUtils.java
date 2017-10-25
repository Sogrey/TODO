package org.sogrey.sogreyframe.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * 跟网络相关的工具类
 * 
 * @author Sogrey
 * @date 2015年7月23日
 */
public class NetUtils {
	/** 单例模式 对象 */
	private static NetUtils sInstance;

	/**
	 * 单例模式 <br>
	 * 一个类最多只能有一个实例 <br>
	 * 1、有一个私有静态成员 <br>
	 * 2、有一个公开静态方法getInstance得到这个私有静态成员 <br>
	 * 3、有一个私有的构造方法（不允许被实例化） <br>
	 */

	public static NetUtils getInstance() {
		if (sInstance == null) {
			synchronized (NetUtils.class) {
				if (sInstance == null) {
					sInstance = new NetUtils();
				}
			}
		}
		return sInstance;
	}

	private NetUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity) {

			NetworkInfo info= connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState()==State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断有无网络链接
	 * 
	 * @author Sogrey
	 * @date 2015年7月23日
	 * @param mContext
	 * @return
	 */
	public static boolean checkNetworkInfo(Context mContext) {
		ConnectivityManager conMan = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
							 .getState();
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						   .getState();
		if (mobile==State.CONNECTED||mobile==State.CONNECTING)
			return true;
		if (wifi==State.CONNECTED||wifi==State.CONNECTING)
			return true;
		return false;
	}

	/**
	 * 判断网络是否为漫游
	 */
	public static boolean isNetworkRoaming(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			LogUtil.w("", "couldn't get connectivity manager");
		} else {
			NetworkInfo info= connectivity.getActiveNetworkInfo();
			if (info != null
					&&info.getType()==ConnectivityManager.TYPE_MOBILE) {
				TelephonyManager tm = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (tm != null && tm.isNetworkRoaming()) {
					LogUtil.d("", "network is roaming");
					return true;
				} else {
					LogUtil.d("", "network is not roaming");
				}
			} else {
				LogUtil.d("", "not using mobile network");
			}
		}
		return false;
	}

	/**
	 * Gps是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isGpsEnabled(Context context) {
		LocationManager locationManager = ((LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders= locationManager.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isMobileDataEnable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isMobileDataEnable = false;

		isMobileDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		return isMobileDataEnable;
	}

	/**
	 * 判断当前网络是否是MOBILE网络
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isMOBILE(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo= connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&&activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * 判断wifi 是否可用
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isWifiDataEnable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isWifiDataEnable = false;
		isWifiDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		return isWifiDataEnable;
	}

	/**
	 * 判断当前网络是否是wifi网络
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo= connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&&activeNetInfo.getType()==ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 获得IP<br>
	 *  <li>android.permission.ACCESS_WIFI_STATE</li>
	 * @author Sogrey
	 * @date 2015-11-13下午6:06:37
	 * @param context
	 * @return
	 */
	public static String getIP(Context context) {
		String ip= "";
		if (isWifi(context)) {
			// 获取wifi服务
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			// 判断wifi是否开启
			if (!wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(true);
			}
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int      ipAddress= wifiInfo.getIpAddress();
			ip = intToIp(ipAddress);
		} else {
			ip = getLocalIpAddress();
		}
		return ip;
	}

	/**
	 * 非wifi下IP地址
	 * 
	 * @author Sogrey
	 * @date 2015-11-13下午6:05:30
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en=NetworkInterface
					.getNetworkInterfaces();en.hasMoreElements();) {
				NetworkInterface intf= en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr=intf
						.getInetAddresses();enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress= enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreferenceIpAddress",ex.toString());
		}
		return null;
	}

	/**
	 * 格式化IP
	 * 
	 * @author Sogrey
	 * @date 2015-11-13下午6:05:59
	 * @param i
	 * @return
	 */
	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Activity activity) {
		Intent intent= new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
											 "com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}

	/**
	 * 网络未连接时，调用设置方法
	 */
	public void setNetwork(final Context context) {

		AlertDialog.Builder builder= new AlertDialog.Builder(context);
		// builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("网络提示信息");
		builder.setMessage("网络不可用，如果继续，请先设置网络！");
		builder.setPositiveButton("设置", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which) {
				Intent intent= null;
				/**
				 * 判断手机系统的版本！如果API大于10 就是3.0+ 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
				 */
				if (android.os.Build.VERSION.SDK_INT > 10) {
					intent = new Intent(
							android.provider.Settings.ACTION_WIFI_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				context.startActivity(intent);
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which) {

			}
		});
		builder.create();
		builder.show();
	}

	/**
	 * 枚举网络状态 NET_NO：没有网络 NET_2G:2g网络 NET_3G：3g网络 NET_4G：4g网络 NET_WIFI：wifi
	 * NET_UNKNOWN：未知网络
	 */
	public static enum NetState {
		NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
	};

	/**
	 * 判断当前状态
	 * 
	 * @param context
	 * @return 状态码
	 */
	public NetState getConnectedState(Context context) {
		NetState stateCode = NetState.NET_NO;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni= cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnectedOrConnecting()) {
			switch (ni.getType()) {
			case ConnectivityManager.TYPE_WIFI:
				stateCode = NetState.NET_WIFI;
				break;
			case ConnectivityManager.TYPE_MOBILE:
				switch (ni.getSubtype()) {
				case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
				case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
				case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN:
					stateCode = NetState.NET_2G;
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B:
				case TelephonyManager.NETWORK_TYPE_EHRPD:
				case TelephonyManager.NETWORK_TYPE_HSPAP:
					stateCode = NetState.NET_3G;
					break;
				case TelephonyManager.NETWORK_TYPE_LTE:
					stateCode = NetState.NET_4G;
					break;
				default:
					stateCode = NetState.NET_UNKNOWN;
				}
				break;
			default:
				stateCode = NetState.NET_UNKNOWN;
			}
		}
		return stateCode;
	}

	/**
	 * 获取网络信息;
	 * 
	 * @param ctx
	 */
	public void GetNetResultInfo(Context ctx) {
		if (mNetEventHandle != null && ctx != null) {
			mNetEventHandle.netState(getConnectedState(ctx));
		}
	}

	public static interface NetEventHandle {
		/**
		 * 网络状态码
		 * 
		 * @param netCode
		 */
		void netState(NetState netCode);
	}

	private NetEventHandle mNetEventHandle;

	/**
	 * 添加监听接口;
	 * 
	 * @param netEvent
	 */
	public void addNetEventHandle(NetEventHandle netEvent) {
		mNetEventHandle = netEvent;
	}

}

/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sogrey.sogreyframe.global;

/**
 * © 2012 amsoft.cn 名称：AbAppConfig.java 描述：初始设置类.
 * 
 */
public class AppConfig {

	/** UI设计的基准宽度 */
	public static int UI_WIDTH = 720;

	/** UI设计的基准高度 */
	public static int UI_HEIGHT = 1080;

	/** 默认 SharePreferences文件名 */
	public static String SHARED_PATH= "app_base_share";

	/** 默认数据库文件名 */
	public static String DB_NAME   = "base.db";
	/** 默认数据库版本 */
	public static int    DB_VERSION= 1;

	/** 默认下载文件地址. */
	public static String DOWNLOAD_ROOT_DIR= "app";

	/** 默认下载图片文件地址. */
	public static String DOWNLOAD_IMAGE_DIR= "images";

	/** 默认下载文件地址. */
	public static String DOWNLOAD_FILE_DIR= "files";

	/** APP缓存目录. */
	public static String CACHE_DIR= "cache";

	/** DB目录. */
	public static String DB_DIR= "db";

	/** 日志目录. */
	public static String LOG_DIR= "log";

	/** 缓存大小 单位10M. */
	public static int MAX_CACHE_SIZE_INBYTES = 10 * 1024 * 1024;

	/** The Constant CONNECTEXCEPTION.无法连接到网络 */
	public static String CONNECT_EXCEPTION= "无法连接到网络";

	/** The Constant UNKNOWNHOSTEXCEPTION. 连接远程地址失败 */
	public static String UNKNOWN_HOST_EXCEPTION= "连接远程地址失败";

	/** The Constant SOCKETEXCEPTION. 网络连接出错，请重试 */
	public static String SOCKET_EXCEPTION= "网络连接出错，请重试";

	/** The Constant SOCKETTIMEOUTEXCEPTION. 连接超时，请重试 */
	public static String SOCKET_TIMEOUT_EXCEPTION= "连接超时，请重试";

	/** The Constant NULLPOINTEREXCEPTION. 抱歉，远程服务出错了 */
	public static String NULL_POINTER_EXCEPTION= "抱歉，远程服务出错了";

	/** The Constant NULLMESSAGEEXCEPTION.抱歉，程序出错了 */
	public static String NULL_MESSAGE_EXCEPTION= "抱歉，程序出错了";

	/** The Constant CLIENTPROTOCOLEXCEPTION. Http请求参数错误 */
	public static String CLIENTP_ROTOCOL_EXCEPTION= "Http请求参数错误";

	/** The Constant MISSING_PARAMETERS.参数个数不够. */
	public static String MISSING_PARAMETERS= "参数没有包含足够的个数";

	/** The Constant REMOTESERVICEEXCEPTION.抱歉，远程服务出错了 */
	public static String REMOTE_SERVICE_EXCEPTION= "抱歉，远程服务出错了";

	/** The Constant NOT_FOUND_EXCEPTION. 页面未找到 */
	public static String NOT_FOUND_EXCEPTION= "页面未找到";

	/** The Constant UNTREATED_EXCEPTION. 其他异常. */
	public static String UNTREATED_EXCEPTION= "未处理的异常";

}

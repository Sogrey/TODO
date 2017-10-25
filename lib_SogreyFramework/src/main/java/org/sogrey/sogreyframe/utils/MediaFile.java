package org.sogrey.sogreyframe.utils;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 判断多媒体文件类型
 * 
 * @author Sogrey
 * 
 */
public class MediaFile {
	// comma separated list of all file extensions supported by the media
	// scanner
	private static String sFileExtensions;

	// Audio file types
	private static final int FILE_TYPE_MP3 = 1;
	private static final int FILE_TYPE_M4A = 2;
	private static final int FILE_TYPE_WAV = 3;
	private static final int FILE_TYPE_AMR = 4;
	private static final int FILE_TYPE_AWB = 5;
	private static final int FILE_TYPE_WMA = 6;
	private static final int FILE_TYPE_OGG = 7;
	private static final int FIRST_AUDIO_FILE_TYPE = FILE_TYPE_MP3;
	private static final int LAST_AUDIO_FILE_TYPE = FILE_TYPE_OGG;

	// MIDI file types
	private static final int FILE_TYPE_MID = 11;
	private static final int FILE_TYPE_SMF = 12;
	private static final int FILE_TYPE_IMY = 13;
	private static final int FIRST_MIDI_FILE_TYPE = FILE_TYPE_MID;
	private static final int LAST_MIDI_FILE_TYPE = FILE_TYPE_IMY;

	// Video file types
	private static final int FILE_TYPE_MP4 = 21;
	private static final int FILE_TYPE_M4V = 22;
	private static final int FILE_TYPE_3GPP = 23;
	private static final int FILE_TYPE_3GPP2 = 24;
	private static final int FILE_TYPE_WMV = 25;
	private static final int FILE_TYPE_WOV = 26;
	private static final int FILE_TYPE_M3U8 = 27;
	private static final int FIRST_VIDEO_FILE_TYPE = FILE_TYPE_MP4;
	private static final int LAST_VIDEO_FILE_TYPE = FILE_TYPE_M3U8;

	// Image file types
	private static final int FILE_TYPE_JPEG = 31;
	private static final int FILE_TYPE_GIF = 32;
	private static final int FILE_TYPE_PNG = 33;
	private static final int FILE_TYPE_BMP = 34;
	private static final int FILE_TYPE_WBMP = 35;
	private static final int FILE_TYPE_ICON = 35;
	private static final int FIRST_IMAGE_FILE_TYPE = FILE_TYPE_JPEG;
	private static final int LAST_IMAGE_FILE_TYPE = FILE_TYPE_ICON;

	// Playlist file types
	private static final int FILE_TYPE_M3U = 41;
	private static final int FILE_TYPE_PLS = 42;
	private static final int FILE_TYPE_WPL = 43;
	private static final int FIRST_PLAYLIST_FILE_TYPE = FILE_TYPE_M3U;
	private static final int LAST_PLAYLIST_FILE_TYPE = FILE_TYPE_WPL;

	/**
	 * 媒体文件类型-音频，视频，图片，未知
	 * @author Sogrey
	 * @date 2015年4月22日
	 */
	public enum MediaFileTypeList {
		Audio, Video, Image, UnKnown
	}

	/**
	 * 静态内部类-媒体文件类型-文件类型，mime
	 * @author Sogrey
	 * @date 2015年4月22日
	 */
	public static class MediaFileType {

		int fileType;
		public String mimeType;

		MediaFileType(int fileType, String mimeType) {
			this.fileType = fileType;
			this.mimeType = mimeType;
		}
	}

	private static HashMap<String, MediaFileType> sFileTypeMap = new HashMap<String, MediaFileType>();
	private static HashMap<String, Integer> sMimeTypeMap = new HashMap<String, Integer>();

	static void addFileType(String extension, int fileType, String mimeType) {
		sFileTypeMap.put(extension, new MediaFileType(fileType, mimeType));
		sMimeTypeMap.put(mimeType, new Integer(fileType));
	}

	static {
		// 音频
		addFileType("MP3", FILE_TYPE_MP3, "audio/mpeg");
		addFileType("M4A", FILE_TYPE_M4A, "audio/mp4");
		addFileType("WAV", FILE_TYPE_WAV, "audio/x-wav");
		addFileType("AMR", FILE_TYPE_AMR, "audio/amr");
		addFileType("AWB", FILE_TYPE_AWB, "audio/amr-wb");
		addFileType("WMA", FILE_TYPE_WMA, "audio/x-ms-wma");
		addFileType("OGG", FILE_TYPE_OGG, "application/ogg");

		addFileType("MID", FILE_TYPE_MID, "audio/midi");
		addFileType("XMF", FILE_TYPE_MID, "audio/midi");
		addFileType("RTTTL", FILE_TYPE_MID, "audio/midi");
		addFileType("SMF", FILE_TYPE_SMF, "audio/sp-midi");
		addFileType("IMY", FILE_TYPE_IMY, "audio/imelody");

		// 视频
		addFileType("MP4", FILE_TYPE_MP4, "video/mp4");
		addFileType("M4V", FILE_TYPE_M4V, "video/mp4");
		addFileType("3GP", FILE_TYPE_3GPP, "video/3gpp");
		addFileType("3GPP", FILE_TYPE_3GPP, "video/3gpp");
		addFileType("3G2", FILE_TYPE_3GPP2, "video/3gpp2");
		addFileType("3GPP2", FILE_TYPE_3GPP2, "video/3gpp2");
		addFileType("WMV", FILE_TYPE_WMV, "video/x-ms-wmv");
		addFileType("WOV", FILE_TYPE_WOV, "video/quicktime");

		// 图片
		addFileType("JPG", FILE_TYPE_JPEG, "image/jpeg");
		addFileType("JPEG", FILE_TYPE_JPEG, "image/jpeg");
		addFileType("GIF", FILE_TYPE_GIF, "image/gif");
		addFileType("PNG", FILE_TYPE_PNG, "image/png");
		addFileType("BMP", FILE_TYPE_BMP, "image/x-ms-bmp");
		addFileType("WBMP", FILE_TYPE_WBMP, "image/vnd.wap.wbmp");
		addFileType("ICO", FILE_TYPE_ICON, "image/x-icon");

		// { ".doc", "application/msword" },
		// { ".docx",
		// "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
		// },
		// { ".pdf", "application/pdf" },
		// { ".pps", "application/vnd.ms-powerpoint" },
		// { ".ppt", "application/vnd.ms-powerpoint" },
		// { ".pptx",
		// "application/vnd.openxmlformats-officedocument.presentationml.presentation"
		// },
		// { ".txt", "text/plain" },
		// { ".rar", "application/octet-stream" },
		// { ".zip", "application/zip" },
		// { ".xla", "application/vnd.ms-excel" },
		// { ".xlc", "application/vnd.ms-excel" },
		// { ".xll", "application/x-excel" },
		// { ".xlm", "application/vnd.ms-excel" },
		// { ".xls", "application/vnd.ms-excel" },
		// { ".xlsx",
		// "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
		// },
		// { ".xlt", "application/vnd.ms-excel" },
		// { ".xlw", "application/vnd.ms-excel" },

		addFileType("M3U", FILE_TYPE_M3U, "audio/x-mpegurl");
		addFileType("PLS", FILE_TYPE_PLS, "audio/x-scpls");
		addFileType("WPL", FILE_TYPE_WPL, "application/vnd.ms-wpl");

		// compute file extensions list for native Media Scanner
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = sFileTypeMap.keySet().iterator();

		while (iterator.hasNext()) {
			if (builder.length() > 0) {
				builder.append(',');
			}
			builder.append(iterator.next());
		}
		sFileExtensions = builder.toString();
	}

	private static final String UNKNOWN_STRING = "<unknown>";

	private static boolean isAudioFileType(int fileType) {
		return ((fileType >= FIRST_AUDIO_FILE_TYPE && fileType <= LAST_AUDIO_FILE_TYPE) || (fileType >= FIRST_MIDI_FILE_TYPE && fileType <= LAST_MIDI_FILE_TYPE));
	}

	private static boolean isVideoFileType(int fileType) {
		return (fileType >= FIRST_VIDEO_FILE_TYPE && fileType <= LAST_VIDEO_FILE_TYPE);
	}

	private static boolean isImageFileType(int fileType) {
		return (fileType >= FIRST_IMAGE_FILE_TYPE && fileType <= LAST_IMAGE_FILE_TYPE);
	}

	private static boolean isPlayListFileType(int fileType) {
		return (fileType >= FIRST_PLAYLIST_FILE_TYPE && fileType <= LAST_PLAYLIST_FILE_TYPE);
	}
	/**
	 * 根据视频文件路径判断文件类型
	 * @author Sogrey
	 * @date 2015年4月22日
	 * @param path
	 * @return
	 */
	public static MediaFileType getFileType(String path) {
		int lastDot = path.lastIndexOf(".");
		if (lastDot < 0)
			return null;
		return sFileTypeMap.get(path.substring(lastDot + 1).toUpperCase());
	}

	/**
	 * 根据视频文件路径判断文件类型
	 * @author Sogrey
	 * @date 2015年4月22日
	 * @param path
	 * @return
	 */
	public static boolean isVideoFileType(String path) { // 自己增加
		MediaFileType type = getFileType(path);
		if (null != type) {
			return isVideoFileType(type.fileType);
		}
		return false;
	}

	/**
	 * 根据音频文件路径判断文件类型
	 * @author Sogrey
	 * @date 2015年4月22日
	 * @param path
	 * @return
	 */
	public static boolean isAudioFileType(String path) { // 自己增加
		MediaFileType type = getFileType(path);
		if (null != type) {
			return isAudioFileType(type.fileType);
		}
		return false;
	}

	/**
	 * 根据音频文件路径判断文件类型
	 * @author Sogrey
	 * @date 2015年4月22日
	 * @param path
	 * @return
	 */
	public static boolean isImageFileType(String path) { // 自己增加
		MediaFileType type = getFileType(path);
		if (null != type) {
			return isImageFileType(type.fileType);
		}
		return false;
	}

	/**
	 * 根据文件路径判断文件类型
	 * @author Sogrey
	 * @date 2015年4月22日
	 * @param path
	 * @return
	 */
	public static MediaFileTypeList getFileTypeByPath(String path) { // 自己增加
		MediaFileType type = getFileType(path);
		if (null != type) {
			if (isImageFileType(type.fileType)) {
				return MediaFileTypeList.Image;
			} else if (isAudioFileType(type.fileType)) {
				return MediaFileTypeList.Audio;
			} else if (isVideoFileType(type.fileType)) {
				return MediaFileTypeList.Video;
			}
		}
		return MediaFileTypeList.UnKnown;
	}

	/**
	 * 根据文件路径判断文件类型
	 * @author Sogrey
	 * @date 2015年4月22日
	 * @param path
	 * @return
	 */
	public static MediaFileTypeList getFileTypeByMime(String path) { // 自己增加
		String mimeType = FileMIME.GetFileMIME(path);
		int fileType = getFileTypeFormMimeType(mimeType);
		if (isImageFileType(fileType)) {
			return MediaFileTypeList.Image;
		} else if (isAudioFileType(fileType)) {
			return MediaFileTypeList.Audio;
		} else if (isVideoFileType(fileType)) {
			return MediaFileTypeList.Video;
		}
		return MediaFileTypeList.UnKnown;
	}

	/**
	 * 根据mime类型查看文件类型
	 * @author Sogrey
	 * @date 2015年4月22日
	 * @param mimeType
	 * @return
	 */
	public static int getFileTypeFormMimeType(String mimeType) {
		Integer value = sMimeTypeMap.get(mimeType);
		return (value == null ? 0 : value.intValue());
	}
}
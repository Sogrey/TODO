/**
 * @author Sogrey
 * @date 2015年4月29日
 */
package org.sogrey.sogreyframe.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Sogrey
 * @date 2015年4月29日
 */
public class InputStreamUtil {
	/**
	 * 1.InputStream to String
	 * 
	 * @author Sogrey
	 * 
	 * @date 2015年4月29日
	 */

	public static String inputStream2String(InputStream is) {
		return inputStream2String(is, null);
	}

	/**
	 * InputStream to String
	 * 
	 * @author Sogrey
	 * @date 2015-8-19 下午2:19:56
	 * @param is
	 *            InputStream
	 * @param charsetName
	 * @return
	 */
	public static String inputStream2String(InputStream is,String charsetName) {
		BufferedReader reader;
		StringBuilder  sb= new StringBuilder();
		try {
			InputStreamReader isr= null;
			if (TextUtils.isEmpty(charsetName)) {
				isr = new InputStreamReader(is);
			} else {
				isr = new InputStreamReader(is,charsetName);
			}
			reader = new BufferedReader(isr);
			String line= null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != is)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 2.InputStream to String
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param inputStream
	 * @return String
	 */
	public static String readTextFile(InputStream inputStream) {
		ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
		byte                  buf[]       = new byte[1024];
		int                   len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			return outputStream.toString();
		} catch (IOException e) {
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return outputStream.toString();
	}

	/**
	 * 3.Bitmap to InputStream
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bm
	 * @return
	 */
	public static InputStream Bitmap2IS(Bitmap bm) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG,100,baos);
		InputStream sbs= new ByteArrayInputStream(baos.toByteArray());
		return sbs;
	}

	/**
	 * 4.将byte[]转换成InputStream
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param b
	 * @return
	 */
	public static InputStream Byte2InputStream(byte[] b) {
		ByteArrayInputStream bais= new ByteArrayInputStream(b);
		return bais;
	}

	/**
	 * 5.将InputStream转换成byte[]
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param is
	 * @return
	 */
	public static byte[] InputStream2Bytes(InputStream is) {
		String str      = "";
		byte[] readByte = new byte[1024];
		int    readCount= -1;
		try {
			while ((readCount = is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 6.将Bitmap转换成InputStream
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bm
	 * @return
	 */
	public static InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG,100,baos);
		InputStream is= new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 7.将Bitmap转换成InputStream
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bm
	 * @param quality
	 * @return
	 */
	public static InputStream Bitmap2InputStream(Bitmap bm,int quality) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG,quality,baos);
		InputStream is= new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 8.将InputStream转换成Bitmap
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param is
	 * @return
	 */
	public static Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * 9.Drawable转换成InputStream
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param d
	 * @return
	 */
	public static InputStream Drawable2InputStream(Drawable d) {
		Bitmap bitmap= drawable2Bitmap(d);
		return Bitmap2InputStream(bitmap);
	}

	/**
	 * 10.InputStream转换成Drawable
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param is
	 * @return
	 */
	public static Drawable InputStream2Drawable(InputStream is) {
		Bitmap bitmap= InputStream2Bitmap(is);
		return bitmap2Drawable(bitmap);
	}

	/**
	 * 11.Drawable转换成byte[]
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param d
	 * @return
	 */
	public static byte[] Drawable2Bytes(Drawable d) {
		Bitmap bitmap= drawable2Bitmap(d);
		return Bitmap2Bytes(bitmap);
	}

	/**
	 * 12.byte[]转换成Drawable
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param b
	 * @return
	 */
	public static Drawable Bytes2Drawable(byte[] b) {
		Bitmap bitmap= Bytes2Bitmap(b);
		return bitmap2Drawable(bitmap);
	}

	/**
	 * 13.Bitmap转换成byte[]
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG,100,baos);
		return baos.toByteArray();
	}

	/**
	 * 14.byte[]转换成Bitmap
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param b
	 * @return
	 */
	public static Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b,0,b.length);
		}
		return null;
	}

	/**
	 * 15.Drawable转换成Bitmap
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity()!=PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
																  : Bitmap.Config.RGB_565);
		Canvas canvas= new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 16.Bitmap转换成Drawable
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd= new BitmapDrawable(bitmap);
		Drawable       d = (Drawable) bd;
		return d;
	}

	/**
	 * 17.将view转为bitmap
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param view
	 * @return
	 */

	public static Bitmap getBitmapFromView(View view) {
		// Define a bitmap with the same size as the view
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),
													view.getHeight(),Bitmap.Config.ARGB_8888);
		// Bind a canvas to it
		Canvas canvas= new Canvas(returnedBitmap);
		// Get the view's background
		Drawable bgDrawable= view.getBackground();
		if (bgDrawable != null)
			// has background drawable, then draw it on the canvas
			bgDrawable.draw(canvas);
		else
			// does not have background drawable, then draw white background on
			// the canvas
			canvas.drawColor(Color.WHITE);
		// draw the view on the canvas
		view.draw(canvas);
		// return the bitmap
		return returnedBitmap;
	}

	/**
	 * 18.将view转为bitmap
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param view
	 * @return
	 */

	public static Bitmap viewToBitmap(View view) {
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bm= view.getDrawingCache();
		return bm;
	}

	/**
	 * ==============参考资料===================
	 * 
	 * 1.http://stackoverflow.com/questions/7200535/how-to-convert-views-to-
	 * bitmap
	 * 
	 * 2.http://stackoverflow.com/questions/5536066/convert-view-to-bitmap-on-
	 * android/9595919#9595919
	 * 
	 * 3.http://stackoverflow.com/questions/12402392/android-converting-xml-view
	 * -to-bitmap-without-showing-it
	 */

	/**
	 * 19.图片缩放与压缩-按大小缩放
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param srcPath
	 * @return
	 */

	public static Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts= new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap= BitmapFactory.decodeFile(srcPath,newOpts); // 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f; // 这里设置高度为800f
		float ww = 480f; // 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1; // be=1表示不缩放
		if (w > h && w > ww) { // 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) { // 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be; // 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
		return compressImage(bitmap); // 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 20.图片质量压缩
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param image
	 * @return
	 */

	private static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG,100,baos); // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset(); // 重置baos即清空baos
			image.compress(CompressFormat.JPEG,options,baos); // 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10; // 每次都减少10
		}
		ByteArrayInputStream isBm  = new ByteArrayInputStream(baos.toByteArray()); // 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap               bitmap= BitmapFactory.decodeStream(isBm,null,null); // 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 21.图片按比例大小压缩
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param image
	 * @return
	 */

	public static Bitmap comp(Bitmap image) {
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG,100,baos);
		if (baos.toByteArray().length / 1024 > 1024) { // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset(); // 重置baos即清空baos
			image.compress(CompressFormat.JPEG,50,baos); // 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream  isBm   = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts= new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap= BitmapFactory.decodeStream(isBm,null,newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f; // 这里设置高度为800f
		float ww = 480f; // 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1; // be=1表示不缩放
		if (w > h && w > ww) { // 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) { // 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be; // 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm,null,newOpts);
		return compressImage(bitmap); // 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 22.图片转为文件
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bmp
	 * @param filename
	 * @return
	 */

	public static boolean saveBitmap2file(Bitmap bmp,String filename) {
		CompressFormat format = CompressFormat.JPEG;
		int            quality= 100;
		OutputStream   stream = null;
		try {
			stream = new FileOutputStream("/sdcard/"+filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bmp.compress(format, quality, stream);
	}

	/**
	 * 23.屏幕截屏方法<br>
	 * 屏幕截屏方法 获取当前屏幕bitmap,转换成bytes[] 调用 UI分享方法
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param context
	 * @param v
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap printscreen_share(Context context,View v) {
		Activity activity= ((Activity) context);
		View     view1   = activity.getWindow().getDecorView();
		Display  display = activity.getWindowManager().getDefaultDisplay();
		view1.layout(0, 0, display.getWidth(), display.getHeight());
		view1.setDrawingCacheEnabled(true);
		Bitmap bitmap= Bitmap.createBitmap(view1.getDrawingCache());
		return bitmap;
	}

	/**
	 * 24.图片转为文件
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bmp
	 * @param savePath
	 * @return
	 */

	@SuppressLint("NewApi")
	public static boolean saveBitmap2filePath(Bitmap bmp,String savePath) {
		CompressFormat format = CompressFormat.PNG;
		int            quality= 100;
		OutputStream   stream = null;
		try {
			// 判断SDcard状态
			if (!Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				// 错误提示
				return false;
			}
			// 检查SDcard空间
			File SDCardRoot= Environment.getExternalStorageDirectory();
			if (SDCardRoot.getFreeSpace() < 10000) {
				// 弹出对话框提示用户空间不够
				Log.e("Utils","存储空间不够");
				return false;
			}
			// 在SDcard创建文件夹及文件
			File bitmapFile= new File(savePath);
			bitmapFile.getParentFile().mkdirs(); // 创建文件夹
			stream = new FileOutputStream(savePath); // "/sdcard/"
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bmp.compress(format, quality, stream);
	}

	/**
	 * 25.下载图片
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param params
	 * @return
	 */

	public static Bitmap loadImage(String... params) {
		InputStream is    = null;
		Bitmap      bitmap= null;
		try {
			URL               url = new URL(params[0]);
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			if (HttpURLConnection.HTTP_OK!=conn.getResponseCode()) {
				// 网络连接异常
				Log.e("","loadImage连接异常");
				return null;
			}
			is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	/**
	 * 26.将字符串转换成Bitmap类型
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param string
	 * @return
	 */

	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap= null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string,Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray,0,
												   bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 27.将Bitmap转换成字符串
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param bitmap
	 * @return
	 */

	public static String bitmaptoString(Bitmap bitmap) {
		// 将Bitmap转换成字符串
		String                string = null;
		ByteArrayOutputStream bStream= new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG,100,bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes,Base64.DEFAULT);
		return string;
	}

	/**
	 * 28.byte[]转为文件
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param b
	 * @param filePath
	 * @return
	 */

	@SuppressLint("NewApi")
	public static File getFileFromBytes(byte[] b,String filePath) {
		BufferedOutputStream stream= null;
		File                 file  = null;
		try {
			// 判断SDcard状态
			if (!Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				// 错误提示
				return null;
			}
			// 检查SDcard空间
			File SDCardRoot= Environment.getExternalStorageDirectory();
			if (SDCardRoot.getFreeSpace() < 10000) {
				// 弹出对话框提示用户空间不够
				Log.e("Utils","存储空间不够");
				return null;
			}
			// 在SDcard创建文件夹及文件
			File bitmapFile= new File(filePath);
			bitmapFile.getParentFile().mkdirs(); // 创建文件夹
			FileOutputStream fstream= new FileOutputStream(bitmapFile);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 29.图片缩放
	 * 
	 * @author Sogrey
	 * @date 2015年5月20日
	 * @param photo
	 * @param newHeight
	 * @param context
	 * @return
	 */

	public static Bitmap scaleDownBitmap(Bitmap photo,int newHeight,
										 Context context) {
		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;
		int h = (int) (newHeight * densityMultiplier);
		int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));
		photo = Bitmap.createScaledBitmap(photo,w,h,true);
		return photo;
	}

}

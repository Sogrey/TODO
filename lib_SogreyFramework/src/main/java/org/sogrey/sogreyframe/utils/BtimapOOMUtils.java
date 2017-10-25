package org.sogrey.sogreyframe.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * 解决溢出问题;
 * 
 */
public class BtimapOOMUtils {
	
	/**
	 * 根据文件路径创建缩略图（不生成新文件）
	 * @author Sogrey
	 * @date 2015年6月30日
	 * @param filePath
	 * @return
	 */
	public static Bitmap createImageThumbnail(String filePath) {
		Bitmap                bitmap= null;
		BitmapFactory.Options opts  = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath,opts);

		opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
		opts.inJustDecodeBounds = false;

		try {
			bitmap = BitmapFactory.decodeFile(filePath,opts);
		} catch (Exception e) {
		}
		return bitmap;
	}

	private static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w/minSideLength),Math.floor(h/minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	
	@SuppressLint("NewApi")
	public static String uri2filePath(Uri uri,Activity activity) {
		String        path    = "";
		final boolean isKitKat=Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT;
		if (isKitKat&&DocumentsContract.isDocumentUri(activity,uri)) {
			String   wholeID= DocumentsContract.getDocumentId(uri);
			String   id     = wholeID.split(":")[1];
			String[] column = { MediaStore.Images.Media.DATA };
			String   sel    =MediaStore.Images.Media._ID+"=?";
			Cursor cursor = activity.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,column,sel,
					new String[] { id },null);
			int columnIndex = cursor.getColumnIndex(column[0]);
			if (cursor.moveToFirst()) {
				path = cursor.getString(columnIndex);
			}
			cursor.close();
		} 
		return path;
	}
}

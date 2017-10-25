package org.sogrey.sogreyframe.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频相关工具类
 * Created by Sogrey on 2016/12/26.
 */

public class VideoUtils {
    private Context mContext;
    private List<Bitmap> bitmaps;

    public VideoUtils(Context context) {
        mContext = context;
    }

    /**
     * Android 获取视频关键帧代码
     *
     * @param uri 视频uri
     * @param num 截图数
     */
    public List<Bitmap> getVideoThumbnail(Uri uri,int num) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(mContext, uri);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int seconds = Integer.valueOf(time) / 1000;
            int timeS = Integer.valueOf(time) / 10;
            for (int i = 1; i <= num; i++) {
                bitmap = retriever.getFrameAtTime(i * timeS * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                if (bitmaps == null)
                    bitmaps = new ArrayList<>();
                if (bitmap != null)
                    bitmaps.add(bitmap);
            }
            return bitmaps;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

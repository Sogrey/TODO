//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bumptech.glide.load.engine.cache;

import android.util.Log;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.disklrucache.DiskLruCache.Editor;
import com.bumptech.glide.disklrucache.DiskLruCache.Value;
import com.bumptech.glide.load.Key;

import java.io.File;
import java.io.IOException;

public class DiskLruCacheWrapper implements DiskCache {
    private static final String               TAG        = "DiskLruCacheWrapper";
    private static final int                  APP_VERSION= 1;
    private static final int                  VALUE_COUNT= 1;
    private static       DiskLruCacheWrapper  wrapper    = null;
    private final        DiskCacheWriteLocker writeLocker= new DiskCacheWriteLocker();
    private final SafeKeyGenerator safeKeyGenerator;
    private final File             directory;
    private final int              maxSize;
    private       DiskLruCache     diskLruCache;

    public static synchronized DiskCache get(File directory,int maxSize) {
        if(wrapper == null) {
            wrapper = new DiskLruCacheWrapper(directory,maxSize);
        }

        return wrapper;
    }

    protected DiskLruCacheWrapper(File directory,int maxSize) {
        this.directory = directory;
        this.maxSize = maxSize;
        this.safeKeyGenerator = new SafeKeyGenerator();
    }

    private synchronized DiskLruCache getDiskCache() throws IOException {
        if(this.diskLruCache == null) {
            this.diskLruCache = DiskLruCache.open(this.directory,1,1,(long)this.maxSize);
        }

        return this.diskLruCache;
    }

    private synchronized void resetDiskCache() {
        this.diskLruCache = null;
    }

    public File get(Key key) {
        String safeKey= this.safeKeyGenerator.getSafeKey(key);
        File   result = null;

        try {
            Value e= this.getDiskCache().get(safeKey);
            if(e != null) {
                result = e.getFile(0);
            }
        } catch (IOException var5) {
            if(Log.isLoggable("DiskLruCacheWrapper",Log.WARN)) {
                Log.w("DiskLruCacheWrapper","Unable to get from disk cache",var5);
            }
        }

        return result;
    }

    public void put(Key key,Writer writer) {
        String safeKey= this.safeKeyGenerator.getSafeKey(key);
        this.writeLocker.acquire(key);

        try {
            Editor e= this.getDiskCache().edit(safeKey);
            if(e != null) {
                try {
                    File file= e.getFile(0);
                    if(writer.write(file)) {
                        e.commit();
                    }
                } finally {
                    e.abortUnlessCommitted();
                }
            }
        } catch (IOException var15) {
            if(Log.isLoggable("DiskLruCacheWrapper",Log.WARN)) {
                Log.w("DiskLruCacheWrapper","Unable to put to disk cache",var15);
            }
        } finally {
            this.writeLocker.release(key);
        }

    }

    public void delete(Key key) {
        String safeKey= this.safeKeyGenerator.getSafeKey(key);

        try {
            this.getDiskCache().remove(safeKey);
        } catch (IOException var4) {
            if(Log.isLoggable("DiskLruCacheWrapper",Log.WARN)) {
                Log.w("DiskLruCacheWrapper","Unable to delete from disk cache",var4);
            }
        }

    }

    public synchronized void clear() {
        try {
            this.getDiskCache().delete();
            this.resetDiskCache();
        } catch (IOException var2) {
            if(Log.isLoggable("DiskLruCacheWrapper",Log.WARN)) {
                Log.w("DiskLruCacheWrapper","Unable to clear disk cache",var2);
            }
        }

    }
}

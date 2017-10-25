//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;

import java.io.File;

public interface DiskCache {
    File get(Key var1);

    void put(Key var1,DiskCache.Writer var2);

    void delete(Key var1);

    void clear();

    public interface Writer {
        boolean write(File var1);
    }

    public interface Factory {
        int    DEFAULT_DISK_CACHE_SIZE= 262144000;
        String DEFAULT_DISK_CACHE_DIR = "image_manager_disk_cache";

        DiskCache build();
    }
}

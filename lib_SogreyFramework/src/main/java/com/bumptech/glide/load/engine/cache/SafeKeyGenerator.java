//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.EngineKey;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SafeKeyGenerator {
    private static final Map<String, String>   loadedMap       = new HashMap<>();
    private final        LruCache<Key, String> loadIdToSafeHash= new LruCache(1000);

    SafeKeyGenerator() {
    }

    /**
     *
     * @param url 圖片原地址
     * @return 下載到本地的位置
     */
    public static String getLocalPathByUrl(String url) {
        return loadedMap.get(url);
    }

    public String getSafeKey(Key key) {
        LruCache e= this.loadIdToSafeHash;
        String   safeKey;
        synchronized (this.loadIdToSafeHash) {
            safeKey = (String) this.loadIdToSafeHash.get(key);
        }

        if (safeKey == null) {
            try {
                MessageDigest e1= MessageDigest.getInstance("SHA-256");
                key.updateDiskCacheKey(e1);
                safeKey = Util.sha256BytesToHex(e1.digest());
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
            } catch (NoSuchAlgorithmException var8) {
                var8.printStackTrace();
            }

            e = this.loadIdToSafeHash;
            synchronized (this.loadIdToSafeHash) {
                this.loadIdToSafeHash.put(key, safeKey);
                EngineKey engineKey= (EngineKey)key;
                String    id       = engineKey.getId();
                this.loadedMap.put(id, safeKey);
            }
        }

        return safeKey;
    }
}

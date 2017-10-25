//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class OriginalKey implements Key {
    private String id       ="";
    private Key    signature=null;

    public OriginalKey(String id,Key signature) {
        this.id = id;
        this.signature = signature;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            OriginalKey that= (OriginalKey)o;
            return !this.id.equals(that.id)?false:this.signature.equals(that.signature);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + this.signature.hashCode();
        return result;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        messageDigest.update(this.id.getBytes("UTF-8"));
        this.signature.updateDiskCacheKey(messageDigest);
    }

    public String getId() {
        return this.id;
    }
}

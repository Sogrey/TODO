//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class EngineKey implements Key {
    private static final String EMPTY_LOG_STRING= "";
    private final String             id;
    private final int                width;
    private final int                height;
    private final ResourceDecoder    cacheDecoder;
    private final ResourceDecoder    decoder;
    private final Transformation     transformation;
    private final ResourceEncoder    encoder;
    private final ResourceTranscoder transcoder;
    private final Encoder            sourceEncoder;
    private final Key                signature;
    private       String             stringKey;
    private       int                hashCode;
    private       Key                originalKey;

    public EngineKey(String id,Key signature,int width,int height,
                     ResourceDecoder cacheDecoder,ResourceDecoder decoder,
                     Transformation transformation,ResourceEncoder encoder,
                     ResourceTranscoder transcoder,Encoder sourceEncoder) {
        this.id = id;
        this.signature = signature;
        this.width = width;
        this.height = height;
        this.cacheDecoder = cacheDecoder;
        this.decoder = decoder;
        this.transformation = transformation;
        this.encoder = encoder;
        this.transcoder = transcoder;
        this.sourceEncoder = sourceEncoder;
    }

    public String getId() {
        return this.id;
    }

    public Key getOriginalKey() {
        if (this.originalKey == null) {
            this.originalKey = new OriginalKey(this.id,this.signature);
        }

        return this.originalKey;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            EngineKey engineKey= (EngineKey) o;
            return !this.id.equals(engineKey.id) ? false : (!this.signature.equals(engineKey.signature) ? false : (this.height != engineKey.height ? false : (this.width != engineKey.width ? false : (this.transformation == null ^ engineKey.transformation == null ? false : (this.transformation != null && !this.transformation.getId().equals(engineKey.transformation.getId()) ? false : (this.decoder == null ^ engineKey.decoder == null ? false : (this.decoder != null && !this.decoder.getId().equals(engineKey.decoder.getId()) ? false : (this.cacheDecoder == null ^ engineKey.cacheDecoder == null ? false : (this.cacheDecoder != null && !this.cacheDecoder.getId().equals(engineKey.cacheDecoder.getId()) ? false : (this.encoder == null ^ engineKey.encoder == null ? false : (this.encoder != null && !this.encoder.getId().equals(engineKey.encoder.getId()) ? false : (this.transcoder == null ^ engineKey.transcoder == null ? false : (this.transcoder != null && !this.transcoder.getId().equals(engineKey.transcoder.getId()) ? false : (this.sourceEncoder == null ^ engineKey.sourceEncoder == null ? false : this.sourceEncoder == null || this.sourceEncoder.getId().equals(engineKey.sourceEncoder.getId())))))))))))))));
        } else {
            return false;
        }
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.id.hashCode();
            this.hashCode = 31 * this.hashCode + this.signature.hashCode();
            this.hashCode = 31 * this.hashCode + this.width;
            this.hashCode = 31 * this.hashCode + this.height;
            this.hashCode = 31 * this.hashCode + (this.cacheDecoder != null ? this.cacheDecoder.getId().hashCode() : 0);
            this.hashCode = 31 * this.hashCode + (this.decoder != null ? this.decoder.getId().hashCode() : 0);
            this.hashCode = 31 * this.hashCode + (this.transformation != null ? this.transformation.getId().hashCode() : 0);
            this.hashCode = 31 * this.hashCode + (this.encoder != null ? this.encoder.getId().hashCode() : 0);
            this.hashCode = 31 * this.hashCode + (this.transcoder != null ? this.transcoder.getId().hashCode() : 0);
            this.hashCode = 31 * this.hashCode + (this.sourceEncoder != null ? this.sourceEncoder.getId().hashCode() : 0);
        }

        return this.hashCode;
    }

    public String toString() {
        if (this.stringKey == null) {
            this.stringKey = "EngineKey{" + this.id + '+' + this.signature + "+[" + this.width + 'x' + this.height + "]+" + '\'' + (this.cacheDecoder != null ? this.cacheDecoder.getId() : "") + '\'' + '+' + '\'' + (this.decoder != null ? this.decoder.getId() : "") + '\'' + '+' + '\'' + (this.transformation != null ? this.transformation.getId() : "") + '\'' + '+' + '\'' + (this.encoder != null ? this.encoder.getId() : "") + '\'' + '+' + '\'' + (this.transcoder != null ? this.transcoder.getId() : "") + '\'' + '+' + '\'' + (this.sourceEncoder != null ? this.sourceEncoder.getId() : "") + '\'' + '}';
        }

        return this.stringKey;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        byte[] dimensions = ByteBuffer.allocate(8).putInt(this.width).putInt(this.height).array();
        this.signature.updateDiskCacheKey(messageDigest);
        messageDigest.update(this.id.getBytes("UTF-8"));
        messageDigest.update(dimensions);
        messageDigest.update((this.cacheDecoder != null ? this.cacheDecoder.getId() : "").getBytes("UTF-8"));
        messageDigest.update((this.decoder != null ? this.decoder.getId() : "").getBytes("UTF-8"));
        messageDigest.update((this.transformation != null ? this.transformation.getId() : "").getBytes("UTF-8"));
        messageDigest.update((this.encoder != null ? this.encoder.getId() : "").getBytes("UTF-8"));
        messageDigest.update((this.sourceEncoder != null ? this.sourceEncoder.getId() : "").getBytes("UTF-8"));
    }
}

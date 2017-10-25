package org.sogrey.sogreyframe.utils;

/**
 * 作者：lhj on 2016/8/23 11:15
 * 邮箱：hujiang_2015@yeah.net
 * function:
 */
import android.app.Activity;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

/**
 * Small helper class to manage text/icon around fingerprint authentication UI.
 */
public class FingerPrintUiHelper {

    private CancellationSignal       signal;
    private FingerprintManagerCompat fingerprintManager;

    public FingerPrintUiHelper(Activity activity) {
        signal = new CancellationSignal();
        fingerprintManager = FingerprintManagerCompat.from(activity);
    }

    public void startFingerPrintListen(FingerprintManagerCompat.AuthenticationCallback callback) {
        fingerprintManager.authenticate(null, 0, signal, callback, null);
    }

    public void stopsFingerPrintListen() {
        signal.cancel();
        signal = null;
    }
}


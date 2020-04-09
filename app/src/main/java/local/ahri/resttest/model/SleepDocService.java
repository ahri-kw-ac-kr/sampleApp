package local.ahri.resttest.model;

import android.annotation.SuppressLint;

import io.reactivex.Completable;
import io.reactivex.Observable;
import local.ahri.resttest.sleep_doc.dto.RawdataDTO;
import local.ahri.resttest.sleep_doc.SleepDoc;

public class SleepDocService {
    private String macAddress;
    private boolean isConnected;
    private static SleepDoc sleepDoc;
    private static SleepDocService sleepDocService;

    @SuppressLint("CheckResult")
    public Observable<RawdataDTO> getRawdata() {
        return sleepDoc.getRawdata();
    }

    private void setMacAddress(String _macAddress) {
        macAddress = _macAddress;
    }
    public Completable connect() {
        return Completable.fromRunnable(() -> sleepDoc = new SleepDoc(macAddress));
    }
    public Completable connect(String macAddress) {
        this.setMacAddress(macAddress);
        return connect();
    }

    public static synchronized SleepDocService getInstance() {
        if (sleepDocService == null) {
            sleepDocService = new SleepDocService();
        }
        return sleepDocService;
    }

    // Singleton
    private SleepDocService() {}
}

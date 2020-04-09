package local.ahri.resttest.model;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.Observable;
import local.ahri.resttest.sleep_doc.dto.RawdataDTO;
import local.ahri.resttest.sleep_doc.SleepDoc;

public class SleepDocService {
    private static SleepDoc sleepDoc;
    private static SleepDocService sleepDocService;

    public Observable<RawdataDTO> getRawdata() {
        return sleepDoc.getRawdata();
    }

    public Completable connect(String macAddress) {
        Log.i("SleepdocService", "connect start");
        sleepDoc = new SleepDoc(macAddress);
        return sleepDoc.connect();
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

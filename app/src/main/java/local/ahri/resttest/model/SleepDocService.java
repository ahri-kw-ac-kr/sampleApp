package local.ahri.resttest.model;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;

import io.reactivex.Observable;
import local.ahri.resttest.model.dto.RawdataDTO;
import local.ahri.resttest.sleep_doc.SleepDoc;

public class SleepDocService {
    private static String macAddress;
    private static SleepDoc sleepDoc;
    private static SleepDocService sleepDocService;

    public Observable<RawdataDTO> getRawdata() {
        return Observable.create(observer -> {
            sleepDoc.getRawdata()
                .subscribe(bytes -> observer.onNext(RawdataDTO.ParseBytearray(bytes)), observer::onError, observer::onComplete);
        });
    }

    public static void setMacAddress(String _macAddress) {
        macAddress = _macAddress;
    }

    public static synchronized SleepDocService getInstance() throws Exception {
        if (sleepDocService == null) {
            if (macAddress == null) { throw new Exception("맥어드레스 설정해야됨 setMacAddress()먼저 호출하기"); }
            sleepDoc = new SleepDoc(macAddress);
            sleepDocService = new SleepDocService();
        }
        return sleepDocService;
    }

    // Singleton
    private SleepDocService() {}
}

package local.ahri.resttest.model;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import local.ahri.resttest.exceptions.MacAddressIsNotProvidedException;
import local.ahri.resttest.model.dto.RawdataDTO;
import local.ahri.resttest.model.dto.SyncDataDTO;
import local.ahri.resttest.sleep_doc.SleepDoc;
import local.ahri.resttest.exceptions.DataIsTooShortException;
import local.ahri.resttest.exceptions.ZeroLengthException;

import static local.ahri.resttest.sleep_doc.Spec.SYNC_DATA_BYTE_LENGTH;

public class SleepDocService {
    private static String macAddress;
    private static SleepDoc sleepDoc;
    private static SleepDocService sleepDocService;

    @SuppressLint("CheckResult")
    public Observable<RawdataDTO> getRawdata() {
        return Observable.create(observer ->
            sleepDoc.getRawdata()
                    .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes ->  {
                    try {
                        Log.i("SleepDocService", "Sync data is arrived");
                        SyncDataDTO syncDataDTO = SyncDataDTO.ParseByteArray(bytes);
                        for (final RawdataDTO rawdataDTO : syncDataDTO.rawdataDTOArray) {
                            Log.i("SleepDocService", "Sync data is parsed into rawdata");
                            observer.onNext(rawdataDTO);
                        }
                    } catch (ZeroLengthException e) {
                        Log.i("SleepDocService", "Sync data has 0 length, Sync is done.");
                        observer.onComplete();
                    } catch (DataIsTooShortException e) {
                        Log.i("SleepDocService", "Request next data");
                        sleepDoc.prepareNext();
                    }
                }, observer::onError, observer::onComplete)
        );
    }

    public static void setMacAddress(String _macAddress) {
        macAddress = _macAddress;
    }

    public static synchronized SleepDocService getInstance() throws MacAddressIsNotProvidedException {
        if (sleepDocService == null) {
            if (macAddress == null) { throw new MacAddressIsNotProvidedException("맥어드레스 설정해야됨 setMacAddress()먼저 호출하기"); }
            sleepDoc = new SleepDoc(macAddress);
            sleepDocService = new SleepDocService();
        }
        return sleepDocService;
    }

    // Singleton
    private SleepDocService() {}
}

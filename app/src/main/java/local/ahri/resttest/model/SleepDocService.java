package local.ahri.resttest.model;

import android.annotation.SuppressLint;
import android.app.Service;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;

import java.io.ByteArrayOutputStream;

import io.reactivex.Observable;
import local.ahri.resttest.model.dto.RawdataDTO;
import local.ahri.resttest.model.dto.SyncDataDTO;
import local.ahri.resttest.sleep_doc.SleepDoc;
import local.ahri.resttest.sleep_doc.command.Command;
import local.ahri.resttest.sleep_doc.exceptions.PrepareNextException;
import local.ahri.resttest.sleep_doc.exceptions.SyncDoneException;
import local.ahri.resttest.sleep_doc.uuid.CharacteristicUUID;
import local.ahri.resttest.sleep_doc.uuid.ServiceUUID;

import static local.ahri.resttest.sleep_doc.Spec.RAWDATA_BYTE_LENGTH;
import static local.ahri.resttest.sleep_doc.Spec.SYNC_DATA_BYTE_LENGTH;

public class SleepDocService {
    private static String macAddress;
    private static SleepDoc sleepDoc;
    private static SleepDocService sleepDocService;
    private ByteArrayOutputStream syncDataStream = new ByteArrayOutputStream();


    @SuppressLint("CheckResult")
    public Observable<RawdataDTO> getRawdata() {
        return Observable.create(observer -> {
            sleepDoc.getRawdata()
                .subscribe(bytes ->  {
                    Log.i("SleepDocService", "readDataSize :"+syncDataStream.size() + "  SleepdocDataSize : "+ SYNC_DATA_BYTE_LENGTH);
                    try {
                        SyncDataDTO syncDataDTO = SyncDataDTO.ParseByteArray(bytes);
                        for (final RawdataDTO rawdataDTO : syncDataDTO.rawdataDTOArray) {
                            observer.onNext(rawdataDTO);
                        }
                    } catch (SyncDoneException e) {
                        observer.onComplete();
                    } catch (PrepareNextException e) {
                        sleepDoc.prepareNext();
                    }

                }, observer::onError, observer::onComplete);
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

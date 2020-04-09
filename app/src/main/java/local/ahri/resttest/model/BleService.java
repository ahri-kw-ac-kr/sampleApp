package local.ahri.resttest.model;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;

import java.util.List;

import io.reactivex.Observable;
import local.ahri.resttest.exceptions.BleScanFailException;
import local.ahri.resttest.model.dto.BleDeviceDTO;

public class BleService {
    private BleManager bleManager;
    private static BleService bleService;

    public static synchronized BleService getInstance() {
        if (bleService == null) {
            bleService = new BleService();
        }
        return bleService;
    }

    public Observable<BleDeviceDTO> scanBle() {
        return Observable.create(observer -> {
            BleManager.getInstance().scan(new BleScanCallback() {
                @Override
                public void onScanStarted(boolean success) {
                    if (!success) {
                        observer.onError(new BleScanFailException("Failed to start scanning"));
                    }
                }
                @Override
                public void onLeScan(BleDevice bleDevice) {
                    super.onLeScan(bleDevice);
                }

                @Override
                public void onScanning(BleDevice bleDevice) {
                    BleDeviceDTO bleDeviceDTO = new BleDeviceDTO(bleDevice);
                    observer.onNext(bleDeviceDTO);
                }

                @Override
                public void onScanFinished(List<BleDevice> scanResultList) {
                    observer.onComplete();
                }
            });
        });
    }

    // Singleton
    private BleService() {
        this.bleManager = BleManager.getInstance();
    }
}

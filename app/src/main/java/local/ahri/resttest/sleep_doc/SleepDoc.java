package local.ahri.resttest.sleep_doc;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.lang.reflect.Method;

import io.reactivex.Completable;
import io.reactivex.Observable;
import local.ahri.resttest.exceptions.DataIsTooShortException;
import local.ahri.resttest.exceptions.ZeroLengthException;
import local.ahri.resttest.sleep_doc.dto.RawdataDTO;
import local.ahri.resttest.sleep_doc.dto.SyncDataDTO;
import local.ahri.resttest.sleep_doc.command.Command;
import local.ahri.resttest.sleep_doc.uuid.CharacteristicUUID;
import local.ahri.resttest.sleep_doc.uuid.DescriptorUUID;
import local.ahri.resttest.sleep_doc.uuid.ServiceUUID;

public class SleepDoc {
    private BleManager bleManager;
    private String macAddress;
    private BleDevice bleDevice;
    private BluetoothGatt gatt;
    private boolean isConnected = false;

    public SleepDoc(String macAddress) {
        this.macAddress = macAddress;
        bleManager = BleManager.getInstance();
    }

    public Completable connect() {
        return Completable.create(observer -> {
            Log.i("Sleepdoc", "connect start");
            bleManager.connect(macAddress, new BleGattCallback() {
                @Override
                public void onStartConnect() {
                    Log.i("SleepDoc", "연결시작");
                }

                @Override
                public void onConnectFail(BleDevice _bleDevice, BleException exception) {
                    Log.i("SleepDoc", "연결실패");
                    observer.onError(new Exception(String.format("Connect fail: %s", macAddress)));
                }

                @Override
                public void onConnectSuccess(BleDevice _bleDevice, BluetoothGatt _gatt, int status) {
                    Log.i("SleepDoc", "연결성공");
                    bleDevice = _bleDevice;
                    isConnected = true;
                    gatt = bleManager.getBluetoothGatt(bleDevice);
                    observer.onComplete();
                }

                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    Log.i("SleepDoc", "연결해제");
                    isConnected = false;
                }
            });
        });
    }

    public Observable<RawdataDTO> getRawdata() {
        if (!isConnected) {
            Log.d("SleepDoc", "이거 나오면 안됨");
        }
        refreshDeviceCache(gatt);
        BluetoothGattCharacteristic syncControlChar = gatt.getService(ServiceUUID.SYNC).getCharacteristic(CharacteristicUUID.SYNC_CONTROL);
        BluetoothGattDescriptor descriptor = syncControlChar.getDescriptor(DescriptorUUID.CLIENT_CHARACTERISTIC_CONFIGURATION);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        gatt.writeDescriptor(descriptor);

        return Observable.create(observer -> {
            bleManager.notify(bleDevice, ServiceUUID.SYNC.toString(), CharacteristicUUID.SYNC_CONTROL.toString(), new BleNotifyCallback() {
                //Sync Notify ON Success
                @Override
                public void onNotifySuccess() {
                    Log.i("SleepDoc", "notify SYNC_CONTROL Success");
                    bleManager.write(bleDevice, ServiceUUID.SYNC.toString(), CharacteristicUUID.SYNC_CONTROL.toString(), new byte[]{Command.SYNC_CONTROL_START}, logWriteCallback);
                }

                @Override
                public void onNotifyFailure(BleException exception) {
                    Log.i("SleepDoc", "notify SYNC_CONTROL Fail");
//                    observer.onError(new Exception(exception.getDescription()));
                }
                @Override
                public void onCharacteristicChanged(byte[] data) {
                    Log.i("SleepDoc", "Characteristic Changed");
                    if(data[0] == Command.SYNC_NOTI_DONE) {
                        Log.i("SleepDoc", "Sync Noti Done");
                        observer.onComplete();
                        return;
                    }
                    bleManager.read(bleDevice, ServiceUUID.SYNC.toString(), CharacteristicUUID.SYNC_DATA.toString(), new BleReadCallback() {
                        @Override
                        public void onReadSuccess(byte[] values) {
                            Log.i("SleepDoc", "Read from SleepDoc");
                            try {
                                Log.i("SleepDoc", "Sync data is arrived");
                                SyncDataDTO syncDataDTO = SyncDataDTO.ParseByteArray(values);
                                for (final RawdataDTO rawdataDTO : syncDataDTO.rawdataDTOArray) {
                                    Log.i("SleepDoc", "Sync data is parsed into rawdata");
                                    Log.i("럭스값", Integer.toString(rawdataDTO.getAvgLux()));`
                                    observer.onNext(rawdataDTO);
                                }
                            } catch (ZeroLengthException e) {
                                Log.i("SleepDoc", "Sync data has 0 length, Sync is done.");
                                bleManager.write(bleDevice, ServiceUUID.SYNC.toString(), CharacteristicUUID.SYNC_CONTROL.toString(), new byte[]{Command.SYNC_CONTROL_DONE}, logWriteCallback);
                            } catch (DataIsTooShortException e) {
                                Log.i("SleepDoc", "Request next data");
                                bleManager.write(bleDevice, ServiceUUID.SYNC.toString(), CharacteristicUUID.SYNC_CONTROL.toString(), new byte[]{Command.SYNC_CONTROL_PREPARE_NEXT}, logWriteCallback);
                            }
                        }
                        @Override
                        public void onReadFailure(BleException exception) {
                            Log.i("SleepDoc", "BleRead Callback fail");
                            observer.onError(new Exception(exception.getDescription()));
                        }
                    });
                }
            });
        });
    }

    private synchronized void refreshDeviceCache(BluetoothGatt bluetoothGatt) {
        try {
            final Method refresh = BluetoothGatt.class.getMethod("refresh");
            if (refresh != null && bluetoothGatt != null) {
                boolean success = (Boolean) refresh.invoke(bluetoothGatt);
                Log.i("SleepDoc", "refreshDeviceCache, is success:  " + success);
            }
        } catch (Exception e) {
            Log.i("SleepDoc", "exception occur while refreshing device: " + e.getMessage());
            e.printStackTrace();
        }
    }

    BleWriteCallback logWriteCallback = new BleWriteCallback() {
        @Override
        public void onWriteSuccess(int current, int total, byte[] justWrite) {
            Log.i("SleepDoc",  "write success, current: " + current
                    + " total: " + total
                    + " justWrite: " + justWrite.toString());
        }
        @Override
        public void onWriteFailure(BleException exception) {
            Log.i("SleepDoc", "Write Fail\n"+exception.toString());
        }
    };
}

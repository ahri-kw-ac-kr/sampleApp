package local.ahri.resttest.ui;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.AsyncTask;
import android.os.Bundle;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.RxBleDeviceServices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;


import org.reactivestreams.Subscription;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import local.ahri.resttest.R;
import local.ahri.resttest.databinding.ActivityMainBinding;
import local.ahri.resttest.model.RestfulAPI;
import local.ahri.resttest.model.RestfulAPIService;
import local.ahri.resttest.model.dto.PageDTO;
import local.ahri.resttest.model.dto.RawdataDTO;
import local.ahri.resttest.model.dto.UserDTO;


public class MainActivity extends AppCompatActivity {
    private UUID characteristicUuid;
    final private UUID SYNC_SERVICE_UUID = UUID.fromString("0000FFFA-0000-1000-8000-00805f9b34fb");
    final private UUID SYNC_CONTROL_CHAR_UUID = UUID.fromString("0000FFFA-0000-1000-8000-00805f9b34fb");
    final private UUID SYNC_DATA_CHAR_UUID = UUID.fromString("0000FFFB-0000-1000-8000-00805f9b34fb");
    private UUID BATTERY_SERVICE_UUID = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
    private UUID  BATTERY_CHAR_UUID = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
    private final UUID DEVICE_INFORMATION_SERVICE_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
    private final UUID SW_REVISION_CHAR_UUID = UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb");

    private final UUID GENERAL_SERVICE_UUID = UUID.fromString("0000fffe-0000-1000-8000-00805f9b34fb");
    private final UUID SYS_CMD_CHAR_UUID = UUID.fromString("0000ffff-0000-1000-8000-00805f9b34fb");
    final UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private ByteArrayOutputStream syncDataStream;

    private static final int NOTIFICATION_ID = 999;

    private static final byte SYNC_CONTROL_START = 0x01;
    private static final byte SYNC_CONTROL_PREPARE_NEXT = 0x02;
    private static final byte SYNC_CONTROL_DONE = 0x03;

    private static final byte SYNC_NOTI_READY = 0x11;
    private static final byte SYNC_NOTI_NEXT_READY = 0x12;
    private static final byte SYNC_NOTI_DONE = 0x13;
    private static final byte SYNC_NOTI_ERROR = (byte)0xFF;

    private static final byte SYS_CMD_SET_RTC = (byte)0x06;

    private static final byte SYS_CMD_GET_UUID = (byte)0x0B;

    private RxBleDevice device;

    private RestfulAPIService restfulAPIService;
    private ActivityMainBinding activityMainBinding;
    private RxBleConnection conn;

    public MainActivity() {
        this.restfulAPIService = RestfulAPI.getInstance();
    }

    public void onFabClick(View view) {
        TextView textView = findViewById(R.id.mytext);
        RestfulAPIService restfulAPIService = RestfulAPI.getInstance();
        Log.d("이 에이피아이는 어떤 에이피아이인가", restfulAPIService.toString()+" 토큰은?? "+ RestfulAPI.token);

        restfulAPIService.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::printUsers, Throwable::printStackTrace);
    }

    private void printUsers(PageDTO<UserDTO> body) {
        TextView textView = findViewById(R.id.mytext);
        List<UserDTO> result = body.getContent();
        Log.d("과연과연"," "+ body.toString());
        textView.setText(result.get(0).getFullname());
    }

    private void setText(byte[] data) {
        TextView textView = findViewById(R.id.mytext);
        RawdataDTO rawdataDTO = RawdataDTO.ParseBytearray(data);
        Log.d("과연과연"," "+ rawdataDTO.getAvgLux());
        textView.setText(rawdataDTO.getAvgLux());
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBleClient rxBleClient = RxBleClient.create(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setActivity(this);
        syncDataStream = new ByteArrayOutputStream();

        /*HashMap<String,Object> user = new HashMap<>();
        user.put("username","wawa");
        user.put("fullname","wiwi");
        user.put("password","wowo");*/
        //UserDTO user = new UserDTO(5,"wawa","wiwi",'l',null,null,"wowo",null,null);
        UserDTO user = new UserDTO();
        user.setUsername("wawa");
        user.setFullname("wiwi");
        user.setPassword("wowo");

        restfulAPIService.postAuth(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RestfulAPI::setToken, Throwable::printStackTrace);

        String macAddress = "AA:BB:CC:DD:EE:FF";

        BleManager bManager = BleManager.getInstance();

        BluetoothDevice bluetoothDevice = bManager.getBluetoothAdapter().getRemoteDevice(macAddress);
        BleDevice bleDevice = new BleDevice(bluetoothDevice,0,null,0);
        syncDataStream = new ByteArrayOutputStream();
        int count = 0;

        // characteristic uuid 확인
        ///////////////////////////////////////////// 이걸해줘야 notification이 제대로 온다
        BluetoothGatt gatt = bManager.getBluetoothGatt(bleDevice);
        BluetoothGattCharacteristic syncControlChar = gatt.getService(SYNC_SERVICE_UUID).getCharacteristic(SYNC_CONTROL_CHAR_UUID);
        //SYNC_CONTROL Notify ON
        final UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        BluetoothGattDescriptor descriptor = syncControlChar.getDescriptor(CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        gatt.writeDescriptor(descriptor);
        /////////////////////////////////////////////

        bManager.notify(bleDevice, SYNC_SERVICE_UUID.toString(), SYNC_CONTROL_CHAR_UUID.toString(), new BleNotifyCallback() {
            //Sync Notify ON Success
            @Override
            public void onNotifySuccess() {
                Log.i("GET_DEVICE_DATA", "notify SYNC_CONTROL Success");
                //노티 켜지면 씽크시작 값 쓰기
                bManager.write(bleDevice, SYNC_SERVICE_UUID.toString(), SYNC_CONTROL_CHAR_UUID.toString(), new byte[]{SYNC_CONTROL_START}, syncControlWriteCallback);
            }

            @Override
            public void onNotifyFailure(BleException exception) {
                Log.i("GET_DEVICE_DATA", "notify SYNC_CONTROL Fail");
            }
            @Override
            public void onCharacteristicChanged(byte[] data) {
                Log.i("notify SYNC_CONTROL", data.toString());
                if(data[0]!=SYNC_NOTI_DONE) {
                    bManager.read(bleDevice, SYNC_SERVICE_UUID.toString(), SYNC_DATA_CHAR_UUID.toString(), syncControlReadCallback);
                }
            }
        });
    }

    BleReadCallback syncControlReadCallback = new BleReadCallback() {
        @Override
        public void onReadSuccess(byte[] bytes) {
            int len = bytes[0];
            byte[] data = new byte[24];
            System.arraycopy(bytes, 1, data,0,24);
            RawdataDTO rawdataDTO = RawdataDTO.ParseBytearray(data);
            Log.i("값", String.format("AvgLux: %d", rawdataDTO.getAvgLux()));
        }
        @Override
        public void onReadFailure(BleException exception) {
            Log.d("fail", "fail");
        }
    };

    BleWriteCallback syncControlWriteCallback = new BleWriteCallback() {
        @Override
        public void onWriteSuccess(int current, int total, byte[] justWrite) {
            Log.i("GET_DEVICE_DATA",  "write success, current: " + current
                    + " total: " + total
                    + " justWrite: " + justWrite.toString());
        }
        @Override
        public void onWriteFailure(BleException exception) {
            Log.i("GET_DEVICE_DATA", "Fail\n"+exception.toString());
        }
    };
}

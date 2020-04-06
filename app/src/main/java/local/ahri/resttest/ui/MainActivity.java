package local.ahri.resttest.ui;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.RxBleDeviceServices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    private static final byte SYS_CMD_SET_RTC = (byte)0x06;

    private static final byte SYS_CMD_GET_UUID = (byte)0x0B;

    private RestfulAPIService restfulAPIService;
    private ActivityMainBinding activityMainBinding;

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
        RxBleDevice device = rxBleClient.getBleDevice(macAddress);

        // characteristic uuid 확인
        Observable<RxBleConnection> observable = device.establishConnection(false); // <-- autoConnect flag
        observable
            .flatMapSingle(RxBleConnection::discoverServices)
            .flatMapSingle(rxBleDeviceServices -> rxBleDeviceServices.getCharacteristic(characteristicUuid))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    characteristic -> {
                            TextView textView = findViewById(R.id.mytext);
                            textView.append(characteristic.toString() + "\n");
                    },
                    Throwable::printStackTrace
            );

        // 버전 가져오기
        observable
            .flatMapSingle(rxBleConnection -> rxBleConnection.readCharacteristic(BATTERY_CHAR_UUID))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    data -> {
                        Log.i("배터리", "battery: " + data[0] + "%, version: ");
                    },
                    Throwable::printStackTrace
            );;



    }
}

package local.ahri.resttest.ui;


import android.bluetooth.BluetoothGatt;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.RxBleDevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;
import java.util.UUID;

import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import local.ahri.resttest.R;
import local.ahri.resttest.databinding.ActivityMainBinding;
import local.ahri.resttest.model.RestfulAPI;
import local.ahri.resttest.model.RestfulAPIService;
import local.ahri.resttest.model.dto.PageDTO;
import local.ahri.resttest.model.dto.RawdataDTO;
import local.ahri.resttest.model.dto.UserDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    final private UUID SYNC_SERVICE_UUID = UUID.fromString("0000FFFA-0000-1000-8000-00805f9b34fb");
    final private UUID SYNC_CONTROL_CHAR_UUID = UUID.fromString("0000FFFA-0000-1000-8000-00805f9b34fb");
    final private UUID SYNC_DATA_CHAR_UUID = UUID.fromString("0000FFFB-0000-1000-8000-00805f9b34fb");

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
        Disposable disposable = device.establishConnection(false) // <-- autoConnect flag
                .flatMapSingle(rxBleConnection -> rxBleConnection.readCharacteristic(SYNC_SERVICE_UUID))
                .subscribe(this::setText, Throwable::printStackTrace);
        disposable.dispose();

    }
}

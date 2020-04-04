package local.ahri.resttest;

import android.bluetooth.BluetoothGatt;
import android.os.Bundle;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

import local.ahri.resttest.dto.MyTestDTO;
import local.ahri.resttest.dto.RawdataEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class MainActivity extends AppCompatActivity {
    final private String SYNC_SERVICE_UUID = "0000fffa-0000-1000-8000-00805f9b34fb";
    final private String SYNC_CONTROL_CHAR_UUID = "0000FFFA-0000-1000-8000-00805f9b34fb";
    final private String SYNC_DATA_CHAR_UUID = "0000FFFB-0000-1000-8000-00805f9b34fb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView textView = findViewById(R.id.mytext);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dakapo.wiki/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyTestService service = retrofit.create(MyTestService.class);

        service.get().enqueue(new Callback<MyTestDTO>() {
            @Override
            public void onResponse(Call<MyTestDTO> call, Response<MyTestDTO> response) {
                MyTestDTO myTestDTO = response.body();
                textView.setText(myTestDTO.getMessage());
            }
            @Override
            public void onFailure(Call<MyTestDTO> call, Throwable t) {
            }
        });

        BleManager instance = BleManager.getInstance();
        instance.init(getApplication());
        instance.enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(5000);

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setScanTimeOut(10000)
                .build();
        instance.initScanRule(scanRuleConfig);
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {

                // 기기 목록 초기화
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                // 기기 목록에 추가
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                // 스캔 종료 알림
                BleDevice bleDevice = scanResultList.get(0);
                MainActivity mainActivity = (MainActivity) getApplicationContext();
                mainActivity.connect(bleDevice);
            }
        });
    }

    // MainActivity에서 스캔 종료시 콜백으로 호출됨
    public void connect(BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {

            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {

            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                MainActivity mainActivity = (MainActivity) getApplicationContext();
                mainActivity.read(bleDevice);
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {

            }
        });
    }

    // connect함수가 connect 성공시 호출함
    public void read(BleDevice bleDevice) {
        BleManager.getInstance().read(
                bleDevice,
                SYNC_SERVICE_UUID,
                SYNC_CONTROL_CHAR_UUID,
                new BleReadCallback() {
                    @Override
                    public void onReadSuccess(byte[] data) {
                        RawdataEntity rawdataEntity = RawdataEntity.ParseBytearray(data);
                        Log.println(Log.DEBUG, "average temp", String.format("%d", rawdataEntity.getAvgTemp()));
                    }

                    @Override
                    public void onReadFailure(BleException exception) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

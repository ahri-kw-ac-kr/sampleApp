package local.ahri.resttest.ui;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.os.Bundle;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import local.ahri.resttest.R;
import local.ahri.resttest.databinding.ActivityMainBinding;
import local.ahri.resttest.model.RestfulAPI;
import local.ahri.resttest.model.dto.BleDeviceDTO;
import local.ahri.resttest.model.dto.PageDTO;
import local.ahri.resttest.sleep_doc.dto.RawdataDTO;
import local.ahri.resttest.model.dto.UserDTO;
import local.ahri.resttest.viewmodel.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel viewModel = new MainActivityViewModel();

    public void onFabClick(View view) {
        MainActivityViewModel viewModel = new MainActivityViewModel();
        Log.d("뷰모델", viewModel.toString() + " 토큰은?? " + RestfulAPI.token);

        /*viewModel.getAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::printUsers, Throwable::printStackTrace);*/

        viewModel.getRawdataFromSleepDoc()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(rawdataDTO -> {
                Log.i("MainActivity", "onSubscribe");
                TextView t = findViewById(R.id.mytext);
                t.setText(String.format("  데이터는 \t%d\t\t%d\t\t%d\t%d\t%d\t%d\t%d\t%d\t%d",
                //Log.i("메인으로 전달된",String.format("  데이터는 \t%d\t\t%d\t\t%d\t%d\t%d\t%d\t%d\t%d\t%d",
                        rawdataDTO.getStartTick(),
                        rawdataDTO.getEndTick(),
                        rawdataDTO.getSteps(),
                        rawdataDTO.getTotalLux(),
                        rawdataDTO.getAvgLux(),
                        rawdataDTO.getAvgTemp(),
                        rawdataDTO.getVectorX(),
                        rawdataDTO.getVectorY(),
                        rawdataDTO.getVectorZ()));
                //Log.i("메인스텝",Integer.toString(rawdataDTO.getSteps()));
            }, Throwable::printStackTrace);
    }

    private void printUsers(PageDTO<UserDTO> body) {
        TextView textView = findViewById(R.id.mytext);
        List<UserDTO> result = body.getContent();
        Log.d("과연과연", " " + body.toString());
        textView.setText("Username: "+result.get(0).getUsername()+", Fullname: "+result.get(0).getFullname());
    }

    private void setText(byte[] data) {
        TextView textView = findViewById(R.id.mytext);
        RawdataDTO rawdataDTO = RawdataDTO.ParseBytearray(data);
        Log.d("과연과연", " " + rawdataDTO.getAvgLux());
        textView.setText(rawdataDTO.getAvgLux());
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setActivity(this);

        UserDTO user = new UserDTO();
        user.setUsername("wawa");
        //user.setFullname("wiwi");
        user.setPassword("wowo");

        System.out.println(user);
        viewModel.postAuth(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RestfulAPI::setToken, Throwable::printStackTrace);


        BleManager.getInstance().init(getApplication());

        //BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder().setDeviceName(true,"SleepDoc").build();
        //BleManager.getInstance().initScanRule(scanRuleConfig);

        //String macAddress;
        viewModel.scanBle()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> Log.i("MainActivity", "블루투스 기기 스캔"))
                .subscribe(BleDeviceDTO -> {if(BleDeviceDTO.getName().equals("SleepDoc")) {
                    viewModel.connectSleepDoc(BleDeviceDTO.getMacAddress())
                            .observeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .doOnComplete(() -> Log.i("MainActivity", "on Complete"))
                            .subscribe(() -> {viewModel.battery()
                                    .observeOn(Schedulers.io())
                                    .subscribeOn(AndroidSchedulers.mainThread())
                                    .subscribe(data -> {Log.i("MainActivity", "배터리 "+ data);});},Throwable::printStackTrace);
                }});

        /*viewModel.battery()
                 .observeOn(Schedulers.io())
                 .subscribeOn(AndroidSchedulers.mainThread())
                 .subscribe(data -> {Log.i("MainActivity", "배터리 "+ data);}), Throwable::printStackTrace);*/

        /*String macAddress = "D0:31:A1:4B:DC:34";


        viewModel.connectSleepDoc(macAddress)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> Log.i("MainActivity", "on Complete"))
                .subscribe(() -> {}, Throwable::printStackTrace);*/

    }

}


package local.ahri.resttest.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;

import com.clj.fastble.BleManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import local.ahri.resttest.R;
import local.ahri.resttest.databinding.ActivityMainBinding;
import local.ahri.resttest.model.RestfulAPI;
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

        viewModel.getAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::printUsers, Throwable::printStackTrace);
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
        String macAddress = "D0:31:A1:4B:DC:34";

        viewModel.scanBle()
            .subscribe(bleDeviceDTO -> {
                Log.i("MainActivity", String.format("find: %s", bleDeviceDTO.getMacAddress()));
            });

        viewModel.connectSleepDoc(macAddress)
            .doOnComplete(() ->
                viewModel.getRawdata()
                    .subscribe(rawdataDTO -> Log.i("MainActivity", String.format("%d", rawdataDTO.getAvgLux())), Throwable::printStackTrace)
        );
    }
}


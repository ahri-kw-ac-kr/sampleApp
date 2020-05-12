package local.ahri.resttest.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import local.ahri.resttest.model.BleService;
import local.ahri.resttest.model.RestfulAPI;
import local.ahri.resttest.model.RestfulAPIService;
import local.ahri.resttest.model.SleepDocService;
import local.ahri.resttest.model.dto.AuthDTO;
import local.ahri.resttest.model.dto.BleDeviceDTO;
import local.ahri.resttest.model.dto.GPSDTO;
import local.ahri.resttest.model.dto.PageDTO;
import local.ahri.resttest.sleep_doc.dto.RawdataDTO;
import local.ahri.resttest.model.dto.UserDTO;

public class MainActivityViewModel extends ViewModel {

    private RestfulAPIService restfulAPIService;
    private BleService bleService;
    private SleepDocService sleepDocService;

    public MainActivityViewModel() {
        this.restfulAPIService = RestfulAPI.getInstance();
        this.bleService = BleService.getInstance();
        this.sleepDocService = SleepDocService.getInstance();
    }

    public Completable connectSleepDoc(String mac) {
        Log.i("Viewmodel", "connect start");
        return sleepDocService.connect(mac);
    }

    public Observable<RawdataDTO> getRawdataFromSleepDoc() {
        return sleepDocService.getRawdata();
    }

    public Observable<BleDeviceDTO> scanBle() {
        return bleService.scanBle();
    }

    public Observable battery(){
        return sleepDocService.battery();
    }

    public Single<UserDTO> postRegister(UserDTO user){
        return restfulAPIService.postRegister(user);
    }

    public Single<AuthDTO> postAuth(UserDTO user){
        return restfulAPIService.postAuth(user);
    }

    /************************ User ***********************/
    public Single<PageDTO<UserDTO>> getAllUser() {
        return restfulAPIService.getAllUser();
    }

    public Single<UserDTO> getUser(Long id){
        return restfulAPIService.getUser(id);
    }

    public Single<PageDTO<RawdataDTO>> getRawdataById(Long id, String page, String created_at_lt, String created_at_gt){
        return restfulAPIService.getRawdataById(id, page, created_at_lt, created_at_gt);
    }

    public Single<PageDTO<GPSDTO>> getGPSById(Long id, String page){
        return restfulAPIService.getGPSById(id, page);
    }

    public Single<UserDTO> postUser(UserDTO user){
        return restfulAPIService.postUser(user);
    }

    public Single<UserDTO> patchUser(Long id, UserDTO user){
        return restfulAPIService.patchUser(id, user);
    }

    public Single<List<UserDTO>> patchAllUser(List<UserDTO> user){
        return restfulAPIService.patchAllUser(user);
    }

    public Single<UserDTO> deleteUser(Long id){
        return restfulAPIService.deleteUser(id);
    }

    public String forget(String username){
        return restfulAPIService.forget(username);
    }

    public  Single<UserDTO> initPassword(String username, String number, String password){
        return restfulAPIService.initPassword(username, number, password);
    }

    public Single<UserDTO> plusFriend(Long id, String frname) {
        return restfulAPIService.plusFriend(id, frname);
    }

    public Single<PageDTO<UserDTO>> myDoctor(Long id, String page){
        return restfulAPIService.myDoctor(id, page);
    }

    public String delFriend(Long proId, Long patId){
        return restfulAPIService.delFriend(proId, patId);
    }

    /************************ GPS ***********************/
    public Single<PageDTO<GPSDTO>> getAllGPS() {
        return restfulAPIService.getAllGPS();
    }

    public Single<GPSDTO> getGPS(Long id){
        return restfulAPIService.getGPS(id);
    }

    public Single<GPSDTO> postGPS(GPSDTO gps){
        return restfulAPIService.postGPS(gps);
    }

    public Single<GPSDTO> patchGPS(Long id, GPSDTO gps){
        return restfulAPIService.patchGPS(id, gps);
    }

    public Single<List<GPSDTO>> patchAllGPS(List<GPSDTO> gps){
        return restfulAPIService.patchAllGPS(gps);
    }

    public Single<GPSDTO> deleteGPS(Long id){
        return restfulAPIService.deleteGPS(id);
    }

    /************************ GPS ***********************/
    public Single<PageDTO<RawdataDTO>> getAllRawdata() {
        return restfulAPIService.getAllRawdata();
    }

    public Single<RawdataDTO> getRawdata(Long id){
        return restfulAPIService.getRawdata(id);
    }

    public Single<RawdataDTO> postRawdata(RawdataDTO rawdata){
        return restfulAPIService.postRawdata(rawdata);
    }

    public Single<RawdataDTO> patchRawdata(Long id, RawdataDTO rawdata){
        return restfulAPIService.patchRawdata(id, rawdata);
    }

    public Single<List<RawdataDTO>> patchAllRawdata(List<RawdataDTO> gps){
        return restfulAPIService.patchAllRawdata(gps);
    }

    public Single<RawdataDTO> deleteRawdata(Long id){
        return restfulAPIService.deleteRawdata(id);
    }
}

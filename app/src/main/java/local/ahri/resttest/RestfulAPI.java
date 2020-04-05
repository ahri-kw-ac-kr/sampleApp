package local.ahri.resttest;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import local.ahri.resttest.dto.AuthDTO;
import local.ahri.resttest.dto.GPSDTO;
import local.ahri.resttest.dto.PageDTO;
import local.ahri.resttest.dto.RawdataDTO;
import local.ahri.resttest.dto.UserDTO;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leegi on 2020-03-31.
 */

public class RestfulAPI {

    private static RestfulAPI restfulAPI;
    //private static RestfulAPI restfulAPI;
    //private String url = "http://localhost:8080/";
    //private String url = "ec2-13-209-48-203.ap-northeast-2.compute.amazonaws.com/api/v1";
    private String url = "http://13.209.225.252/api/v1/";
    public String token;
    public Service service;

    private RestfulAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
    }
    public static RestfulAPI getInstance(){
        if(restfulAPI == null){ restfulAPI = new RestfulAPI(); }
        return restfulAPI;
    }

    ///회원가입 : /register
    public void PostRegister(HashMap user, Callback<UserDTO> callback){
        service.postRegister(user).enqueue(callback);
    }

    ///권한인증 : /authenticate
    public void PostAuth(UserDTO user){
        //Log.d("user",user.getFullname()+user.getUsername()+user.getPassword()+user.getBirth());
        Log.d("service",service.toString());
        service.postAuth(user).enqueue(new Callback<AuthDTO>(){
            @Override
            public void onResponse(Call<AuthDTO> call, Response<AuthDTO> response) {
                AuthDTO authDTO = response.body();
                Log.d("왓왓왓",response.toString());
                //Log.d("ttttooken",""+authDTO.toString()+",,,,"+token);
                token = authDTO.getToken();
                Log.d("token", authDTO.getToken().toString()+"--->"+token);

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Authorization", "Bearer "+token)
                                .build();
                        return chain.proceed(request);
                    }
                });
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(url).client(httpClient.build())
                        .build();
                service = retrofit.create(Service.class);
                Log.d("새로운 서비스이어야 한다.",service.toString());
            }
            @Override
            public void onFailure(Call<AuthDTO> call, Throwable t) {
                Log.d("NoNoNo","실행 안됨"+token+"/////"+service.toString());
                Log.d("NoNoNo","왜"+t.getMessage());
            }
        });
    }

/********************************************* User ************************************************/
    ///모든 유저 정보 조회 : /user
    public void GetUsers(Callback<PageDTO<UserDTO>> callback){
        service.getUsers().enqueue(callback);
    }

    ///유저 한 명 정보 조회 : /user/{id}
    public void GetUser(Long id, Callback<UserDTO> callback){
        service.getUser(id).enqueue(callback);
    }

    ///한 유저의 날짜,시간별 팔찌데이터 조회 : /user/{id}/rawdata/
    public void GetRawdataById(Long id, String created_at_lt, String created_at_gt, Callback<PageDTO<RawdataDTO>> callback){
        service.getRawdataById(id, created_at_lt, created_at_gt).enqueue(callback);
    }

    ///한 유저의 GPS 데이터 조회 : /user/{id}/gps/
    public void GetGPSById(Long id, Callback<PageDTO<GPSDTO>> callback){
        service.getGPSById(id).enqueue(callback);
    }

    ///Put User Entity : /user
    public void PutUser(UserDTO user, Callback<UserDTO> callback){
        service.putUser(user).enqueue(callback);
    }

    ///Patch User Entity : /user/{id}
    public void PatchUser(Long id, HashMap<String,Object> user, Callback<UserDTO> callback){
        service.patchUser(id, user).enqueue(callback);
    }

    ///Patch Users Entity : /user
    public void PatchUsers(List<HashMap<String,Object>> user, Callback<List<UserDTO>> callback){
        service.patchUsers(user).enqueue(callback);
    }

    ///Delete User Entity: /user
    public void DeleteUser(Long id, Callback<UserDTO> callback){
        service.deleteUser(id).enqueue(callback);
    }


/********************************************* GPS ************************************************/

    ///모든 gps 정보 조회 : /gps
    public void GetGPSs(Callback<PageDTO<GPSDTO>> callback){
        service.getGPSs().enqueue(callback);
    }

    ///GPS 한 게 정보 조회 : /gps/{id}
    public void GetGPS(Long id, Callback<GPSDTO> callback){
        service.getGPS(id).enqueue(callback);
    }

    ///Put GPS Entity : /gps
    public void PutGPS(GPSDTO gps, Callback<GPSDTO> callback){
        service.putGPS(gps).enqueue(callback);
    }

    ///Patch gps Entity : /gps/{id}
    public void PatchGPS(Long id, HashMap<String,Object> gps, Callback<GPSDTO> callback){
        service.patchGPS(id, gps).enqueue(callback);
    }

    ///Patch GPSs Entity : /gps
    public void PatchGPSs(List<HashMap<String,Object>> gps, Callback<List<GPSDTO>> callback){
        service.patchGPSs(gps).enqueue(callback);
    }

    ///Delete GPS Entity: /gps
    public void DeleteGPS(Long id, Callback<GPSDTO> callback){
        service.deleteGPS(id).enqueue(callback);
    }

/********************************************* Rawdata ************************************************/

    ///모든 Rawdata 정보 조회 : /rawdata
    public void GetRawdatas(Callback<PageDTO<RawdataDTO>> callback){
        service.getRawdatas().enqueue(callback);
    }

    ///rawdata 한 게 정보 조회 : /rawdata/{id}
    public void GetRawdata(Long id, Callback<RawdataDTO> callback){
        service.getRawdata(id).enqueue(callback);
    }

    ///Put rawdata Entity : /rawdata
    public void PutRawdata(RawdataDTO rawdata, Callback<RawdataDTO> callback){
        service.putRawdata(rawdata).enqueue(callback);
    }

    ///Patch rawdata Entity : /rawdata/{id}
    public void PatchRawdata(Long id, HashMap<String,Object> rawdata, Callback<RawdataDTO> callback){
        service.patchRawdata(id, rawdata).enqueue(callback);
    }

    ///Patch rawdatas Entity : /rawdata
    public void PatchRawdatas(List<HashMap<String,Object>> rawdata, Callback<List<RawdataDTO>> callback){
        service.patchRawdatas(rawdata).enqueue(callback);
    }

    ///Delete rawdata Entity: /rawdata
    public void DeleteRawdata(Long id, Callback<RawdataDTO> callback){
        service.deleteRawdata(id).enqueue(callback);
    }






}

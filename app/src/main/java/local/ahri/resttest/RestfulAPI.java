package local.ahri.resttest;

import java.util.HashMap;
import java.util.List;

import local.ahri.resttest.dto.GPSEntity;
import local.ahri.resttest.dto.RawdataEntity;
import local.ahri.resttest.dto.UserEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leegi on 2020-03-31.
 */

public class RestfulAPI {

    public Service BaseURL(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        return service;
    }

    ///회원가입 : /register
    public void PostRegister(Service service, HashMap user){
        service.postRegister(user).enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                UserEntity result = response.body();
            }
            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
            }
        });
    }

    ///권한인증 : /authenticate
    public void GetAuth(Service service, UserEntity user){
        service.getAuth(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

/********************************************* User ************************************************/
    ///모든 유저 정보 조회 : /user
    public void GetUsers(Service service){
        service.getUsers().enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                List<UserEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
            }
            });
    }

    ///유저 한 명 정보 조회 : /user/{id}
    public void GetUser(Service service, Long id){
        service.getUser(id).enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                UserEntity result = response.body();
            }
            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
            }
        });
    }

    ///한 유저의 날짜,시간별 팔찌데이터 조회 : /user/{id}/rawdata/
    public void GetRawdataById(Service service, Long id, String created_at_lt, String created_at_gt){
        service.getRawdataById(id, created_at_lt, created_at_gt).enqueue(new Callback<List<RawdataEntity>>() {
            @Override
            public void onResponse(Call<List<RawdataEntity>> call, Response<List<RawdataEntity>> response) {
                List<RawdataEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<RawdataEntity>> call, Throwable t) {
            }
        });
    }

    ///한 유저의 GPS 데이터 조회 : /user/{id}/gps/
    public void GetGPSById(Service service, Long id){
        service.getGPSById(id).enqueue(new Callback<List<GPSEntity>>() {
            @Override
            public void onResponse(Call<List<GPSEntity>> call, Response<List<GPSEntity>> response) {
                List<GPSEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<GPSEntity>> call, Throwable t) {
            }
        });
    }

    ///Put User Entity : /user
    public void PutUser(Service service, UserEntity user){
        service.putUser(user).enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                UserEntity result = response.body();
            }
            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
            }
        });
    }

    ///Patch User Entity : /user/{id}
    public void PatchUser(Service service, Long id, HashMap<String,Object> user){
        service.patchUser(id, user).enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                UserEntity result = response.body();
            }
            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
            }
        });
    }

    ///Patch Users Entity : /user
    public void PatchUsers(Service service, List<HashMap<String,Object>> user){
        service.patchUsers(user).enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                List<UserEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
            }
        });
    }

    ///Delete User Entity: /user
    public void DeleteUser(Service service, Long id){
        service.deleteUser(id).enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                UserEntity result = response.body();
            }
            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
            }
        });
    }


/********************************************* GPS ************************************************/

    ///모든 gps 정보 조회 : /gps
    public void GetGPSs(Service service){
        service.getGPSs().enqueue(new Callback<List<GPSEntity>>() {
            @Override
            public void onResponse(Call<List<GPSEntity>> call, Response<List<GPSEntity>> response) {
                List<GPSEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<GPSEntity>> call, Throwable t) {
            }
        });
    }

    ///GPS 한 게 정보 조회 : /gps/{id}
    public void GetGPS(Service service, Long id){
        service.getGPS(id).enqueue(new Callback<GPSEntity>() {
            @Override
            public void onResponse(Call<GPSEntity> call, Response<GPSEntity> response) {
                GPSEntity result = response.body();
            }
            @Override
            public void onFailure(Call<GPSEntity> call, Throwable t) {
            }
        });
    }

    ///Put GPS Entity : /gps
    public void PutGPS(Service service, GPSEntity gps){
        service.putGPS(gps).enqueue(new Callback<GPSEntity>() {
            @Override
            public void onResponse(Call<GPSEntity> call, Response<GPSEntity> response) {
                GPSEntity result = response.body();
            }
            @Override
            public void onFailure(Call<GPSEntity> call, Throwable t) {
            }
        });
    }

    ///Patch gps Entity : /gps/{id}
    public void PatchGPS(Service service, Long id, HashMap<String,Object> gps){
        service.patchGPS(id, gps).enqueue(new Callback<GPSEntity>() {
            @Override
            public void onResponse(Call<GPSEntity> call, Response<GPSEntity> response) {
                GPSEntity result = response.body();
            }
            @Override
            public void onFailure(Call<GPSEntity> call, Throwable t) {
            }
        });
    }

    ///Patch GPSs Entity : /gps
    public void PatchGPSs(Service service, List<HashMap<String,Object>> gps){
        service.patchGPSs(gps).enqueue(new Callback<List<GPSEntity>>() {
            @Override
            public void onResponse(Call<List<GPSEntity>> call, Response<List<GPSEntity>> response) {
                List<GPSEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<GPSEntity>> call, Throwable t) {
            }
        });
    }

    ///Delete GPS Entity: /gps
    public void DeleteGPS(Service service, Long id){
        service.deleteGPS(id).enqueue(new Callback<GPSEntity>() {
            @Override
            public void onResponse(Call<GPSEntity> call, Response<GPSEntity> response) {
                GPSEntity result = response.body();
            }
            @Override
            public void onFailure(Call<GPSEntity> call, Throwable t) {
            }
        });
    }

/********************************************* Rawdata ************************************************/

    ///모든 Rawdata 정보 조회 : /rawdata
    public void GetRawdatas(Service service){
        service.getRawdatas().enqueue(new Callback<List<RawdataEntity>>() {
            @Override
            public void onResponse(Call<List<RawdataEntity>> call, Response<List<RawdataEntity>> response) {
                List<RawdataEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<RawdataEntity>> call, Throwable t) {
            }
        });
    }

    ///rawdata 한 게 정보 조회 : /rawdata/{id}
    public void GetRawdata(Service service, Long id){
        service.getRawdata(id).enqueue(new Callback<RawdataEntity>() {
            @Override
            public void onResponse(Call<RawdataEntity> call, Response<RawdataEntity> response) {
                RawdataEntity result = response.body();
            }
            @Override
            public void onFailure(Call<RawdataEntity> call, Throwable t) {
            }
        });
    }

    ///Put rawdata Entity : /rawdata
    public void PutRawdata(Service service, RawdataEntity rawdata){
        service.putRawdata(rawdata).enqueue(new Callback<RawdataEntity>() {
            @Override
            public void onResponse(Call<RawdataEntity> call, Response<RawdataEntity> response) {
                RawdataEntity result = response.body();
            }
            @Override
            public void onFailure(Call<RawdataEntity> call, Throwable t) {
            }
        });
    }

    ///Patch rawdata Entity : /rawdata/{id}
    public void PatchRawdata(Service service, Long id, HashMap<String,Object> rawdata){
        service.patchRawdata(id, rawdata).enqueue(new Callback<RawdataEntity>() {
            @Override
            public void onResponse(Call<RawdataEntity> call, Response<RawdataEntity> response) {
                RawdataEntity result = response.body();
            }
            @Override
            public void onFailure(Call<RawdataEntity> call, Throwable t) {
            }
        });
    }

    ///Patch rawdatas Entity : /rawdata
    public void PatchRawdatas(Service service, List<HashMap<String,Object>> rawdata){
        service.patchRawdatas(rawdata).enqueue(new Callback<List<RawdataEntity>>() {
            @Override
            public void onResponse(Call<List<RawdataEntity>> call, Response<List<RawdataEntity>> response) {
                List<RawdataEntity> result = response.body();
            }
            @Override
            public void onFailure(Call<List<RawdataEntity>> call, Throwable t) {
            }
        });
    }

    ///Delete rawdata Entity: /rawdata
    public void DeleteRawdata(Service service, Long id){
        service.deleteRawdata(id).enqueue(new Callback<RawdataEntity>() {
            @Override
            public void onResponse(Call<RawdataEntity> call, Response<RawdataEntity> response) {
                RawdataEntity result = response.body();
            }
            @Override
            public void onFailure(Call<RawdataEntity> call, Throwable t) {
            }
        });
    }






}

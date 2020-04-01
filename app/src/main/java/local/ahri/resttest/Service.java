package local.ahri.resttest;

import java.util.HashMap;
import java.util.List;

import local.ahri.resttest.dto.UserEntity;
import local.ahri.resttest.dto.RawdataEntity;
import local.ahri.resttest.dto.GPSEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    /** POST
     *  BaseURL/register
     *  회원가입 **/
    @FormUrlEncoded
    @POST("/register")
    Call<UserEntity> postRegister(@FieldMap HashMap<String, Object> user);

    /** GET
     *  BaseURL/authenticate
     *  권한인증 **/
    @GET("/authenticate")
    Call<String> getAuth(@Body UserEntity user);

    /******************************  User  ************************************/
    /** GET
     *  BaseURL/user
     *  모든 유저 정보 조회 **/
    @GET("/user")
    Call<List<UserEntity>> getUsers();

	/** GET
     *  BaseURL/user/1
	 *  1번 유저 정보 조회 **/
    @GET("/user/{id}")
    Call<UserEntity> getUser(@Path("id") Long id);
    
    /** GET
     *  BaseURL/user/1/rawdata/?page=0&created_at_lt=00&created_at_gt=00
	 *  1번 유저의 어느 시간대 rawdata 조회 **/
    @GET("/user/{id}/rawdata")
    Call<List<RawdataEntity>> getRawdataById(@Path("id")Long id, @Query("created_at_lt")String created_at_lt, @Query("created_at_gt")String created_at_gt);

    /** GET
     *  BaseURL/user/1/gps
     *  1번 유저의 GPS data 조회 **/
    @GET("/user/{id}/gps")
    Call<List<GPSEntity>> getGPSById(@Path("id")Long id);

    /** PUT
     *  BaseURL/user
     *  user put **/
    @PUT("/user")
    Call<UserEntity> putUser(@Body UserEntity user);

    /** Patch
     *  BaseURL/user/{id}
     *  user patch **/
    @FormUrlEncoded
    @PATCH("/user/{id}")
    Call<UserEntity> patchUser(@Path("id")Long id, @FieldMap HashMap<String, Object> user);

    /** Patch
     *  BaseURL/user
     *  users patch **/
    @FormUrlEncoded
    @PATCH("/user")
    Call<List<UserEntity>> patchUsers(@FieldMap List<HashMap<String, Object>> user);

    /** Delete
     *  BaseURL/user/{id}
     *  user delete **/
    @DELETE("/user/{id}")
    Call<UserEntity> deleteUser(@Path("id")Long id);


    /******************************  GPS  ************************************/
    /** GET
     *  BaseURL/gps
     *  모든 gps 정보 조회 **/
    @GET("/gps")
    Call<List<GPSEntity>> getGPSs();

    /** GET
     *  BaseURL/gps/1
     *  1번 GPS 정보 조회 **/
    @GET("/user/{id}")
    Call<GPSEntity> getGPS(@Path("id") Long id);

    /** PUT
     *  BaseURL/gps
     *  gps put **/
    @PUT("/gps")
    Call<GPSEntity> putGPS(@Body GPSEntity gps);

    /** Patch
     *  BaseURL/gps/{id}
     *  gps patch **/
    @FormUrlEncoded
    @PATCH("/gps/{id}")
    Call<GPSEntity> patchGPS(@Path("id")Long id, @FieldMap HashMap<String, Object> gps);

    /** Patch
     *  BaseURL/gps
     *  GPSs patch **/
    @FormUrlEncoded
    @PATCH("/gps")
    Call<List<GPSEntity>> patchGPSs(@FieldMap List<HashMap<String, Object>> gps);

    /** Delete
     *  BaseURL/gps/{id}
     *  gps delete **/
    @DELETE("/gps/{id}")
    Call<GPSEntity> deleteGPS(@Path("id")Long id);


/******************************  Rawdata  ************************************/
    /** GET
     *  BaseURL/rawdata
     *  모든 rawdata 정보 조회 **/
    @GET("/rawdata")
    Call<List<RawdataEntity>> getRawdatas();

    /** GET
     *  BaseURL/rawdata/1
     *  1번 GPS 정보 조회 **/
    @GET("/rawdata/{id}")
    Call<RawdataEntity> getRawdata(@Path("id") Long id);

    /** PUT
     *  BaseURL/rawdata
     *  rawdata put **/
    @PUT("/gps")
    Call<RawdataEntity> putRawdata(@Body RawdataEntity rawdata);

    /** Patch
     *  BaseURL/rawdata/{id}
     *  gps patch **/
    @FormUrlEncoded
    @PATCH("/gps/{id}")
    Call<RawdataEntity> patchRawdata(@Path("id")Long id, @FieldMap HashMap<String, Object> rawdata);

    /** Patch
     *  BaseURL/rawdata
     *  Rawdatas patch **/
    @FormUrlEncoded
    @PATCH("/gps")
    Call<List<RawdataEntity>> patchRawdatas(@FieldMap List<HashMap<String, Object>> rawdata);

    /** Delete
     *  BaseURL/rawdata/{id}
     *  rawdata delete **/
    @DELETE("/gps/{id}")
    Call<RawdataEntity> deleteRawdata(@Path("id")Long id);

}

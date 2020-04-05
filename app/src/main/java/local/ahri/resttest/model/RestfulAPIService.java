package local.ahri.resttest.model;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import local.ahri.resttest.model.dto.AuthDTO;
import local.ahri.resttest.model.dto.GPSDTO;
import local.ahri.resttest.model.dto.PageDTO;
import local.ahri.resttest.model.dto.RawdataDTO;
import local.ahri.resttest.model.dto.UserDTO;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestfulAPIService {
    /** POST
     *  BaseURL/register
     *  회원가입 **/
    @FormUrlEncoded
    @POST("register")
    Single<UserDTO> postRegister(@FieldMap HashMap<String, Object> user);

    /** GET
     *  BaseURL/authenticate
     *  권한인증 **/
    //@FormUrlEncoded
    @POST("authenticate")
    //Single<AuthDTO> getAuth(@Body UserDTO user);
    //Single<AuthDTO> postAuth(@FieldMap HashMap<String,Object> user);
    Single<AuthDTO> postAuth(@Body UserDTO user);

    /******************************  User  ************************************/
    /** GET
     *  BaseURL/user
     *  모든 유저 정보 조회 **/
    @GET("user")
    Single<PageDTO<UserDTO>> getUsers();

    /** GET
     *  BaseURL/user/1
     *  1번 유저 정보 조회 **/
    @GET("user/{id}")
    Single<UserDTO> getUser(@Path("id") Long id);

    /** GET
     *  BaseURL/user/1/rawdata/?page=0&created_at_lt=00&created_at_gt=00
     *  1번 유저의 어느 시간대 rawdata 조회 **/
    @GET("user/{id}/rawdata")
    Single<PageDTO<RawdataDTO>> getRawdataById(@Path("id")Long id, @Query("created_at_lt")String created_at_lt, @Query("created_at_gt")String created_at_gt);

    /** GET
     *  BaseURL/user/1/gps
     *  1번 유저의 GPS data 조회 **/
    @GET("user/{id}/gps")
    Single<PageDTO<GPSDTO>> getGPSById(@Path("id")Long id);

    /** POST
     *  BaseURL/user
     *  user post **/
    @POST("user")
    Single<UserDTO> putUser(@Body UserDTO user);

    /** Patch
     *  BaseURL/user/{id}
     *  user patch **/
    @FormUrlEncoded
    @PATCH("user/{id}")
    Single<UserDTO> patchUser(@Path("id")Long id, @FieldMap HashMap<String, Object> user);

    /** Patch
     *  BaseURL/user
     *  users patch **/
    @FormUrlEncoded
    @PATCH("user")
    Single<List<UserDTO>> patchUsers(@FieldMap List<HashMap<String, Object>> user);

    /** Delete
     *  BaseURL/user/{id}
     *  user delete **/
    @DELETE("user/{id}")
    Single<UserDTO> deleteUser(@Path("id")Long id);


    /******************************  GPS  ************************************/
    /** GET
     *  BaseURL/gps
     *  모든 gps 정보 조회 **/
    @GET("gps")
    Single<PageDTO<GPSDTO>> getGPSs();

    /** GET
     *  BaseURL/gps/1
     *  1번 GPS 정보 조회 **/
    @GET("gps/{id}")
    Single<GPSDTO> getGPS(@Path("id") Long id);

    /** POST
     *  BaseURL/gps
     *  gps post **/
    @POST("gps")
    Single<GPSDTO> putGPS(@Body GPSDTO gps);

    /** Patch
     *  BaseURL/gps/{id}
     *  gps patch **/
    @FormUrlEncoded
    @PATCH("gps/{id}")
    Single<GPSDTO> patchGPS(@Path("id")Long id, @FieldMap HashMap<String, Object> gps);

    /** Patch
     *  BaseURL/gps
     *  GPSs patch **/
    @FormUrlEncoded
    @PATCH("gps")
    Single<List<GPSDTO>> patchGPSs(@FieldMap List<HashMap<String, Object>> gps);

    /** Delete
     *  BaseURL/gps/{id}
     *  gps delete **/
    @DELETE("gps/{id}")
    Single<GPSDTO> deleteGPS(@Path("id")Long id);


/******************************  Rawdata  ************************************/
    /** GET
     *  BaseURL/rawdata
     *  모든 rawdata 정보 조회 **/
    @GET("rawdata")
    Single<PageDTO<RawdataDTO>> getRawdatas();

    /** GET
     *  BaseURL/rawdata/1
     *  1번 GPS 정보 조회 **/
    @GET("rawdata/{id}")
    Single<RawdataDTO> getRawdata(@Path("id") Long id);

    /** POST
     *  BaseURL/rawdata
     *  rawdata post **/
    @POST("rawdata")
    Single<RawdataDTO> putRawdata(@Body RawdataDTO rawdata);

    /** Patch
     *  BaseURL/rawdata/{id}
     *  gps patch **/
    @FormUrlEncoded
    @PATCH("rawdata/{id}")
    Single<RawdataDTO> patchRawdata(@Path("id")Long id, @FieldMap HashMap<String, Object> rawdata);

    /** Patch
     *  BaseURL/rawdata
     *  Rawdatas patch **/
    @FormUrlEncoded
    @PATCH("rawdata")
    Single<List<RawdataDTO>> patchRawdatas(@FieldMap List<HashMap<String, Object>> rawdata);

    /** Delete
     *  BaseURL/rawdata/{id}
     *  rawdata delete **/
    @DELETE("rawdata/{id}")
    Single<RawdataDTO> deleteRawdata(@Path("id")Long id);
}
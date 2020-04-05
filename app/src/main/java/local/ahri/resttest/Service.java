package local.ahri.resttest;

import java.util.HashMap;
import java.util.List;

import local.ahri.resttest.dto.AuthDTO;
import local.ahri.resttest.dto.PageDTO;
import local.ahri.resttest.dto.UserDTO;
import local.ahri.resttest.dto.RawdataDTO;
import local.ahri.resttest.dto.GPSDTO;

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
    @POST("register")
    Call<UserDTO> postRegister(@FieldMap HashMap<String, Object> user);

    /** GET
     *  BaseURL/authenticate
     *  권한인증 **/
    //@FormUrlEncoded
    @POST("authenticate")
    //Call<AuthDTO> getAuth(@Body UserDTO user);
    //Call<AuthDTO> postAuth(@FieldMap HashMap<String,Object> user);
    Call<AuthDTO> postAuth(@Body UserDTO user);

    /******************************  User  ************************************/
    /** GET
     *  BaseURL/user
     *  모든 유저 정보 조회 **/
    @GET("user")
    Call<PageDTO<UserDTO>> getUsers();

	/** GET
     *  BaseURL/user/1
	 *  1번 유저 정보 조회 **/
    @GET("user/{id}")
    Call<UserDTO> getUser(@Path("id") Long id);
    
    /** GET
     *  BaseURL/user/1/rawdata/?page=0&created_at_lt=00&created_at_gt=00
	 *  1번 유저의 어느 시간대 rawdata 조회 **/
    @GET("user/{id}/rawdata")
    Call<PageDTO<RawdataDTO>> getRawdataById(@Path("id")Long id, @Query("created_at_lt")String created_at_lt, @Query("created_at_gt")String created_at_gt);

    /** GET
     *  BaseURL/user/1/gps
     *  1번 유저의 GPS data 조회 **/
    @GET("user/{id}/gps")
    Call<PageDTO<GPSDTO>> getGPSById(@Path("id")Long id);

    /** POST
     *  BaseURL/user
     *  user post **/
    @POST("user")
    Call<UserDTO> putUser(@Body UserDTO user);

    /** Patch
     *  BaseURL/user/{id}
     *  user patch **/
    @FormUrlEncoded
    @PATCH("user/{id}")
    Call<UserDTO> patchUser(@Path("id")Long id, @FieldMap HashMap<String, Object> user);

    /** Patch
     *  BaseURL/user
     *  users patch **/
    @FormUrlEncoded
    @PATCH("user")
    Call<List<UserDTO>> patchUsers(@FieldMap List<HashMap<String, Object>> user);

    /** Delete
     *  BaseURL/user/{id}
     *  user delete **/
    @DELETE("user/{id}")
    Call<UserDTO> deleteUser(@Path("id")Long id);


    /******************************  GPS  ************************************/
    /** GET
     *  BaseURL/gps
     *  모든 gps 정보 조회 **/
    @GET("gps")
    Call<PageDTO<GPSDTO>> getGPSs();

    /** GET
     *  BaseURL/gps/1
     *  1번 GPS 정보 조회 **/
    @GET("gps/{id}")
    Call<GPSDTO> getGPS(@Path("id") Long id);

    /** POST
     *  BaseURL/gps
     *  gps post **/
    @POST("gps")
    Call<GPSDTO> putGPS(@Body GPSDTO gps);

    /** Patch
     *  BaseURL/gps/{id}
     *  gps patch **/
    @FormUrlEncoded
    @PATCH("gps/{id}")
    Call<GPSDTO> patchGPS(@Path("id")Long id, @FieldMap HashMap<String, Object> gps);

    /** Patch
     *  BaseURL/gps
     *  GPSs patch **/
    @FormUrlEncoded
    @PATCH("gps")
    Call<List<GPSDTO>> patchGPSs(@FieldMap List<HashMap<String, Object>> gps);

    /** Delete
     *  BaseURL/gps/{id}
     *  gps delete **/
    @DELETE("gps/{id}")
    Call<GPSDTO> deleteGPS(@Path("id")Long id);


/******************************  Rawdata  ************************************/
    /** GET
     *  BaseURL/rawdata
     *  모든 rawdata 정보 조회 **/
    @GET("rawdata")
    Call<PageDTO<RawdataDTO>> getRawdatas();

    /** GET
     *  BaseURL/rawdata/1
     *  1번 GPS 정보 조회 **/
    @GET("rawdata/{id}")
    Call<RawdataDTO> getRawdata(@Path("id") Long id);

    /** POST
     *  BaseURL/rawdata
     *  rawdata post **/
    @POST("rawdata")
    Call<RawdataDTO> putRawdata(@Body RawdataDTO rawdata);

    /** Patch
     *  BaseURL/rawdata/{id}
     *  gps patch **/
    @FormUrlEncoded
    @PATCH("rawdata/{id}")
    Call<RawdataDTO> patchRawdata(@Path("id")Long id, @FieldMap HashMap<String, Object> rawdata);

    /** Patch
     *  BaseURL/rawdata
     *  Rawdatas patch **/
    @FormUrlEncoded
    @PATCH("rawdata")
    Call<List<RawdataDTO>> patchRawdatas(@FieldMap List<HashMap<String, Object>> rawdata);

    /** Delete
     *  BaseURL/rawdata/{id}
     *  rawdata delete **/
    @DELETE("rawdata/{id}")
    Call<RawdataDTO> deleteRawdata(@Path("id")Long id);

}

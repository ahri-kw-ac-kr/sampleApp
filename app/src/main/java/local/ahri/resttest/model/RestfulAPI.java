package local.ahri.resttest.model;

import java.io.IOException;

import local.ahri.resttest.model.dto.AuthDTO;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestfulAPI {
//    private static String url = "ec2-13-209-48-203.ap-northeast-2.compute.amazonaws.com/api/v1";
    private static String url = "http://13.209.225.252/api/v1/";
    private static RestfulAPIService restfulAPIService;
    public static String token;

    public static synchronized RestfulAPIService getInstance() {
        if (restfulAPIService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            restfulAPIService = retrofit.create(RestfulAPIService.class);
        }
        return restfulAPIService;
    }

    public static synchronized void setToken(AuthDTO authDTO) {
        RestfulAPI.token = authDTO.getToken();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        restfulAPIService = retrofit.create(RestfulAPIService.class);
    }

    private RestfulAPI() {
    }
}

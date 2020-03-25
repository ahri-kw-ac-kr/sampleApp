package local.ahri.resttest;

import local.ahri.resttest.dto.MyTestDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MyTestService {
    @GET("test.json")
    Call<MyTestDTO> get();
}

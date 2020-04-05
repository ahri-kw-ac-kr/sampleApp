package local.ahri.resttest.dto;

import java.io.Serializable;

public class AuthDTO implements Serializable {
    private String token;

    public AuthDTO(){}
    public AuthDTO(String token){
        this.token = token;
    }

    public String getToken(){ return token; }
    public void setToken(String token) { this.token = token; }
}

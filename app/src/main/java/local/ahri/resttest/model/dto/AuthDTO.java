package local.ahri.resttest.model.dto;

import java.io.Serializable;

public class AuthDTO implements Serializable {
    private UserDTO user;
    private String token;

    public AuthDTO(){}
    public AuthDTO(UserDTO user, String token){
        this.user = user;
        this.token = token;
    }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public String getToken(){ return token; }
    public void setToken(String token) { this.token = token; }
}

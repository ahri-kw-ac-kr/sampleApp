package local.ahri.resttest.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leegi on 2020-03-31.
 */

public class UserDTO implements Serializable {

    /*private class SmallUserEntity{
        private Long id;
        private String username;
        private String fullname;
        private char sex;
        private String birth;
        private String phone;
        private String password;
        private String newpassword;
    }*/

    private Long id;
    private String username;
    private String fullname;
    private char sex;
    private String birth;
    private String phone;
    private String password;
    private String newpassword;
    private List<UserDTO> friend;

    public UserDTO(){    }

    public UserDTO(Long id, String username, String fullname, char sex, String birth, String phone, String password, String newpassword, List<UserDTO> freind){
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
        this.password = password;
        this.newpassword = newpassword;
        this.friend = freind;
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname){ this.fullname = fullname; }

    public char getSex() { return sex; }
    public void setSex(char sex){ this.sex = sex; }

    public String getBirth() { return birth; }
    public void setBirth(String birth){ this.birth = birth; }

    public String getPhone() { return phone; }
    public void setPhone(String phone){ this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password){ this.password = password; }

    public String getNewpassword() { return newpassword; }
    public void setNewpassword(String newpassword){ this.newpassword= newpassword; }

    public List<UserDTO> getFriend() { return friend; }
    public void setFriend(){ this.friend = friend;}
}

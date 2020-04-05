package local.ahri.resttest.model.dto;

import java.io.Serializable;


public class MyTestDTO implements Serializable {
    Long id;
    String message;

    public MyTestDTO() {

    }

    public MyTestDTO(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package local.ahri.resttest.dto;

import java.io.Serializable;

public class MyTest implements Serializable {
    Long id;
    String message;

    public MyTest() {

    }

    public MyTest(Long id, String message) {
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

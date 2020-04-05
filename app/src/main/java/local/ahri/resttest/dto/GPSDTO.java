package local.ahri.resttest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leegi on 2020-03-31.
 */

public class GPSDTO implements Serializable {

    private Long id;
    private Date createdAt;
    private String lat;
    private String lon;
    private UserDTO user;

    public GPSDTO(){    }

    public GPSDTO(Long id, Date createdAt, String lat, String lon, UserDTO user){
        this.id = id;
        this.createdAt = createdAt;
        this.lat = lat;
        this.lon = lon;
        this.user = user;
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public Date getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Date id){ this.createdAt = createdAt; }

    public String getLat(){ return lat;}
    public void setLat(String lat){ this.lat = lat; }

    public String getLon(){ return lon;}
    public void setLon(String lon){ this.lon = lon; }

    public UserDTO getUser(){ return user;}
    public void setUser(UserDTO user){ this.user = user; }
}

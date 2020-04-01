package local.ahri.resttest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import local.ahri.resttest.dto.UserEntity;
/**
 * Created by leegi on 2020-03-31.
 */

public class GPSEntity implements Serializable {

    private Long id;
    private Date createdAt;
    private String lat;
    private String lon;
    private UserEntity user;

    public GPSEntity(){    }

    public GPSEntity(Long id, Date createdAt, String lat, String lon, UserEntity user){
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

    public UserEntity getUser(){ return user;}
    public void setUser(UserEntity user){ this.user = user; }
}

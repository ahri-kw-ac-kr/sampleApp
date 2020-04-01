package local.ahri.resttest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import local.ahri.resttest.dto.UserEntity;

/**
 * Created by leegi on 2020-03-31.
 */

public class RawdataEntity implements Serializable {

    private Long id;
    private Date createdAt;
    private int startTick;
    private int endTick;
    private int totalLux;
    private short steps;
    private short avgLux;
    private short avgTemp;
    private short vectorX;
    private short vectorY;
    private short vectorZ;
    private UserEntity user;

    public RawdataEntity(){    }

    public RawdataEntity(Long id, Date createdAt, int startTick, int endTick, int totalLux, short steps, short avgLux, short avgTemp, short vectorX, short vectorY, short vectorZ, UserEntity user){
        this.id = id;
        this.createdAt = createdAt;
        this.startTick = startTick;
        this.endTick = endTick;
        this.totalLux = totalLux;
        this.steps= steps;
        this.avgLux= avgLux;
        this.avgTemp = avgTemp;
        this.vectorX= vectorX;
        this.vectorY= vectorY;
        this.vectorZ= vectorZ;
        this.user = user;
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public Date getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Date id){ this.createdAt = createdAt; }

    public int getStartTick(){ return startTick; }
    public void setStartTick(int startTick){ this.startTick = startTick; }

    public int getEndTick(){ return endTick; }
    public void setEndTick(int endTick){ this.endTick = endTick; }

    public int getTotalLux(){ return totalLux; }
    public void setTotalLux(int totalLux){ this.totalLux = totalLux; }

    public short getSteps() { return steps; }
    public void setSteps(short steps){ this.steps = steps; }

    public short getAvgLux() { return avgLux; }
    public void setAvgLux(short avgLux){ this.avgLux = avgLux; }

    public short getAvgTemp() { return avgTemp; }
    public void setAvgTemp(short avgTemp){ this.avgTemp = avgTemp; }

    public short getVectorX() { return vectorX; }
    public void setVectorX(short vectorX){ this.vectorX= vectorX; }

    public short getVectorY() { return vectorY; }
    public void setVectorY(short vectorY){ this.vectorY= vectorY; }

    public short getVectorZ() { return vectorZ; }
    public void setVectorZ(short vectorZ){ this.vectorZ= vectorZ; }

    public UserEntity getUser(){ return user;}
    public void setUser(UserEntity user){ this.user = user; }
}

package local.ahri.resttest.model.dto;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by leegi on 2020-03-31.
 */

public class RawdataDTO implements Serializable {

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
    private UserDTO user;

    /*
    24 byte length
    9 fields
    [][][][]  - StartTick
    [][][][]  - End Tick
    [][]      - Steps
    [][][][]  - totalLux
    [][]      - avgLux
    [][]      - avgTemp
    [][]      - vectorX
    [][]      - vectorY
    [][]      - vectorZ
     */
    public static RawdataDTO ParseBytearray(byte[] data) {
        RawdataDTO res = new RawdataDTO();
        int offset = 0;
        byte[] chunk4 = new byte[4];
        byte[] chunk2 = new byte[2];

        // StartTick
        System.arraycopy(data, offset, chunk4, 0, chunk4.length);
        res.setStartTick(ByteBuffer.wrap(chunk4).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt());
        offset += chunk4.length;

        // EndTick
        System.arraycopy(data, offset, chunk4, 0, chunk4.length);
        res.setEndTick(ByteBuffer.wrap(chunk4).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt());
        offset += chunk4.length;

        // Steps
        System.arraycopy(data, offset, chunk2, 0, chunk2.length);
        res.setSteps(ByteBuffer.wrap(chunk2).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort());
        offset += chunk2.length;

        // TotalLux
        System.arraycopy(data, offset, chunk4, 0, chunk4.length);
        res.setTotalLux(ByteBuffer.wrap(chunk4).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt());
        offset += chunk4.length;

        // Average Lux
        System.arraycopy(data, offset, chunk2, 0, chunk2.length);
        res.setAvgLux(ByteBuffer.wrap(chunk2).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort());
        offset += chunk2.length;

        // Average temp
        System.arraycopy(data, offset, chunk2, 0, chunk2.length);
        res.setAvgTemp(ByteBuffer.wrap(chunk2).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort());
        offset += chunk2.length;

        // VectorX
        System.arraycopy(data, offset, chunk2, 0, chunk2.length);
        res.setVectorX(ByteBuffer.wrap(chunk2).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort());
        offset += chunk2.length;

        // VectorY
        System.arraycopy(data, offset, chunk2, 0, chunk2.length);
        res.setVectorY(ByteBuffer.wrap(chunk2).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort());
        offset += chunk2.length;

        // VectorZ
        System.arraycopy(data, offset, chunk2, 0, chunk2.length);
        res.setVectorZ(ByteBuffer.wrap(chunk2).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort());

        return res;
    }

    public RawdataDTO(){    }


    public RawdataDTO(Long id, Date createdAt, int startTick, int endTick, int totalLux, short steps, short avgLux, short avgTemp, short vectorX, short vectorY, short vectorZ, UserDTO user){
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

    public UserDTO getUser(){ return user;}
    public void setUser(UserDTO user){ this.user = user; }
}

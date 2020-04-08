package local.ahri.resttest.model.dto;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class BleInfoDTO implements Serializable {
    public RawdataDTO[] rawdataDTOArray = new RawdataDTO[6];
    public int time_zone;
    public short reset_num;
    public int remainings;

    static public BleInfoDTO ParseByteArray(byte[] bytes) {
        int i = 0;
        byte[] time_zone_bytes = new byte[4];
        byte[] reset_num_bytes = new byte[2];
        byte[] remainings_bytes = new byte[4];
        BleInfoDTO bleInfoDTO = new BleInfoDTO();


        for(i=0; i<6; i++) {
            byte[] data = new byte[24];
            System.arraycopy(bytes, i*24, data, 0, 24 );
            bleInfoDTO.rawdataDTOArray[i] = RawdataDTO.ParseBytearray(data);
        }

        int srcPos = 24 * 6;

        System.arraycopy(bytes, srcPos, time_zone_bytes, 0, 4 );
        srcPos += 4;

        System.arraycopy(bytes, srcPos, reset_num_bytes, 0, 2 );
        srcPos += 2;

        System.arraycopy(bytes, srcPos, remainings_bytes, 0, 4 );

        ByteBuffer wrapped = ByteBuffer.wrap(time_zone_bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        bleInfoDTO.time_zone = wrapped.getInt();

        wrapped = ByteBuffer.wrap(reset_num_bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        bleInfoDTO.reset_num = wrapped.getShort();

        wrapped = ByteBuffer.wrap(remainings_bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        bleInfoDTO.remainings = wrapped.getInt();

        return bleInfoDTO;
    }
}

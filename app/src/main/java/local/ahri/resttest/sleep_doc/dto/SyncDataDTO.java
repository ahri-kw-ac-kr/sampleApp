package local.ahri.resttest.sleep_doc.dto;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

import local.ahri.resttest.exceptions.DataIsTooShortException;
import local.ahri.resttest.exceptions.ZeroLengthException;

import static local.ahri.resttest.sleep_doc.Spec.SYNC_DATA_BYTE_LENGTH;

public class SyncDataDTO implements Serializable {
    public RawdataDTO[] rawdataDTOArray = new RawdataDTO[6];
    public int time_zone;
    public short reset_num;
    public int remain;

    public static int RAWDATA_BYTE_LENGTH = 24;
    public static int SYNCDATA_BYTE_LENGTH = 24 * 6 + 10;

    private static ByteArrayOutputStream syncDataStream = new ByteArrayOutputStream();

    static public SyncDataDTO ParseByteArray(byte[] bytes) throws DataIsTooShortException, ZeroLengthException {
        if (bytes[0] == 0) {
            throw new ZeroLengthException("Sync Done");
        }
        syncDataStream.write(bytes, 1, bytes[0]);
        Log.i("SleepDocService", "readDataSize :"+syncDataStream.size() + "  SleepdocDataSize : "+ SYNC_DATA_BYTE_LENGTH);

        if( syncDataStream.size() < SYNC_DATA_BYTE_LENGTH ) {
            throw new DataIsTooShortException("Prepare Next");
        }

        byte[] stream = syncDataStream.toByteArray();
        byte[] data = new byte[SYNC_DATA_BYTE_LENGTH];
        byte[] timeZoneBytes = new byte[4];
        byte[] resetNumBytes = new byte[2];
        byte[] remainBytes = new byte[4];

        System.arraycopy(stream, stream.length-data.length, data, 0, data.length);

        SyncDataDTO syncDataDTO = new SyncDataDTO();

        for(int i=0; i<6; i++) {
            byte[] rawdataByteArray = new byte[RAWDATA_BYTE_LENGTH];
            System.arraycopy(data, i*RAWDATA_BYTE_LENGTH, rawdataByteArray, 0, RAWDATA_BYTE_LENGTH );
            syncDataDTO.rawdataDTOArray[i] = RawdataDTO.ParseBytearray(rawdataByteArray);
        }

        int srcPos = RAWDATA_BYTE_LENGTH * 6;

        System.arraycopy(data, srcPos, timeZoneBytes, 0, 4 );
        srcPos += 4;

        System.arraycopy(data, srcPos, resetNumBytes, 0, 2 );
        srcPos += 2;

        System.arraycopy(data, srcPos, remainBytes, 0, 4 );

        ByteBuffer wrapped = ByteBuffer.wrap(timeZoneBytes).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        syncDataDTO.time_zone = wrapped.getInt();

        wrapped = ByteBuffer.wrap(resetNumBytes).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        syncDataDTO.reset_num = wrapped.getShort();

        wrapped = ByteBuffer.wrap(remainBytes).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        syncDataDTO.remain = wrapped.getInt();

        return syncDataDTO;
    }
}
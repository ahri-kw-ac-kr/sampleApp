package local.ahri.resttest.sleep_doc.command;

public class Command {
    public static final byte SYNC_CONTROL_START = 0x01;
    public static final byte SYNC_CONTROL_PREPARE_NEXT = 0x02;
    public static final byte SYNC_CONTROL_DONE = 0x03;

    public static final byte SYNC_NOTI_READY = 0x11;
    public static final byte SYNC_NOTI_NEXT_READY = 0x12;
    public static final byte SYNC_NOTI_DONE = 0x13;
    public static final byte SYNC_NOTI_ERROR = (byte) 0xFF;

    public static final byte SYS_CMD_SET_RTC = 0x06;
    public static final byte SYS_CMD_GET_UUID = 0x0B;
}

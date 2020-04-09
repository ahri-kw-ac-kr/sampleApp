package local.ahri.resttest.model.dto;

import com.clj.fastble.data.BleDevice;

import java.io.Serializable;
import java.util.List;

public class BleDeviceDTO implements Serializable {
    private String macAddress;
    private String key;
    private String name;
    private int rssi;

    public BleDeviceDTO() {};

    public BleDeviceDTO(BleDevice bleDevice) {
        this.macAddress = bleDevice.getMac();
        this.key = bleDevice.getKey();
        this.name = bleDevice.getName();
        this.rssi = bleDevice.getRssi();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}

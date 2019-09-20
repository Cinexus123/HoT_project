package rakietrak.networkmock.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import rakietrak.networkmock.utils.MacUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "DEVICES_MOCK")
public class Device {

    @Id
    private String macAddress;
    private String networkId;
    private LocalDateTime updatedAt;

    public Device(String macAddress, String networkId, LocalDateTime updatedAt) {
        this.macAddress = MacUtils.prepareMacAddress(macAddress);
        this.networkId = networkId;
        this.updatedAt = updatedAt;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = MacUtils.prepareMacAddress(macAddress);
    }
}

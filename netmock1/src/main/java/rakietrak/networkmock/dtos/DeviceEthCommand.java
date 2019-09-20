package rakietrak.networkmock.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEthCommand {
    private String mac_address;
    private String port_id;
}

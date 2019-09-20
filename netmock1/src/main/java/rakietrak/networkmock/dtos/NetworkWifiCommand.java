package rakietrak.networkmock.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class NetworkWifiCommand {
    private String ap_mac;
}

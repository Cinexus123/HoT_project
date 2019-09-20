package rakietrak.networkmock.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceWifiCommand {

    private String mac;
    private String ap_mac;
    private Long _last_seen_by_uap;
}

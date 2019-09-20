package rakietrak.networkmock.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {

    private String apName;
    private String apType;
    private boolean isOnline;
    private Timestamp lastSeen;
}

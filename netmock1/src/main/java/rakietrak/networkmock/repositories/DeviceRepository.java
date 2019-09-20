package rakietrak.networkmock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import rakietrak.networkmock.entities.Device;

@RestController
public interface DeviceRepository extends JpaRepository<Device, String> {
}

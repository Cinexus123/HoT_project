package rakietrak.networkmock.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rakietrak.networkmock.entities.Device;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="USERS_MOCK")
public class User {

    @Id
    private String nickname;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Device> devices;
}

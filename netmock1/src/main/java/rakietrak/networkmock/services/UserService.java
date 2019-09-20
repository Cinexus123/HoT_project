package rakietrak.networkmock.services;

import rakietrak.networkmock.entities.User;

import java.util.List;

public interface UserService {
    User get(String nickName);

    List<User> getAll();
}

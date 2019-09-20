package rakietrak.networkmock.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rakietrak.networkmock.entities.User;
import rakietrak.networkmock.exceptions.NotFoundException;
import rakietrak.networkmock.repositories.UserRepository;
import rakietrak.networkmock.services.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User get(String nickName) {
        return repository.findById(nickName).orElseThrow(() -> new NotFoundException(nickName));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}

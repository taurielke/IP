package ru.ulstu.is.sbapp.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ulstu.is.sbapp.user.model.User;
import ru.ulstu.is.sbapp.user.model.UserRole;
import ru.ulstu.is.sbapp.user.repository.UserRepository;
import ru.ulstu.is.sbapp.util.validation.ValidationException;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidatorUtil validatorUtil;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validatorUtil = validatorUtil;
    }

    public Page<User> findAllPages(int page, int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").ascending()));
    }

    public User findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }

    public User createUser(String login, String password, String passwordConfirm) {
        return createUser(login, password, passwordConfirm, UserRole.TEACHER);
    }

    public User createUser(String login, String password, String passwordConfirm, UserRole role) {
        if (findByLogin(login) != null) {
            throw new ValidationException(String.format("User '%s' already exists", login));
        }
        final User user = new User(login, passwordEncoder.encode(password), role);
        validatorUtil.validate(user);
        if (!Objects.equals(password, passwordConfirm)) {
            throw new ValidationException("Passwords not equal");
        }
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userEntity = findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }
}

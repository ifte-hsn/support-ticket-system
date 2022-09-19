package com.helloshishir.support.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 1. get all users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 2. get user by id
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
    // 3. get user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 4. get user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 5. create/update user
    public User save(User user) {
        encodePassword(user);
        return userRepository.save(user);
    }

    // 6. delete user
    public void delete(User user) {
        userRepository.delete(user);
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}

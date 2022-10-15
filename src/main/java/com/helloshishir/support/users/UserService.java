package com.helloshishir.support.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
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
    public Page<User> findAll(int pageNumber, int perPage, String sortField, String direction) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortField);

        if(direction.equalsIgnoreCase("DESC")) {
            sort = Sort.by(Sort.Direction.DESC, "id");
        }
        Pageable page = PageRequest.of(pageNumber, perPage, sort);
        return userRepository.findAll(page);
    }

    // 2. get user by id
    public User findById(Integer id) {
        return userRepository.findById(id).get();
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

        Boolean isUpdatingUser = (user.getId() != null); // returns true if id is not null

        if(isUpdatingUser) {
            User userFromDb = findById(user.getId());
            if(user.getPassword().isEmpty()) {
                user.setPassword(userFromDb.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }

        return userRepository.save(user);
    }

    // 6. delete user
    public void delete(User user) {
        userRepository.delete(user);
    }

    // check is unique email
    public boolean isEmailUnique(Integer id, String email) {
        if(id != null) {
            // edit user
            // So we need to check if provided email is same as user email
            // if same then return true, so that user can save the form.
            // otherwise normal validation process
            User user = findById(id);
            if(user.getEmail().equals(email)) {
                return true;
            }
        }
        return findByEmail(email) == null;
    }

    public boolean isUsernameUnique(Integer id, String username) {
        if(id != null) {
            User user = findById(id);
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return findByUsername(username) == null;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}

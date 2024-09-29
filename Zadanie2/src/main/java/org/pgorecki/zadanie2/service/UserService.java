package org.pgorecki.zadanie2.service;

import lombok.RequiredArgsConstructor;
import org.pgorecki.zadanie2.model.User;
import org.pgorecki.zadanie2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
}

package org.pgorecki.zadanie2.service;

import lombok.RequiredArgsConstructor;
import org.pgorecki.zadanie2.exception.ResourceNotFoundException;
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

    public void deleteUser(Long id) {
        User userToDelete = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        userRepository.deleteById(userToDelete.getId());
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
}

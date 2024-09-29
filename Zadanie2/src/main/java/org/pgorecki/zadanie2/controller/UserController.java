package org.pgorecki.zadanie2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.pgorecki.zadanie2.dto.UserDto;
import org.pgorecki.zadanie2.model.User;
import org.pgorecki.zadanie2.repository.UserSearchDao;
import org.pgorecki.zadanie2.dto.UserSearchRequest;
import org.pgorecki.zadanie2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserSearchDao userSearchDao;
    private final ModelMapper modelMapper;

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public URI createUser(@Valid @RequestBody User newUser) {
        User createdUser = userService.createUser(newUser);
        log.info("A new user has been created with id: {}", createdUser.getId());
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        log.warn("User with id: {} has been removed", id);
        return String.format("User with id: %s deleted successfully", id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserInformation(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return convertToUserDto(user);
    }

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email
    ) {
        UserSearchRequest userSearchRequest = new UserSearchRequest(firstName, lastName, email);

        List<User> users = userSearchDao.findByCriteria(userSearchRequest.getCriteriaParams());

        return users.stream()
                .map(this::convertToUserDto)
                .toList();
    }
}

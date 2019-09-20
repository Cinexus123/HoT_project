package com.example.template.controllers;

import com.example.template.commands.UserCommand;
import com.example.template.entities.User;
import com.example.template.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("getUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Getting list of users...");
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("addUser")
    public ResponseEntity<User> saveUser(@RequestBody UserCommand userCommand) {
        User user = userService.saveUser(new User(userCommand.getUsername()));
        log.info("Posted new item into user repository: \nID: " + user.getId() + "\nUsername: " + user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

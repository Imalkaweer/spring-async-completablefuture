package com.aynccompletablefuture.demoasync.controller;

import com.aynccompletablefuture.demoasync.entity.dto.UserDTO;
import com.aynccompletablefuture.demoasync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestBody final List<UserDTO> userDTOList) throws Exception {

            userService.saveUsers(userDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        return  userService.getAllUsers().thenApply(ResponseEntity::ok);
    }
}

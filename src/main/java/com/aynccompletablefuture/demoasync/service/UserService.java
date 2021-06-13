package com.aynccompletablefuture.demoasync.service;

import com.aynccompletablefuture.demoasync.entity.User;
import com.aynccompletablefuture.demoasync.entity.dto.UserDTO;
import com.aynccompletablefuture.demoasync.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Async
    public CompletableFuture<List<User>> saveUsers (final List<UserDTO> userDTOList) throws Exception {
        final long start = System.currentTimeMillis();
        List<User> users = getUserList(userDTOList);
        LOGGER.info("saving list of users of size [{}] | Thread Name [{}]", users.size(), Thread.currentThread().getName());
        users = userRepository.saveAll(users);
        final long end = System.currentTimeMillis();
        LOGGER.info("Time to Invoke transaction in saving [{}]", (end- start));
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<User>> getAllUsers(){

        LOGGER.info("get list of users [{}]", Thread.currentThread().getName());
        final long start = System.currentTimeMillis();
        List<User> users = userRepository.findAll();
        final long end = System.currentTimeMillis();
        LOGGER.info("time to complete task in retrieving [{}]", (end-start));
        return CompletableFuture.completedFuture(users);
    }

    private List<User> getCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        try {
            try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final String[] userData = line.split(",");
                    final User user = new User();
                    user.setFirst_name(userData[0]);
                    user.setLast_name(userData[1]);
                    user.setEmail(userData[2]);
                    user.setGender(userData[3]);
                    users.add(user);
                }
                return users;
            }
        } catch (final IOException e) {
            LOGGER.error("Failed to read CSV File [{}]", e.getCause());
            throw new Exception("Failed to read CSV File [{}]", e);
        }
    }

    private List<User> getUserList(final List<UserDTO> userDTOList){
        final List<User> userList = new ArrayList<>();
        userDTOList.stream().forEach(u ->{
            final User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setFirst_name(u.getFirst_name());
            user.setLast_name(u.getLast_name());
            user.setEmail(u.getEmail());
            user.setGender(u.getGender());
            userList.add(user);
        });
        return userList;
    }
}

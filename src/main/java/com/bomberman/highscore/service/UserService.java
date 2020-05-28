package com.bomberman.highscore.service;

import com.bomberman.highscore.entity.User;
import com.bomberman.highscore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public User createUser(String userName, int score){
        User user = getUserByUserName(userName);
        if(user == null){
            user = new User(userName, score);
            userRepository.save(user);
        }
        return user;
    }

    public User getUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User getOneUser(long userId){
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user, long userId){
        Optional<User> updateUser = userRepository.findById(userId);
        updateUser.get().setId(userId);
        updateUser.get().setUserName(user.getUserName());
        updateUser.get().setScore(user.getScore());
        return user;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User deleteUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        userRepository.deleteById(userId);
        return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

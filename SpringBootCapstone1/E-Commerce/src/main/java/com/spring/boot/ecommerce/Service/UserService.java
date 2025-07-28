package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean updateUser(String userID, User user) {
        for (User u : users) {
            if (u.getId().equals(userID)) {
                users.set(users.indexOf(u), user);
                return true;
            }
        }

        return false;
    }

    public boolean deleteUser(String userID) {
        for (User u : users) {
            if (u.getId().equals(userID)) {
                users.remove(u);
                return true;
            }
        }

        return false;
    }

    public boolean checkAvailableUser(String userID) {
        for (User u : users) {
            if (u.getId().equals(userID)) {
                return true;
            }
        }

        return false;
    }
    
}

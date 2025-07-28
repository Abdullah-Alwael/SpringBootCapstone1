package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.Product;
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

    public User getUser(String userID) {
        for (User u : users) {
            if (u.getId().equals(userID)) {
                return u;
            }
        }

        return null;
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

    // to make the user balance private, another method is created to pay the amount
    public boolean canPay(String userID, double amount) {
        for (User u : users) {
            if (u.getId().equals(userID)) {
                if (u.getBalance() >= amount) {
                    return true; // can pay
                }
                break; // no need to check the rest of users.
            }
        }

        return false; // user does not exist error
    }

    public boolean pay(String userID, double amount) {
        for (User u : users) {
            if (u.getId().equals(userID)) {
                if (u.getBalance() >= amount) {
                    u.setBalance(u.getBalance()-amount);
                    return true; // paid
                }
                break; // no need to check the rest of users.
            }
        }

        return false; // payment error
    }

}

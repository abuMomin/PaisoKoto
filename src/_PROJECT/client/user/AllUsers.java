package _PROJECT.client.user;

import java.util.ArrayList;
import java.util.List;

public class AllUsers {
    //to creatw an arraylist
    List<users> allUsers;

    public AllUsers(){
        this.allUsers = new ArrayList<>();
        this.allUsers.add(new users("win", "123", 0, 1));
        this.allUsers.add(new users("nayem", "456", 0, 2));
        this.allUsers.add(new users("teacher", "789", 1, 3));
        this.allUsers.add(new users("aftab", "111", 0, 4));

    }

    public List<users> getAllUsers() {
        return allUsers;
    }
}

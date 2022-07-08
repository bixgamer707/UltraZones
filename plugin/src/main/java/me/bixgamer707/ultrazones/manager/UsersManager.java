package me.bixgamer707.ultrazones.manager;

import me.bixgamer707.ultrazones.Main;
import me.bixgamer707.ultrazones.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UsersManager {
    private Main plugin;
    private final Map<UUID, User> userMap;
    public UsersManager(Main plugin){
        this.plugin = plugin;
        this.userMap = new HashMap<>();
    }

    public void addUser(UUID uuid,User user){
        userMap.put(uuid,user);
    }

    public User getUserByUuid(UUID uuid){
        return userMap.get(uuid);
    }

}

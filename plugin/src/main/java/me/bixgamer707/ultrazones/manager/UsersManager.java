package me.bixgamer707.ultrazones.manager;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UsersManager {
    private UltraZones plugin;
    private final Map<UUID, User> userMap;
    public UsersManager(UltraZones plugin){
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

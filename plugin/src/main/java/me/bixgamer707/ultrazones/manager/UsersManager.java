package me.bixgamer707.ultrazones.manager;

import me.bixgamer707.ultrazones.user.User;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UsersManager implements LoaderManager{
    public ConcurrentMap<UUID, User> userMap;

    public CompletableFuture<User> getUserByUuid(UUID uuid, String name){
        return CompletableFuture.supplyAsync(() -> {
            User user = userMap.get(uuid);
            if(user == null){
                user = new User(uuid, name);
            }

            userMap.put(uuid,user);
            return user;
        });
    }

    @Override
    public void start() {
        userMap = new ConcurrentHashMap<>();
    }

    @Override
    public void stop() {

    }
}

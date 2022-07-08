package me.bixgamer707.ultrazones.user;

import me.bixgamer707.ultrazones.wgevents.events.RegionEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionLeftEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsLeftEvent;

import java.util.UUID;

public abstract class UserData {

    protected UUID uuid;
    protected StringBuilder name;

    public UserData(UUID uuid, StringBuilder name){
        this.uuid = uuid;
        this.name = name;
    }
    public abstract void onRegionJoin(RegionEnteredEvent event);

    public abstract void onRegionsJoin(RegionsEnteredEvent event);

    public abstract void onRegionLeft(RegionLeftEvent event);

    public abstract void onRegionsLeft(RegionsLeftEvent event);


    public UUID getUniqueId() {
        return uuid;
    }

    public StringBuilder getName() {
        return name;
    }

}

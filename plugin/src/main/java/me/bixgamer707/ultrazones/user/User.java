package me.bixgamer707.ultrazones.user;

import me.bixgamer707.ultrazones.wgevents.events.RegionEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionLeftEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsLeftEvent;

import java.util.UUID;

public class User extends UserData{
    public User(UUID uuid, StringBuilder name) {
        super(uuid, name);
    }

    @Override
    public void onRegionJoin(RegionEnteredEvent event) {
        setRegionId(new StringBuilder(event.getRegionName()));
        System.out.println("ENTRANDO(s): "+event.getRegionName());
    }

    @Override
    public void onRegionsJoin(RegionsEnteredEvent event) {
        System.out.println("ENTRANDO(s): "+event.getRegions());
    }

    @Override
    public void onRegionLeft(RegionLeftEvent event) {
        System.out.println("SALIENDO(s): "+event.getRegion());
    }

    @Override
    public void onRegionsLeft(RegionsLeftEvent event) {
        System.out.println("SALIENDO(s): "+event.getRegions());
    }
}

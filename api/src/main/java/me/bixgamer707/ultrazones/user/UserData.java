package me.bixgamer707.ultrazones.user;

import me.bixgamer707.ultrazones.wgevents.events.RegionEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionLeftEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsLeftEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class UserData {

    protected UUID uuid;
    protected List<String> zonesJoined;
    protected String name;
    protected List<String> zonesExit;
    protected String currentZone;

    public UserData(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
        this.zonesJoined = new ArrayList<>();
        this.zonesExit = new ArrayList<>();
        this.currentZone = "none";
    }
    public abstract void onRegionJoin(RegionEnteredEvent event);

    public abstract void onRegionsJoin(RegionsEnteredEvent event);

    public abstract void onRegionLeft(RegionLeftEvent event);

    public abstract void onRegionsLeft(RegionsLeftEvent event);


    public List<String> getZonesJoined() {
        return zonesJoined;
    }

    public void addZone(String id){
        if(!zonesJoined.contains(id)){
            zonesJoined.add(id);
        }
    }

    public List<String> getZonesExit() {
        return zonesExit;
    }

    public void addZoneExit(String id){
        if(!zonesExit.contains(id)){
            zonesExit.add(id);
        }
    }

    public void setZonesExit(List<String> zonesExit) {
        this.zonesExit = zonesExit;
    }

    public void setZonesJoined(List<String> zonesJoined) {
        this.zonesJoined = zonesJoined;
    }

    protected abstract void loadData();

    protected abstract void saveData();

    public UUID getUniqueId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getCurrentZone() {
        return currentZone;
    }

    public void setCurrentZone(String currentZone) {
        this.currentZone = currentZone;
    }
}

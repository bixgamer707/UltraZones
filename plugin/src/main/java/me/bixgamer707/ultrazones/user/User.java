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
        System.out.println("ENTRANDO(s): "+event.getRegionName());
    }
    /*
    Zones:
  spawn:
    placerholder:
      enable: true
      placeholder: "zone_player"
      replacer: "&8&l| &aSPAWN &8&L|"
    join:
      execute-commands:
        - "broadcast &8> &a+ &8< &7%player%"
      sounds:
        to-server:
          - BLOCK_NOTE_BLOCK_PLING;10;2
        player:
          - BLOCK_NOTE_BLOCK_PLING;10;0.
      messages:
        action-bar:
          enable: true
          message: "&aYou has been join in the spawn"
        message: "&aYou has been join in the spawn"
    left:
      execute-commands:
        - "broadcast &8> &a+ &8< &7%player%"
      sounds:
        to-server:
          - BLOCK_NOTE_BLOCK_PLING;10;2
        player:
          - BLOCK_NOTE_BLOCK_PLING;10;0.
      messages:
        action-bar:
          enable: true
          message: "&aYou has been join in the spawn"
        message: "&aYou has been join in the spawn"
     */

    @Override
    public void onRegionsJoin(RegionsEnteredEvent event) {
        System.out.println("ENTRANDO(s): "+event.getRegionsNames());
    }

    @Override
    public void onRegionLeft(RegionLeftEvent event) {
        System.out.println("SALIENDO(s): "+event.getRegionName());
    }

    @Override
    public void onRegionsLeft(RegionsLeftEvent event) {
        System.out.println("SALIENDO(s): "+event.getRegionsNames());
    }
}

package me.bixgamer707.ultrazones.wgevents;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class WorldGuardChecks {

    static RegionContainer container;
    public WorldGuardChecks(RegionContainer container){
        WorldGuardChecks.container = container;
    }

    /**
     * Gets the regions a player is currently in.
     *
     * @param playerUUID UUID of the player in question.
     * @return Set of WorldGuard protected regions that the player is currently in.
     */
    @Nonnull
    public static Set<ProtectedRegion> getRegions(UUID playerUUID)
    {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null || !player.isOnline())
            return Collections.emptySet();

        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()));
        return set.getRegions();
    }

    /**
     * Gets the regions names a player is currently in.
     *
     * @param playerUUID UUID of the player in question.
     * @return Set of Strings with the names of the regions the player is currently in.
     */
    @Nonnull
    public static Set<String> getRegionsNames(UUID playerUUID)
    {
        return getRegions(playerUUID).stream().map(ProtectedRegion::getId).collect(Collectors.toSet());
    }

    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID UUID of the player in question.
     * @param regionNames Set of regions to check.
     * @return True if the player is in (all) the named region(s).
     */
    public static boolean isPlayerInAllRegions(UUID playerUUID, Set<String> regionNames)
    {
        Set<String> regions = getRegionsNames(playerUUID);
        if(regionNames.isEmpty()) throw new IllegalArgumentException("You need to check for at least one region !");

        return regions.containsAll(regionNames.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID UUID of the player in question.
     * @param regionNames Set of regions to check.
     * @return True if the player is in (any of) the named region(s).
     */
    public static boolean isPlayerInAnyRegion(UUID playerUUID, Set<String> regionNames)
    {
        Set<String> regions = getRegionsNames(playerUUID);
        if(regionNames.isEmpty()) throw new IllegalArgumentException("You need to check for at least one region !");
        for(String region : regionNames)
        {
            if(regions.contains(region.toLowerCase()))
                return true;
        }
        return false;
    }

    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID UUID of the player in question.
     * @param regionName List of regions to check.
     * @return True if the player is in (any of) the named region(s).
     */
    public static boolean isPlayerInAnyRegion(UUID playerUUID, String... regionName)
    {
        return isPlayerInAnyRegion(playerUUID, new HashSet<>(Arrays.asList(regionName)));
    }

    /**
     * Checks whether a player is in one or several regions
     *
     * @param playerUUID UUID of the player in question.
     * @param regionName List of regions to check.
     * @return True if the player is in (any of) the named region(s).
     */
    public static boolean isPlayerInAllRegions(UUID playerUUID, String... regionName)
    {
        return isPlayerInAllRegions(playerUUID, new HashSet<>(Arrays.asList(regionName)));
    }
}

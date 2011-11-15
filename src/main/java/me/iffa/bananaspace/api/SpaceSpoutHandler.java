// Package Declaration
package me.iffa.bananaspace.api;

// Java Imports
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.runnables.SpoutFixRunnable;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.entity.Entity;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Useful methods for Spout&Spoutcraft-only features.
 * 
 * @author iffamies
 * @author Adamki11s
 */
public class SpaceSpoutHandler {
    /**
     * Sets or resets Spoutcraft-only features.
     * 
     * @param plugin BananaSpace instance
     * @param player SpoutPlayer
     * @param location Location
     */
    public static void setOrReset(BananaSpace plugin, SpoutPlayer player, Location location) {
        SkyManager sky = SpoutManager.getSkyManager();
        if (BananaSpace.getWorldHandler().isSpaceWorld(location.getWorld())) {
            sky.setCloudsVisible(player, false);
            sky.setMoonVisible(player, false);
            sky.setCloudsVisible(player, false);
            sky.setStarFrequency(player, 4200);
            player.setTexturePack(SpaceConfigHandler.getSpoutTexturePack());
            SpaceMessageHandler.debugPrint(Level.INFO, "Set " + player.getName() + "'s texture pack");
            BananaSpace.scheduler.scheduleSyncDelayedTask(plugin, new SpoutFixRunnable(player), 10L);
            SpaceMessageHandler.debugPrint(Level.INFO, "Made clouds and the moon invisible for player '" + player.getName() + "'. Starting runnable thread to setup Player movements...");
        } else {
            sky.setCloudsVisible(player, true);
            sky.setMoonVisible(player, true);
            sky.setStarFrequency(player, 500);
            SpaceMessageHandler.debugPrint(Level.INFO, "Made clouds visible for player '" + player.getName() + "'.");
            player.resetTexturePack();
            SpaceMessageHandler.debugPrint(Level.INFO, "Reset " + player.getName() + "'s texture pack");
            player.setCanFly(false);
            player.resetMovement();
            SpaceMessageHandler.debugPrint(Level.INFO, "Reset player '" + player.getName() + "'s gravity and visual settings.");
        }
    }

    /**
     * Checks if an entity is inside a radius.
     * 
     * @param entity Entity
     * @param centre Center of radius
     * @param radius Radius
     * 
     * @return True if inside radius
     */
    public static boolean isInsideRadius(Entity entity, Location centre, int radius) {
        Location point1 = new Location(centre.getWorld(), centre.getX() - radius, centre.getY() - radius, centre.getZ() - radius);
        Location point2 = new Location(centre.getWorld(), centre.getX() + radius, centre.getY() + radius, centre.getZ() + radius);
        double x1 = point1.getX(), x2 = point2.getX(),
                y1 = point1.getY(), y2 = point2.getY(),
                z1 = point1.getZ(), z2 = point2.getZ(),
                px = entity.getLocation().getX(),
                py = entity.getLocation().getY(),
                pz = entity.getLocation().getZ();
        if ((((py <= y1)
                && (py >= y2))
                || ((py >= y1)
                && (py <= y2)))
                && (((pz <= z1)
                && (pz >= z2))
                || ((pz >= z1)
                && (pz <= z2)))
                && (((px <= x1)
                && (px >= x2))
                || ((px >= x1)
                && (px <= x2)))
                && (((px <= x1)
                && (px >= x2))
                || ((px >= x1)
                && (px <= x2)))) {
            return true;
        }
        return false;
    }

    /**
     * Constructor of SpaceSpoutHandler.
     */
    private SpaceSpoutHandler() {
    }
}

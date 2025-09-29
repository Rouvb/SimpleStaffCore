package net.arkamc.simpleStaffCore.vanish;

import net.arkamc.simpleStaffCore.util.VisibilityUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class VanishListener implements Listener {

    private void updateVisibility() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            VisibilityUtil.updateVisibility(player);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateVisibility();
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        updateVisibility();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        updateVisibility();
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        updateVisibility();
    }
}

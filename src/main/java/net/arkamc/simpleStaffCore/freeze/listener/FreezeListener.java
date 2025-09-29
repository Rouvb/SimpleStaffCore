package net.arkamc.simpleStaffCore.freeze.listener;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {

    private static final String FREEZE_METADATA = "freeze";
    private static final BasicConfigFile configFile = SimpleStaffCore.getInstance().getConfigFile();
    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();

    private void executeDisconnectAction(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), configFile.getString("freeze.disconnect_action")
                .replace("{player}", player.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!player.hasMetadata(FREEZE_METADATA)) {
            return;
        }

        executeDisconnectAction(player);

        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        for (Profile profile : profileManager.getProfiles()) {
            Player staff = profile.getPlayer();
            staff.sendMessage(langFile.getString("freeze.staff_alert.log_out")
                    .replace("{target}", player.getName()));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata(FREEZE_METADATA)) {
            Location from = event.getFrom();
            Location to = event.getTo();

            to.setX(from.getX());
            to.setZ(from.getZ());

            event.setTo(to);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (player.hasMetadata(FREEZE_METADATA)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player victim = (Player) event.getEntity();
        if (victim.hasMetadata(FREEZE_METADATA)) {
            event.setCancelled(true);
            return;
        }

        Player damager = null;
        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        } else if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if ((projectile.getShooter() instanceof Player)) {
                damager = (Player) projectile.getShooter();
            }
        }

        if (damager != null && damager.hasMetadata(FREEZE_METADATA)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (player.hasMetadata(FREEZE_METADATA)) {
            event.setCancelled(true);
        }
    }
}

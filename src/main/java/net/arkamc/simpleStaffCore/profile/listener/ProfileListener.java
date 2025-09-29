package net.arkamc.simpleStaffCore.profile.listener;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.profile.ProfileStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission("simplestaffcore.staff")) {
            return;
        }

        // Add staff member to profile structure
        Profile profile = new Profile(event.getPlayer(), ProfileStatus.NORMAL);
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        profileManager.addProfile(profile);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile profile = profileManager.getProfileByUuid(event.getPlayer().getUniqueId());
        profileManager.removeProfile(profile);
    }
}

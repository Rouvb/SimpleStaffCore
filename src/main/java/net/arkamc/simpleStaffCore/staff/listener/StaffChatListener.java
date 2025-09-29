package net.arkamc.simpleStaffCore.staff.listener;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener implements Listener {

    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile profile = profileManager.getProfileByUuid(event.getPlayer().getUniqueId());

        if (profile == null || !profile.isStaffChat()) {
            return;
        }

        event.setCancelled(true);

        for (Profile staffProfile : profileManager.getProfiles()) {
            Player staff = staffProfile.getPlayer();
            staff.sendMessage(langFile.getString("staff_chat.message")
                    .replace("{player}", event.getPlayer().getName())
                    .replace("{message}", event.getMessage()));
        }
    }

}

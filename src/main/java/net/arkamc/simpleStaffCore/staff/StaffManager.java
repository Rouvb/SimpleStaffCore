package net.arkamc.simpleStaffCore.staff;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import net.arkamc.simpleStaffCore.util.VisibilityUtil;
import org.bukkit.entity.Player;

public class StaffManager {

    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();

    public static void toggleStaffMode(Player player) {
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile profile = profileManager.getProfileByUuid(player.getUniqueId());

        if (!profile.isStaffMode()) {
            profile.setStaffMode(true);

            // Save current player's inventory contents
            profile.setContents(player.getInventory().getContents());
            profile.setArmorContents(player.getInventory().getArmorContents());
            profile.setVanish(true);
            VisibilityUtil.updateVisibility(player);

            // Clear player's inventory
            player.getInventory().clear();
            player.getInventory().setItem(0, StaffItems.RANDOM_TELEPORT);
            player.getInventory().setItem(1, StaffItems.FREEZE);
            player.getInventory().setItem(8, StaffItems.VISIBILITY_ON);

            player.setAllowFlight(true);
            player.setFlying(true);

            player.sendMessage(langFile.getString("staff_mode.enabled"));
        } else {
            profile.setStaffMode(false);
            profile.setVanish(false);

            VisibilityUtil.updateVisibility(player);

            player.getInventory().setContents(profile.getContents());
            player.getInventory().setArmorContents(profile.getArmorContents());

            player.setAllowFlight(false);
            player.setFlying(false);

            player.sendMessage(langFile.getString("staff_mode.disabled"));
        }
    }
}

package net.arkamc.simpleStaffCore.util;

import lombok.experimental.UtilityClass;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public final class VisibilityUtil {
    public static void updateVisibility(Player target) {
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (shouldSeePlayer(target)) {
                otherPlayer.showPlayer(target);
            } else {
                otherPlayer.hidePlayer(target);
            }
        }
    }

    private static boolean shouldSeePlayer(Player target) {
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile profile = profileManager.getProfileByUuid(target.getUniqueId());

        if (profile == null) {
            return true;
        }

        return !profile.isVanish();
    }
}

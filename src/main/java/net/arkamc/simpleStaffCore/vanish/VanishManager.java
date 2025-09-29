package net.arkamc.simpleStaffCore.vanish;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import net.arkamc.simpleStaffCore.util.VisibilityUtil;
import org.bukkit.entity.Player;

public class VanishManager {

    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();

    public static void toggleVanish(Player player) {
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile profile = profileManager.getProfileByUuid(player.getUniqueId());

        if (profile == null) {
            player.sendMessage(langFile.getString("vanish.cannot_vanish"));
            return;
        }

        profile.setVanish(!profile.isVanish());
        VisibilityUtil.updateVisibility(player);

        if (profile.isVanish()) {
            player.sendMessage(langFile.getString("vanish.enabled"));
        } else {
            player.sendMessage(langFile.getString("vanish.disabled"));
        }
    }
}

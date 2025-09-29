package net.arkamc.simpleStaffCore.freeze;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class FreezeManager {

    private static final String FREEZE_METADATA = "freeze";
    private static BasicConfigFile configFile = SimpleStaffCore.getInstance().getConfigFile();
    private static BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();

    public static void freezePlayer(Player target, Player staff) {

        if (target == null || !target.isOnline()) {
            staff.sendMessage(langFile.getString("freeze.player_not_found"));
            return;
        }

        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile targetProfile = profileManager.getProfileByUuid(target.getUniqueId());

        if (target.equals(staff) && configFile.getBoolean("freeze.prevent_freeze_staff")) {
            staff.sendMessage(langFile.getString("freeze.cannot_yourself"));
            return;
        }

        if (targetProfile != null) {
            staff.sendMessage(langFile.getString("freeze.cannot_staff"));
            return;
        }

        if (target.hasMetadata(FREEZE_METADATA)) {
            target.removeMetadata(FREEZE_METADATA, SimpleStaffCore.getInstance());
            staff.sendMessage(langFile.getString("freeze.staff_alert.unfrozen")
                    .replace("{target}", target.getName())
                    .replace("{staff}", staff.getName()));
        } else {
            FreezeTask task = new FreezeTask(target);
            task.runTaskTimer(SimpleStaffCore.getInstance(), 0, 20L * configFile.getInt("freeze.task_interval"));
            target.setMetadata(FREEZE_METADATA, new FixedMetadataValue(SimpleStaffCore.getInstance(), true));
            staff.sendMessage(langFile.getString("freeze.staff_alert.frozen")
                    .replace("{target}", target.getName())
                    .replace("{staff}", staff.getName()));
        }
    }
}

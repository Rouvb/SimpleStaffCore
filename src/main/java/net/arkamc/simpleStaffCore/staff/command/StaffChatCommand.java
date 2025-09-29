package net.arkamc.simpleStaffCore.staff.command;

import me.gleeming.command.Command;
import me.gleeming.command.paramter.Param;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import org.bukkit.entity.Player;

public class StaffChatCommand {
    
    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();

    @Command(names = {"staffchat", "sc"}, permission = "simplestaffcore.staffchat")
    public void staffChatCommand(Player sender, @Param(name = "message", required = false, defaultValue = " ") String message) {
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();

        if (!message.equals(" ")) {
            for (Profile profile : profileManager.getProfiles()) {
                Player staff = profile.getPlayer();
                staff.sendMessage(langFile.getString("staff_chat.message")
                        .replace("{player}", sender.getName())
                        .replace("{message}", message));
            }
            return;
        }

        Profile profile = profileManager.getProfileByUuid(sender.getUniqueId());
        profile.setStaffChat(!profile.isStaffChat());

        if (profile.isStaffChat()) {
            sender.sendMessage(langFile.getString("staff_chat.enabled"));
        } else {
            sender.sendMessage(langFile.getString("staff_chat.disabled"));
        }
    }

}

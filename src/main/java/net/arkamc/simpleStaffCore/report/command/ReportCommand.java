package net.arkamc.simpleStaffCore.report.command;

import me.gleeming.command.Command;
import me.gleeming.command.paramter.Param;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.report.ReportGui;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import org.bukkit.entity.Player;

public class ReportCommand {

    private static final SimpleStaffCore plugin = SimpleStaffCore.getInstance();
    private static final BasicConfigFile langFile = plugin.getLangFile();

    @Command(
            names = "report",
            permission = "simplestaffcore.report"
    )
    public void reportCommand(Player sender, @Param(name = "player") Player target) {
        if (target == null || !target.isOnline()) {
            sender.sendMessage(langFile.getString("report.player_not_found"));
            return;
        }

        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile profile = profileManager.getProfileByUuid(target.getUniqueId());

        if (target.equals(sender)) {
            sender.sendMessage(langFile.getString("report.cannot_yourself"));
            return;
        }

        if (profile != null) {
            sender.sendMessage(langFile.getString("report.cannot_staff"));
            return;
        }

        if (plugin.getReportCooldownHandler().isOnCooldown(sender)) {
            sender.sendMessage(langFile.getString("report.cooldown"));
        }

        ReportGui reportGUI = new ReportGui(sender, target);
        reportGUI.getReportGui().open(sender);
    }
}

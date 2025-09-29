package net.arkamc.simpleStaffCore.staff.command;

import me.gleeming.command.Command;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.staff.StaffManager;
import net.arkamc.simpleStaffCore.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand {

    private static final SimpleStaffCore plugin = SimpleStaffCore.getInstance();

    @Command(names = {"staff", "staffmode", "mod", "modmode"}, permission = "simplestaffcore.staff")
    public static void staffCommand(Player sender) {
        StaffManager.toggleStaffMode(sender);
    }

    @Command(names = {"staff reload"}, permission = "simplestaffcore.admin")
    public static void staffReloadCommand(CommandSender sender) {
        plugin.getConfigFile().reload();
        plugin.getLangFile().reload();
        plugin.getReportGuiFile().reload();
        plugin.getStaffItemsFile().reload();

        sender.sendMessage(CC.translate("&aSimpleStaffCore has been reloaded."));
    }
}

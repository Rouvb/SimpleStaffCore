package net.arkamc.simpleStaffCore.freeze;

import me.gleeming.command.Command;
import me.gleeming.command.paramter.Param;
import org.bukkit.entity.Player;

public class FreezeCommand {

    @Command(names = {"freeze", "ss", "screenshare"}, permission = "simplestaffcore.freeze")
    public void freezeCommand(Player sender, @Param(name = "target") Player target) {
        FreezeManager.freezePlayer(target, sender);
    }
}

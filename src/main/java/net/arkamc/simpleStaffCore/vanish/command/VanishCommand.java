package net.arkamc.simpleStaffCore.vanish.command;

import me.gleeming.command.Command;
import net.arkamc.simpleStaffCore.vanish.VanishManager;
import org.bukkit.entity.Player;

public class VanishCommand {

    @Command(
            names = "vanish",
            permission = "simplestaffcore.vanish"
    )
    public void vanishCommand(Player sender) {
        VanishManager.toggleVanish(sender);
    }
}

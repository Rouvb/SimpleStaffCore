package net.arkamc.simpleStaffCore.freeze;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FreezeTask extends BukkitRunnable {

    private static final String FREEZE_METADATA = "freeze";
    private static final BasicConfigFile configFile = SimpleStaffCore.getInstance().getLangFile();
    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();
    private final Player player;

    public FreezeTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (!player.hasMetadata(FREEZE_METADATA)) {
            this.cancel();
            return;
        }

        langFile.getStringList("freeze.frozen").forEach(player::sendMessage);
        player.playSound(player.getLocation(), Sound.valueOf(configFile.getString("freeze.sound")), 1.0f, 1.0f);
    }
}

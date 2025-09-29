package net.arkamc.simpleStaffCore.report;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReportCooldownHandler {

    @Getter
    private final Set<UUID> cooldowns;
    private int cooldownTime = SimpleStaffCore.getInstance().getConfigFile().getInt("report.cooldown");

    public ReportCooldownHandler() {
        cooldowns = new HashSet<>();
    }

    public void addCooldown(Player player) {
        cooldowns.add(player.getUniqueId());

        Bukkit.getScheduler().runTaskLaterAsynchronously(SimpleStaffCore.getInstance(),
                () -> cooldowns.remove(player.getUniqueId()), cooldownTime * 20L);
    }

    public boolean isOnCooldown(Player player) {
        return cooldowns.contains(player.getUniqueId());
    }
}

package net.arkamc.simpleStaffCore.staff.listener;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.freeze.FreezeManager;
import net.arkamc.simpleStaffCore.staff.StaffItems;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import net.arkamc.simpleStaffCore.vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StaffItemListener implements Listener {

    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!event.getAction().toString().contains("RIGHT")) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getItemMeta() == null) {
            return;
        }
        if (item.isSimilar(StaffItems.RANDOM_TELEPORT)) {
            randomTeleport(player);
        } else if (item.isSimilar(StaffItems.VISIBILITY_ON)) {
            toggleVisibility(player);
            player.getInventory().setItem(8, StaffItems.VISIBILITY_OFF);
        } else if (item.isSimilar(StaffItems.VISIBILITY_OFF)) {
            toggleVisibility(player);
            player.getInventory().setItem(8, StaffItems.VISIBILITY_ON);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player staff = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();

        if (!(clickedEntity instanceof Player)) {
            return;
        }

        Player target = (Player) clickedEntity;

        ItemStack item = staff.getItemInHand();
        if (item.isSimilar(StaffItems.FREEZE)) {
            FreezeManager.freezePlayer(target, staff);
        }
    }

    private void randomTeleport(Player player) {
        List<Player> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.hasPermission("simplestaffcore.staff"))
                .collect(Collectors.toList());

        if (onlinePlayers.isEmpty()) {
            player.sendMessage(langFile.getString("random_teleport.player_not_found"));
            return;
        }

        Random rand = new Random();
        int rantInt = rand.nextInt(onlinePlayers.size());

        player.teleport(onlinePlayers.get(rantInt));
        player.sendMessage(langFile.getString("random_teleport.teleported"));
    }

    private void toggleVisibility(Player player) {
        VanishManager.toggleVanish(player);
    }
}

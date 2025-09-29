package net.arkamc.simpleStaffCore.staff;

import java.util.List;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import net.arkamc.simpleStaffCore.util.ItemBuilder;
import net.arkamc.simpleStaffCore.util.VisibilityUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class StaffManager {

    private static final BasicConfigFile langFile = SimpleStaffCore.getInstance().getLangFile();
    private static final BasicConfigFile staffItemsFile = SimpleStaffCore.getInstance().getStaffItemsFile();

    public static void toggleStaffMode(Player player) {
        ProfileManager profileManager = SimpleStaffCore.getInstance().getProfileManager();
        Profile profile = profileManager.getProfileByUuid(player.getUniqueId());

        if (!profile.isStaffMode()) {
            profile.setStaffMode(true);

            // Save current player's inventory contents
            profile.setContents(player.getInventory().getContents());
            profile.setArmorContents(player.getInventory().getArmorContents());
            profile.setVanish(true);
            VisibilityUtil.updateVisibility(player);

            // Clear player's inventory
            player.getInventory().clear();

            for (final String key : staffItemsFile.getConfigurationSection("staff_items").getKeys(false)) {
                final ConfigurationSection section = staffItemsFile.getConfigurationSection("staff_items." + key);
                if (section != null) {
                    final String name = section.getString("name");
                    final List<String> lore = section.getStringList("lore");
                    final Material material = Material.getMaterial(section.getString("material"));
                    final int slot = section.getInt("slot");
                    final int durability = section.getInt("durability");

                    player.getInventory().setItem(slot, new ItemBuilder(material)
                            .name(name)
                            .lore(lore)
                            .durability(durability)
                            .build());
                }
            }

            player.setAllowFlight(true);
            player.setFlying(true);

            player.sendMessage(langFile.getString("staff_mode.enabled"));
        } else {
            profile.setStaffMode(false);
            profile.setVanish(false);

            VisibilityUtil.updateVisibility(player);

            player.getInventory().setContents(profile.getContents());
            player.getInventory().setArmorContents(profile.getArmorContents());

            player.setAllowFlight(false);
            player.setFlying(false);

            player.sendMessage(langFile.getString("staff_mode.disabled"));
        }
    }
}

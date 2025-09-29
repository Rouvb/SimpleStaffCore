package net.arkamc.simpleStaffCore.staff;

import net.arkamc.simpleStaffCore.util.CC;
import net.arkamc.simpleStaffCore.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StaffItems {

    public static ItemStack RANDOM_TELEPORT = new ItemBuilder(Material.COMPASS)
            .name(CC.translate("&bRandom Teleport"))
            .build();

    public static ItemStack FREEZE = new ItemBuilder(Material.ICE)
            .name(CC.translate("&bFreeze"))
            .build();

    public static ItemStack VISIBILITY_ON = new ItemBuilder(Material.INK_SACK)
            .name(CC.translate("&bVanish &7(&aON&7)"))
            .durability(10)
            .build();

    public static ItemStack VISIBILITY_OFF = new ItemBuilder(Material.INK_SACK)
            .name(CC.translate("&bVanish &7(&cOFF&7)"))
            .durability(8)
            .build();
}

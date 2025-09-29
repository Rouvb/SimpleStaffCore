package net.arkamc.simpleStaffCore.staff;

import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import net.arkamc.simpleStaffCore.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StaffItems {

    private static final BasicConfigFile staffItemsFile = SimpleStaffCore.getInstance()
            .getStaffItemsFile();

    public static ItemStack RANDOM_TELEPORT = new ItemBuilder(
            Material.getMaterial(staffItemsFile.getString("staff_items.random_teleport.material")))
            .name(staffItemsFile.getString("staff_items.random_teleport.name"))
            .lore(staffItemsFile.getStringList("staff_items.random_teleport.lore"))
            .durability(staffItemsFile.getInt("staff_items.random_teleport.durability"))
            .build();

    public static ItemStack FREEZE = new ItemBuilder(
            Material.getMaterial(staffItemsFile.getString("staff_items.freeze.material")))
            .name(staffItemsFile.getString("staff_items.freeze.name"))
            .lore(staffItemsFile.getStringList("staff_items.freeze.lore"))
            .durability(staffItemsFile.getInt("staff_items.freeze.durability"))
            .build();

    public static ItemStack VISIBILITY_ON = new ItemBuilder(
            Material.getMaterial(staffItemsFile.getString("staff_items.visibility_on.material")))
            .name(staffItemsFile.getString("staff_items.visibility_on.name"))
            .lore(staffItemsFile.getStringList("staff_items.visibility_on.lore"))
            .durability(staffItemsFile.getInt("staff_items.visibility_on.durability"))
            .build();

    public static ItemStack VISIBILITY_OFF = new ItemBuilder(
            Material.getMaterial(staffItemsFile.getString("staff_items.visibility_off.material")))
            .name(staffItemsFile.getString("staff_items.visibility_off.name"))
            .lore(staffItemsFile.getStringList("staff_items.visibility_off.lore"))
            .durability(staffItemsFile.getInt("staff_items.visibility_off.durability"))
            .build();
}
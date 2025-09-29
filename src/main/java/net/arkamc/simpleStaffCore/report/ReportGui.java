package net.arkamc.simpleStaffCore.report;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.arkamc.simpleStaffCore.SimpleStaffCore;
import net.arkamc.simpleStaffCore.profile.Profile;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import net.arkamc.simpleStaffCore.util.CC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ReportGui {

    private static final SimpleStaffCore plugin = SimpleStaffCore.getInstance();
    private static final BasicConfigFile configFile = plugin.getConfigFile();
    private static final BasicConfigFile langFile = plugin.getLangFile();
    private static final BasicConfigFile reportGuiFile = plugin.getReportGuiFile();
    private final Player executor;
    private final Player target;

    public ReportGui(Player executor, Player target) {
        this.executor = executor;
        this.target = target;
    }

    public Gui getReportGui() {
        final ConfigurationSection itemSection = reportGuiFile.getConfigurationSection("gui.items");
        if (itemSection == null) {
            return null;
        }

        Gui gui = Gui.gui()
                .title(Component.text(
                        reportGuiFile.getString("gui.title").replace("{player}", target.getName())))
                .rows(reportGuiFile.getInt("gui.rows"))
                .create();

        gui.getFiller().fill(getFillerItem());

        for (final String key : reportGuiFile.getConfigurationSection("gui.items").getKeys(false)) {
            final ConfigurationSection section = reportGuiFile.getConfigurationSection("gui.items." + key);
            if (section == null) {
                continue;
            }

            final String name = section.getString("name");
            final List<String> lore = section.getStringList("lore");
            final Material material = Material.getMaterial(section.getString("material"));
            final int slot = section.getInt("slot");
            final int durability = section.getInt("data");

            gui.setItem(slot, buildReportButton(name, lore, material, durability));
        }

        return gui;
    }

    private GuiItem getFillerItem() {
        ItemStack item = new ItemStack(Material.getMaterial(reportGuiFile.getString("gui.background.material")));
        item.setDurability((short) reportGuiFile.getInt("gui.background.durability"));
        return ItemBuilder
                .from(item)
                .name(Component.text(reportGuiFile.getString("gui.background.name")))
                .asGuiItem();
    }

    private GuiItem buildReportButton(String name, List<String> lore, Material material, int durability) {
        ItemStack item = new ItemStack(material);
        item.setDurability((byte) durability);

        String rawName = ChatColor.stripColor(CC.translate(name));

        List<String> safeLore = lore != null ? lore : Collections.emptyList();
        List<Component> translatedLore = safeLore.stream()
                .map(line -> LegacyComponentSerializer.legacyAmpersand().deserialize(line))
                .collect(Collectors.toList());

        return ItemBuilder
                .from(item)
                .name(Component.text(CC.translate(name)))
                .lore(translatedLore)
                .asGuiItem(
                        event -> {
                            executor.closeInventory();

                            plugin.getReportCooldownHandler().addCooldown(executor);

                            ProfileManager profileManager = plugin.getProfileManager();
                            for (Profile profile : new ArrayList<>(profileManager.getProfiles())) {
                                Player staff = profile.getPlayer();
                                if (staff != null && staff.isOnline()) {
                                    staff.sendMessage(langFile.getString("report.staff_alert")
                                            .replace("{reporter}", target.getName())
                                            .replace("{reporter}", executor.getName())
                                            .replace("{reason}", rawName));
                                }
                            }

                            executor.sendMessage(langFile.getString("report.reported"));

                            if (configFile.getBoolean("report.webhook.enabled")) {
                                ReportWebhookHandler.sendWebhook(target, executor, rawName);
                            }
                        }
                );
    }
}

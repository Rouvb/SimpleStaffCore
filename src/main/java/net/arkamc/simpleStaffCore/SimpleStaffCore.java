package net.arkamc.simpleStaffCore;

import lombok.Getter;
import me.gleeming.command.CommandHandler;
import net.arkamc.simpleStaffCore.freeze.FreezeCommand;
import net.arkamc.simpleStaffCore.freeze.listener.FreezeListener;
import net.arkamc.simpleStaffCore.profile.ProfileManager;
import net.arkamc.simpleStaffCore.profile.listener.ProfileListener;
import net.arkamc.simpleStaffCore.report.ReportCooldownHandler;
import net.arkamc.simpleStaffCore.report.command.ReportCommand;
import net.arkamc.simpleStaffCore.staff.command.StaffChatCommand;
import net.arkamc.simpleStaffCore.staff.command.StaffCommand;
import net.arkamc.simpleStaffCore.staff.listener.StaffChatListener;
import net.arkamc.simpleStaffCore.staff.listener.StaffItemListener;
import net.arkamc.simpleStaffCore.staff.listener.StaffPreventionListener;
import net.arkamc.simpleStaffCore.util.BasicConfigFile;
import net.arkamc.simpleStaffCore.vanish.VanishListener;
import net.arkamc.simpleStaffCore.vanish.command.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SimpleStaffCore extends JavaPlugin {

    @Getter
    private static SimpleStaffCore instance;
    private ProfileManager profileManager;
    private ReportCooldownHandler reportCooldownHandler;
    private BasicConfigFile configFile;
    private BasicConfigFile langFile;
    private BasicConfigFile reportGuiFile;
    private BasicConfigFile staffItemsFile;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadFiles();
        loadManagers();
        registerListeners();
        registerCommands();
        getLogger().info("SimpleStaffCore has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleStaffCore has been disabled.");
    }

    private void loadManagers() {
        this.profileManager = new ProfileManager();
        this.reportCooldownHandler = new ReportCooldownHandler();
    }

    private void loadFiles() {
        this.configFile = new BasicConfigFile(this, "config.yml");
        this.langFile = new BasicConfigFile(this, "lang.yml");
        this.reportGuiFile = new BasicConfigFile(this, "report_gui.yml");
        this.staffItemsFile = new BasicConfigFile(this, "staff_items.yml");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
        Bukkit.getPluginManager().registerEvents(new VanishListener(), this);
        Bukkit.getPluginManager().registerEvents(new StaffChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new StaffPreventionListener(), this);
        Bukkit.getPluginManager().registerEvents(new StaffItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new FreezeListener(), this);
    }

    private void registerCommands() {
        CommandHandler.registerCommands(ReportCommand.class, this);
        CommandHandler.registerCommands(VanishCommand.class, this);
        CommandHandler.registerCommands(StaffCommand.class, this);
        CommandHandler.registerCommands(StaffChatCommand.class, this);
        CommandHandler.registerCommands(FreezeCommand.class, this);
    }
}

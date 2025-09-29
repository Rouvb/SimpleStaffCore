package net.arkamc.simpleStaffCore.profile;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Profile {

    private UUID uuid;
    private ProfileStatus status;
    private boolean staffMode;
    private boolean vanish;
    private boolean staffChat;
    private ItemStack[] contents;
    private ItemStack[] armorContents;

    public Profile(Player player, ProfileStatus status) {
        this.uuid = player.getUniqueId();
        this.status = status;
        this.staffMode = false;
        this.vanish = false;
        this.staffChat = false;
        this.contents = new ItemStack[0];
        this.armorContents = new ItemStack[0];
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUuid());
    }
}

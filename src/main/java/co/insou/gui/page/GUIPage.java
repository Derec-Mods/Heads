package co.insou.gui.page;

import co.insou.gui.GUIPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class GUIPage<T extends JavaPlugin> {

    protected final T plugin;
    protected final GUIPlayer player;
    protected final String title;
    protected final Object[] params;

    private GUIInventory inventory;

    public GUIPage(T plugin, GUIPlayer player, String title, Object... params) {
        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.params = params;
    }

    protected abstract GUIInventory loadInventory();

    protected void onInventoryClick(InventoryClickEvent event) {
    }

    public void open() {
        inventory = loadInventory();
        player.player().openInventory(inventory.getInventory());
    }

    public Inventory getBukkitInventory() {
        return inventory == null ? null : inventory.getInventory();
    }

    public void handleClick(InventoryClickEvent event) {
        if (inventory instanceof PageInventory && ((PageInventory) inventory).handleNavigation(event)) {
            return;
        }
        onInventoryClick(event);
    }

}

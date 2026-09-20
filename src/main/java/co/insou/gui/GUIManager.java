package co.insou.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager implements Listener {

    private final Map<UUID, GUIPlayer> players = new HashMap<UUID, GUIPlayer>();

    public GUIManager(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GUIPlayer getPlayer(Player player) {
        GUIPlayer guiPlayer = players.get(player.getUniqueId());
        if (guiPlayer == null) {
            guiPlayer = new GUIPlayer(player);
            players.put(player.getUniqueId(), guiPlayer);
        }
        return guiPlayer;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        GUIPlayer guiPlayer = players.get(event.getWhoClicked().getUniqueId());
        if (guiPlayer == null || guiPlayer.getPage() == null) {
            return;
        }
        if (event.getView().getTopInventory() != guiPlayer.getPage().getBukkitInventory()) {
            return;
        }
        event.setCancelled(true);
        if (event.getClickedInventory() == null || event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }
        guiPlayer.getPage().handleClick(event);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        GUIPlayer guiPlayer = players.get(event.getWhoClicked().getUniqueId());
        if (guiPlayer == null || guiPlayer.getPage() == null) {
            return;
        }
        if (event.getView().getTopInventory() == guiPlayer.getPage().getBukkitInventory()) {
            event.setCancelled(true);
        }
    }

}

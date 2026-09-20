package co.insou.gui.page;

import co.insou.colorchar.ColorChar;
import co.insou.gui.GUIPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PageInventory implements GUIInventory, InventoryHolder {

    private static final int PAGE_SIZE = 45;

    private final GUIPlayer player;
    private final String title;
    private final List<ItemStack> items = new ArrayList<ItemStack>();

    private Inventory inventory;
    private int page;

    public PageInventory(GUIPlayer player, String title) {
        this.player = player;
        this.title = title;
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }

    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            build();
        }
        return inventory;
    }

    public boolean handleNavigation(InventoryClickEvent event) {
        if (items.size() <= 54 && inventory.getSize() <= 54 && items.size() <= inventory.getSize()) {
            return event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR;
        }
        int slot = event.getRawSlot();
        if (slot == 45 && page > 0) {
            page--;
            build();
            return true;
        }
        if (slot == 53 && page < maxPage()) {
            page++;
            build();
            return true;
        }
        return slot >= PAGE_SIZE || event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR;
    }

    private int maxPage() {
        return Math.max(0, (items.size() - 1) / PAGE_SIZE);
    }

    private void build() {
        if (items.size() <= 54) {
            int size = Math.max(9, ((items.size() + 8) / 9) * 9);
            if (inventory == null || inventory.getSize() != size) {
                inventory = Bukkit.createInventory(this, size, title);
            } else {
                inventory.clear();
            }
            for (ItemStack item : items) {
                inventory.addItem(item);
            }
            return;
        }
        if (inventory == null || inventory.getSize() != 54) {
            inventory = Bukkit.createInventory(this, 54, title);
        } else {
            inventory.clear();
        }
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, items.size());
        for (int i = start; i < end; i++) {
            inventory.addItem(items.get(i));
        }
        if (page > 0) {
            inventory.setItem(45, navItem("&aPrevious"));
        }
        if (page < maxPage()) {
            inventory.setItem(53, navItem("&aNext"));
        }
    }

    private static ItemStack navItem(String name) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorChar.color(name));
        item.setItemMeta(meta);
        return item;
    }

}

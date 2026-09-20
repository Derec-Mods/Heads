package co.insou.gui;

import co.insou.gui.page.GUIPage;
import org.bukkit.entity.Player;

public class GUIPlayer {

    private final Player player;
    private GUIPage<?> page;

    public GUIPlayer(Player player) {
        this.player = player;
    }

    public Player player() {
        return player;
    }

    public GUIPage<?> getPage() {
        return page;
    }

    public void openPage(GUIPage<?> page, boolean replace) {
        this.page = page;
        page.open();
    }

    public void closeGUI(boolean close) {
        this.page = null;
        if (close) {
            player.closeInventory();
        }
    }

}

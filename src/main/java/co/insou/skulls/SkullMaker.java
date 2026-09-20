package co.insou.skulls;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SkullMaker {

    private String skinUrl;
    private String name;
    private final List<String> lore = new ArrayList<String>();

    public SkullMaker withSkinUrl(String skinUrl) {
        this.skinUrl = skinUrl;
        return this;
    }

    public SkullMaker withName(String name) {
        this.name = name;
        return this;
    }

    public SkullMaker withLore(String... lore) {
        this.lore.addAll(Arrays.asList(lore));
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(name);
        }
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }
        if (skinUrl != null) {
            try {
                PlayerProfile profile = Bukkit.createPlayerProfile(
                        UUID.nameUUIDFromBytes(skinUrl.getBytes(StandardCharsets.UTF_8)),
                        "Heads"
                );
                PlayerTextures textures = profile.getTextures();
                textures.setSkin(new URL(skinUrl));
                profile.setTextures(textures);
                meta.setOwnerProfile(profile);
            } catch (Exception ignored) {
            }
        }
        item.setItemMeta(meta);
        return item;
    }

}

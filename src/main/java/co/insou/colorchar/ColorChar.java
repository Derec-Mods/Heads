package co.insou.colorchar;

import org.bukkit.ChatColor;

public final class ColorChar {

    private ColorChar() {
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String strip(String text) {
        return ChatColor.stripColor(text);
    }

    public static boolean equalsNoColor(String first, String second) {
        if (first == null || second == null) {
            return first == second;
        }
        return strip(first).equals(strip(second));
    }

}

package site.hellishmods.moderncustomdiscs.items;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

// Config loaded from json
public class DiscConfig {
    public boolean creeper; // Creeper-dropable
    public String name; // Disc display name
    public String author; // Disc music author
    String[] colors; // Disc item colors
    int redstone; // Comparator output

    @SuppressWarnings("deprecation")
    public String[] getColors() { // Color shenanigans
        if (colors==null) { // If no "color" parameter in json, make the disc white
            colors[0] = "ffffff";
            colors[1] = "ffffff";
        }
        else if (colors.length==1) { // If only one color, make the disc one color
            colors[1] = colors[0];
        }
        else if (colors.length>2) { // Cut off unneeded values
            colors = Arrays.copyOfRange(colors, 0, 1);
        }
        return ArrayUtils.add(colors, 0, "FFFFFF"); // Set the first layer (disc_base) to default color
    }
    public int getRedstone() {
        if (redstone>16) redstone = 16; // No bigger than 16
        else if (redstone<0) redstone = 0; // No smaller than 0
        return redstone;
    }
}

package site.hellishmods.moderncustomdiscs.items;

import java.io.File;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import site.hellishmods.moderncustomdiscs.moderncustomdiscs;

// Disc class
public class Disc {
    public final RegistryObject<CustomDiscItem> DISC_ITEM; // In-game disc item
    public final RegistryObject<SoundEvent> SOUND; // Music
    public final String ID; // Disc item id
    public final String NAME; // Disc sound id
    public final String DESC; // Disc item description
    public final boolean CREEPER; // Creeper-dropable
    public final String[] COLORS; // Disc item colors
    public final File TEXTURE; // Disc item texture

    // Constructor
    public Disc(String name, String displayname, String author, boolean creeperdrop, String[] colors, File texture, int redstone) {
        NAME = name; // Set the music id
        if (displayname==null) displayname = name; // Set the displayname to id if it's not defined
        if (author!=null) DESC = author + " — " + displayname; // Set the description (if author is not null)
        else DESC = displayname; // Set the description to displayname if author is null
        CREEPER = creeperdrop; // Set creeperdrop
        COLORS = colors; // Set colors
        TEXTURE = texture; // Set texture

        ID = "disc_"+name; // Set item id to "disc_{sound id}"

        // Register
        SOUND = moderncustomdiscs.MUSIC.register(name, () -> new SoundEvent(new ResourceLocation(moderncustomdiscs.MOD_ID, name))); // Register sound
        DISC_ITEM = moderncustomdiscs.DISCS.register(ID, 
            () -> new CustomDiscItem(SOUND, redstone)); // Register item
    }
}

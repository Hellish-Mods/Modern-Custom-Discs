package site.hellishmods.moderncustomdiscs.items;

import java.io.File;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import site.hellishmods.digitality.lib.VirtualDataPack;
import site.hellishmods.moderncustomdiscs.moderncustomdiscs;

// Disc class
public class Disc {
    final VirtualDataPack TAG_REGISTER = new VirtualDataPack("minecraft");
    public final RegistryObject<CustomDiscItem> DISC_ITEM; // In-game disc item
    public final RegistryObject<SoundEvent> SOUND; // Music
    public final File TEXTURE; // TEXTURE FILE
    public final String ID; // Disc item id
    public DiscConfig config; // Config

    // Constructor
    public Disc(String name, DiscConfig config, File texture) {
        this.config = config; // Set the config var
        TEXTURE = texture; // Set texture file
        ID = "disc_"+name; // Set the disc item id

        // Construct description
        String desc; // Description variable
        if (config.name==null) config.name = name; // If no display name, set to id
        if (config.author==null) desc = config.name; // If author is not defined, set the description to display name
        else desc = config.author + " — " + config.name; // Else, set to the default minecraft disc naming scheme
        moderncustomdiscs.RESOURCES.lang(String.format("item.%s.%s.desc", moderncustomdiscs.MOD_ID, ID), desc); // Register lang

        // Register assets
        if (texture.exists()) { // Register custom texture and model (if it exists)
            moderncustomdiscs.RESOURCES.texture("item", texture, ID);
            moderncustomdiscs.RESOURCES.itemModel(ID, ID);
        }
        else moderncustomdiscs.RESOURCES.itemModel(ID, "disc_base", "disc_center1", "disc_center2"); // Default texture

        // Register
        SOUND = moderncustomdiscs.MUSIC.register(name, () -> new SoundEvent(new ResourceLocation(moderncustomdiscs.MOD_ID, name))); // Register sound
        DISC_ITEM = moderncustomdiscs.DISCS.register(ID, 
            () -> new CustomDiscItem(SOUND, config.redstone, desc)); // Register item

        // Tags
        TAG_REGISTER.itemTag("music_discs", moderncustomdiscs.MOD_ID+":"+ID); // #minecraft:music_discs
        if (config.creeper) TAG_REGISTER.itemTag("creeper_drop_music_discs", moderncustomdiscs.MOD_ID+":"+ID); // #minecraft:creeper_drop_music_discs (if set to true in config)
    }
}

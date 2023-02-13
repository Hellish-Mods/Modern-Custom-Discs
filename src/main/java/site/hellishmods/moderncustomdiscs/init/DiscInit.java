package site.hellishmods.moderncustomdiscs.init;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import net.minecraftforge.fml.loading.FMLPaths;
import site.hellishmods.moderncustomdiscs.moderncustomdiscs;
import site.hellishmods.moderncustomdiscs.items.Disc;
import site.hellishmods.moderncustomdiscs.items.DiscConfig;
import site.hellishmods.moderncustomdiscs.util.ExceptionHandler;

// Init discs
public class DiscInit {
    // Constants and variables
    static final Gson JSON_LOADER = new Gson(); // Json reader
    static final File CONFIG = FMLPaths.CONFIGDIR.get().resolve(moderncustomdiscs.MOD_ID).toFile(); // Config dir
    public static final ArrayList<Disc> DISC_ITEMS = new ArrayList<Disc>(); // Disc items
    static File[] discs; // Discs

    public static final void GenerateDiscs() throws FileNotFoundException {
        if (!CONFIG.exists())
            CONFIG.mkdirs(); // Create "config/moderncustomdiscs"

        discs = CONFIG.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".ogg")); // Get all music files
        for (File disc : discs) {
            moderncustomdiscs.RESOURCES.sound(disc, "record");

            String id = FilenameUtils.removeExtension(disc.getName()); // Get sound id
            DiscConfig json = new DiscConfig(); // Make a new DiscConfig

            File jsonfile = CONFIG.toPath().resolve(id+".json").toFile(); // Get json file
            if (jsonfile.exists()) { // If it exists
                JsonReader reader = new JsonReader(new FileReader(jsonfile)); // Reader

                try {
                    json = JSON_LOADER.fromJson(reader, DiscConfig.class); // Try to read the file and load into json variable
                } catch(JsonSyntaxException e) {new ExceptionHandler(e, id);} // If it's formated wrong, log and stop caring
            }

            File texture = CONFIG.toPath().resolve(id+".png").toFile(); // Load texture file

            // Create a new disc and add it to DISC_ITEMS
            DISC_ITEMS.add(new Disc(id, json, texture));
        }
    }
}

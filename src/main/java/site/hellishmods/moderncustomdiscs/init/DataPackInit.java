package site.hellishmods.moderncustomdiscs.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import site.hellishmods.moderncustomdiscs.moderncustomdiscs;
import site.hellishmods.moderncustomdiscs.items.Disc;

// Create and load custom data pack
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = moderncustomdiscs.MOD_ID)
public class DataPackInit {
    @SubscribeEvent
    public static void onServerStart(final FMLServerStartedEvent e) { // On world start
        ResourcePackList packs = e.getServer().getPackRepository(); // Get datapacks

        try {
            Path tempPack = Files.createTempDirectory("ModernCustomDiscData"); // Create temp dir
            
            // Create pack.mcmeta
            File pack = new File(tempPack.toFile(), "pack.mcmeta");
            pack.createNewFile();
            FileWriter packwriter = new FileWriter(pack);
            packwriter.write("{\"pack\": {\"pack_format\": 6, \"description\": \"MCD's auto-loaded data\"}}");
            packwriter.close();
            
            // Create subdirs
            Path tags = tempPack.resolve("data").resolve("minecraft").resolve("tags").resolve("items");
            tags.toFile().mkdirs();

            // Create music_discs.json
            File tagjson = tags.resolve("music_discs.json").toFile();
            tagjson.createNewFile();
            List<String> tagitems = new ArrayList<>();

            // Create creeper_drop_music_discs.json
            File creepertagjson = tags.resolve("creeper_drop_music_discs.json").toFile();
            creepertagjson.createNewFile();
            List<String> creepertagitems = new ArrayList<>();

            // Add items to tags
            for (Disc disc : DiscInit.DISC_ITEMS) {
                tagitems.add('"' + moderncustomdiscs.MOD_ID + ':' + disc.ID + '"');
                if (disc.CREEPER) creepertagitems.add('"' + moderncustomdiscs.MOD_ID + ':' + disc.ID + '"');
            }

            // Write jsons
            FileWriter tagwriter = new FileWriter(tagjson);
            tagwriter.write("{\"replace\":false, \"values\":["+String.join(",", tagitems)+"]}");
            tagwriter.close();
            FileWriter creepertagwriter = new FileWriter(creepertagjson);
            creepertagwriter.write("{\"replace\":false, \"values\":["+String.join(",", creepertagitems)+"]}");
            creepertagwriter.close();

            // Register pack
            packs.addPackFinder(new FolderPackFinder(tempPack.getParent().toFile(), IPackNameDecorator.DEFAULT));
            packs.reload();
            packs.setSelected(ImmutableSet.<String>builder()
                .addAll(packs.getSelectedIds())
                .add("file/"+tempPack.toFile().getName())
                .build()
            );
            e.getServer().reloadResources(packs.getSelectedIds());

            // Clean up the temp data pack folder
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        FileUtils.deleteDirectory(tempPack.toFile());
                    } catch (IOException err) {}
            }));
        } catch (IOException err) {}
    }
}

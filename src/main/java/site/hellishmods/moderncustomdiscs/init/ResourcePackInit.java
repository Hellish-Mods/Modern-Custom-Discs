package site.hellishmods.moderncustomdiscs.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import site.hellishmods.moderncustomdiscs.moderncustomdiscs;
import site.hellishmods.moderncustomdiscs.items.Disc;

// Create custom resource pack
public class ResourcePackInit implements IPackFinder {
    public Path tmpDir; // Temp directory for resource pack

    @Override
    public void loadPacks(Consumer<ResourcePackInfo> consumer, IFactory factory) {
        try {
            tmpDir = Files.createTempDirectory("ModernCustomDiscAssets"); // Create tmp dir

            // Create pack.mcmeta
            File pack = new File(tmpDir.toFile(), "pack.mcmeta");
            pack.createNewFile();
            FileWriter packwriter = new FileWriter(pack);
            packwriter.write("{\"pack\": {\"pack_format\": 6, \"description\": \"MCD's auto-loaded assets\"}}");
            packwriter.close();

            // Create dirs
            Path assets = tmpDir.resolve("assets").resolve(moderncustomdiscs.MOD_ID);
            Path textures = assets.resolve("textures").resolve("item").resolve("custom");
            Path models = assets.resolve("models").resolve("item");
            Path lang = assets.resolve("lang");
            textures.toFile().mkdirs();
            models.toFile().mkdirs();
            lang.toFile().mkdirs();

            // Create sounds dir and copy .oggs from config
            File sounds = assets.resolve("sounds").toFile();
            sounds.mkdirs();
            for (File musicfile : DiscInit.discs) {
                FileUtils.copyFile(musicfile, sounds.toPath().resolve(musicfile.getName()).toFile());
            }

            // Create jsons
            File soundsjson = new File(assets.toFile(), "sounds.json");
            soundsjson.createNewFile();
            File langjson = new File(lang.toFile(), "en_us.json");
            langjson.createNewFile();

            // Json lists
            List<String> soundsProperties = new ArrayList<>();
            List<String> descriptions = new ArrayList<>();

            for (Disc disc : DiscInit.DISC_ITEMS) {
                // Create a model
                File discmodel = new File(models.toFile(), disc.ID);
                FileWriter modelwriter = new FileWriter(discmodel+".json");
                if (disc.TEXTURE.exists()) {
                    FileUtils.copyFile(disc.TEXTURE, textures.resolve(disc.NAME+".png").toFile());
                    modelwriter.write(String.format("{\"parent\": \"minecraft:item/generated\", \"textures\": {\"layer0\": \"%s:item/custom/%s\"}}", moderncustomdiscs.MOD_ID, disc.NAME));
                }
                else modelwriter.write(String.format("{\"parent\": \"%s:item/disc\"}", moderncustomdiscs.MOD_ID));
                modelwriter.close();

                soundsProperties.add(String.format("\"%s\": {\"sounds\": [{\"name\":\"%s:%s\", \"stream\": true}]}", disc.NAME, moderncustomdiscs.MOD_ID, disc.NAME)); // Add sound to sounds.json
                descriptions.add(String.format("\"item.%s.%s.desc\": \"%s\"", moderncustomdiscs.MOD_ID, disc.ID, disc.DESC)); // Add desc to en_us.json
            }

            // Write to jsons
            FileWriter soundwriter = new FileWriter(soundsjson);
            soundwriter.write("{"+String.join(",", soundsProperties)+"}");
            soundwriter.close();
            FileWriter langwriter = new FileWriter(langjson);
            langwriter.write("{"+String.join(",", descriptions)+"}");
            langwriter.close();

            // Create resource pack
            consumer.accept(ResourcePackInfo.create("mcdautoassets", true, () -> new FolderPack(tmpDir.toFile()), factory, ResourcePackInfo.Priority.BOTTOM, IPackNameDecorator.BUILT_IN));
        } catch (IOException err) {}
    }
}

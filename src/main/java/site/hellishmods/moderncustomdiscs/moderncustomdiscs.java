package site.hellishmods.moderncustomdiscs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import site.hellishmods.moderncustomdiscs.init.DiscInit;
import site.hellishmods.moderncustomdiscs.init.ResourcePackInit;

@Mod(moderncustomdiscs.MOD_ID)
public class moderncustomdiscs
{
    // Constants
    public static final String MOD_ID = "moderncustomdiscs";
    // Registries
    public static final DeferredRegister<Item> DISCS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<SoundEvent> MUSIC = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public moderncustomdiscs() throws FileNotFoundException {
        // Event bus
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // If client side
        if (FMLEnvironment.dist==Dist.CLIENT) {
            ResourcePackInit rp = new ResourcePackInit(); // Instantiate a resource pack

            Minecraft.getInstance().getResourcePackRepository().addPackFinder(rp); // Add the resource pack
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    FileUtils.deleteDirectory(rp.tmpDir.toFile()); // Clean up the temp resource pack folder
                } catch (IOException e) {}
            }));
        }

        // Init
        DiscInit.GenerateDiscs(); // Generate discs
        MUSIC.register(bus); // Register sounds
        DISCS.register(bus); // Register discs

        MinecraftForge.EVENT_BUS.register(this); // Register the mod
    }
}

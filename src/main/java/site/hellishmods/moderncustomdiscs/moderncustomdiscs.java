package site.hellishmods.moderncustomdiscs;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import site.hellishmods.digitality.lib.VirtualResourcePack;
import site.hellishmods.moderncustomdiscs.init.DiscInit;

@Mod(moderncustomdiscs.MOD_ID)
public class moderncustomdiscs
{
    // Constants
    public static final String MOD_ID = "moderncustomdiscs";
    public static final Logger LOGGER = LogManager.getLogger(); // Logger
    // Registries
    public static final DeferredRegister<Item> DISCS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<SoundEvent> MUSIC = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    // Digitality
    public static final VirtualResourcePack RESOURCES = new VirtualResourcePack(MOD_ID);

    public moderncustomdiscs() throws FileNotFoundException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus(); // Event bus

        // Init
        DiscInit.GenerateDiscs(); // Generate discs
        MUSIC.register(bus); // Register sounds
        DISCS.register(bus); // Register discs

        MinecraftForge.EVENT_BUS.register(this); // Register the mod
    }
}

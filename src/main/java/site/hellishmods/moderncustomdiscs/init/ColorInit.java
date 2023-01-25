package site.hellishmods.moderncustomdiscs.init;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import site.hellishmods.moderncustomdiscs.moderncustomdiscs;
import site.hellishmods.moderncustomdiscs.items.Disc;
import site.hellishmods.moderncustomdiscs.util.Parsing;

// Init disc colors
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = moderncustomdiscs.MOD_ID)
public class ColorInit {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT) // Client side only
    public static void registerColors(ColorHandlerEvent.Item e) {
        for (Disc disc : DiscInit.DISC_ITEMS) {
            if (!disc.TEXTURE.exists()) { // If no texture exists
                IItemColor disccolor = (s, index) -> {
                    return Parsing.parseColorHex(disc.COLORS[index]); // Take the colors and paint it
                };
                e.getItemColors().register(disccolor, disc.DISC_ITEM.get()); // Register
            }
        }
    }
}

package site.hellishmods.moderncustomdiscs.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLEnvironment;

// Custom disc item class
public class CustomDiscItem extends MusicDiscItem {
    final String DESC;

    public CustomDiscItem(RegistryObject<SoundEvent> sound, int redstone, String desc) {
        super(redstone, () -> sound.get(), new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)); // Some default values + passed comparator value
        DESC = desc; // Set description
    }
    
    @Override
    public ITextComponent getName(ItemStack i) { // Set name to localized "music disc"
        if (FMLEnvironment.dist==Dist.CLIENT) return new StringTextComponent(I18n.get("item.minecraft.music_disc_cat")).withStyle(Rarity.RARE.color);
        else return null; // Client side only
    }
}

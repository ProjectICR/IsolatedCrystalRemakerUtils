package superhelo.icrutils.items;

import net.minecraft.item.Item;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.handlers.ItemHandler;

public class ItemBase extends Item {

    public ItemBase(String registryName) {
        this.setRegistryName(registryName);
        this.setCreativeTab(ICRUtils.ICR_CREATIVE_TAB);
        this.setTranslationKey(ICRUtils.MODID + "." + registryName);
        ItemHandler.ITEM_REGISTER.add(this);
    }

}

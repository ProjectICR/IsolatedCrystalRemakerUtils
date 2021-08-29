package superhelo.icrutils.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.handlers.BlockHandler;
import superhelo.icrutils.handlers.ItemHandler;

public class BlockBase extends Block {

    public BlockBase(Material materialIn, String registryName) {
        super(materialIn);
        this.setHardness(3.0f);
        this.setRegistryName(registryName);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(ICRUtils.ICR_CREATIVE_TAB);
        this.setHarvestLevel("pickaxe", 2);
        this.setTranslationKey(ICRUtils.MODID + "." + registryName);
        BlockHandler.BLOCK_REGISTER.add(this);
        ItemHandler.ITEM_REGISTER.add(new ItemBlock(this).setRegistryName(registryName));
    }
}

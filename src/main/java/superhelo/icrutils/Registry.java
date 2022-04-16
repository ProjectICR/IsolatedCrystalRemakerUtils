package superhelo.icrutils;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import superhelo.icrutils.blocks.BlockHandler;
import superhelo.icrutils.items.ItemHandler;
import superhelo.icrutils.tileentity.TileEntityBase;
import superhelo.icrutils.tileentity.render.RenderInit;

@EventBusSubscriber(modid = ICRUtils.MODID)
public class Registry {

    private static void registerModelResourceLocation(@Nonnull Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        TileEntityBase.init();
        event.getRegistry().registerAll(BlockHandler.BLOCK_REGISTER.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        ICRUtils.BUCKET = getBucket();
        event.getRegistry().registerAll(ItemHandler.ITEM_REGISTER.toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        RenderInit.init();
        ItemHandler.ITEM_REGISTER.forEach(Registry::registerModelResourceLocation);
    }

    private static ItemStack getBucket() {
        ItemStack bucket = new ItemStack(ForgeModContainer.getInstance().universalBucket);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("FluidName", "astralsorcery.liquidstarlight");
        nbt.setInteger("Amount", 1000);
        bucket.setTagCompound(nbt);

        return bucket;
    }

}

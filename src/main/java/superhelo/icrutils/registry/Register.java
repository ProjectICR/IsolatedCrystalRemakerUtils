package superhelo.icrutils.registry;

import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import superhelo.icrutils.handlers.BlockHandler;
import superhelo.icrutils.handlers.ItemHandler;
import superhelo.icrutils.tileentity.TileEntityBase;
import superhelo.icrutils.tileentity.render.RenderInit;

@EventBusSubscriber
public class Register {

    private static void registerModelResourceLocation(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        TileEntityBase.init();
        event.getRegistry().registerAll(BlockHandler.BLOCK_REGISTER.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemHandler.ITEM_REGISTER.toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        RenderInit.init();
        for(Item item : ItemHandler.ITEM_REGISTER) {
            registerModelResourceLocation(item);
        }
    }
}

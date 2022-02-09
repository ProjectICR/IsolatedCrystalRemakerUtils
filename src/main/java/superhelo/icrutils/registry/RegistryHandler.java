package superhelo.icrutils.registry;

import java.util.Objects;
import javax.annotation.Nonnull;
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
public class RegistryHandler {

    private static void registerModelResourceLocation(@Nonnull Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BlockHandler.BLOCK_REGISTER.toArray(new Block[0]));
        TileEntityBase.init();
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemHandler.ITEM_REGISTER.toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        RenderInit.init();
        ItemHandler.ITEM_REGISTER.forEach(RegistryHandler::registerModelResourceLocation);
    }

}

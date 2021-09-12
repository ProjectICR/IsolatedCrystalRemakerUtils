package superhelo.icrutils.event;

import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import superhelo.icrutils.handlers.BlockHandler;

@EventBusSubscriber
public class BlockPlace {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockPlace(EntityPlaceEvent event) {
        World world = event.getWorld();
        if (!world.isRemote && !event.isCanceled()) {
            if (world.getBlockState(event.getPos().down()).getBlock() == BlockHandler.CEREMONIALCOLUMN) {
                event.setCanceled(true);
            }
        }
    }
}

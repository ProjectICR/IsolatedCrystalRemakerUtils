package superhelo.icrutils.event.listener;

import java.util.Collections;
import java.util.Objects;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.capability.CapabilityHandler;
import superhelo.icrutils.capability.StoreStageData;
import superhelo.icrutils.capability.StoreStageDataProvider;
import superhelo.icrutils.network.PacketHandler;
import superhelo.icrutils.network.PacketStageSync;
import superhelo.icrutils.network.PacketStageSync.Mode;

@EventBusSubscriber
public class EventListener {

    @SubscribeEvent
    public static void addCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(ICRUtils.MODID, "stage_data"), new StoreStageDataProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        StoreStageData original = event.getOriginal().getCapability(CapabilityHandler.STORE_STAGE_DATA, null);
        StoreStageData now = event.getEntityPlayer().getCapability(CapabilityHandler.STORE_STAGE_DATA, null);

        if (Objects.nonNull(original) && Objects.nonNull(now)) {
            now.setStage(original.getStages());
        }

    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof EntityPlayerSP) {
            PacketHandler.INSTANCE.sendToServer(PacketStageSync.load(Collections.emptySet(), Mode.SYNC));
        }

    }

}

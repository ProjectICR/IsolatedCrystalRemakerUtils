package superhelo.icrutils.stage;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import superhelo.icrutils.capability.CapabilityHandler;
import superhelo.icrutils.network.PacketHandler;
import superhelo.icrutils.network.PacketStageSync;
import superhelo.icrutils.network.PacketStageSync.Mode;

@SuppressWarnings("unchecked")
public class StageUtils {

    public static void addStageToPlayer(EntityPlayer player, String stage) {
        modifyStage(player, Sets.newHashSet(stage), Mode.ADD);
    }

    public static void removeStageFromPlayer(EntityPlayer player, String stage) {
        modifyStage(player, Sets.newHashSet(stage), Mode.REMOVE);
    }

    public static void setStagesFromPlayer(EntityPlayer player, Set<String> stages) {
        modifyStage(player, stages, Mode.SET);
    }

    public static void clearStagesFromPlayer(EntityPlayer player) {
        modifyStage(player, Collections.EMPTY_SET, Mode.CLEAR);
    }

    public static Set<String> getStagesFromPlayer(EntityPlayer player) {
        return player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null) ?
            Sets.newHashSet(player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null).getStages().iterator()) : Collections.EMPTY_SET;
    }

    public static boolean hasStage(EntityPlayer player, String stage) {
        if (Objects.nonNull(stage) && player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null)) {
            return player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null).hasStage(stage);
        }

        return true;
    }

    public static boolean hasStage(EntityPlayer player, ItemStack stack) {
        return hasStage(player, StageHandler.getStage(stack));
    }

    private static void modifyStage(EntityPlayer player, Set<String> stages, Mode mode) {
        if (stages.contains(null) || !player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null)) {
            return;
        }

        mode.getConsumer().accept(player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null), stages);

        if (player instanceof EntityPlayerMP) {
            PacketHandler.INSTANCE.sendTo(new PacketStageSync(stages, mode), (EntityPlayerMP) player);
        }
    }

}

package superhelo.icrutils.stage;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import superhelo.icrutils.cap.CapabilityHandler;
import superhelo.icrutils.cap.IStoreStageData;
import superhelo.icrutils.network.PacketHandler;
import superhelo.icrutils.network.PacketStageSync;
import superhelo.icrutils.network.PacketStageSync.Mode;

@SuppressWarnings("unchecked")
public class StageUtils {

    public static void addStageToPlayer(EntityPlayer player, String stage) {
        modifyStage(player, Sets.newHashSet(stage), Mode.ADD, data -> data.addStage(stage));
    }

    public static void removeStageFromPlayer(EntityPlayer player, String stage) {
        modifyStage(player, Sets.newHashSet(stage), Mode.REMOVE, data -> data.removeStage(stage));
    }

    public static void setStagesFromPlayer(EntityPlayer player, Set<String> stages) {
        modifyStage(player, stages, Mode.SET, data -> data.setStage(stages));
    }

    public static void clearStagesFromPlayer(EntityPlayer player) {
        modifyStage(player, Collections.EMPTY_SET, Mode.CLEAR, IStoreStageData::clearStage);
    }

    public static Set<String> getStagesFromPlayer(EntityPlayer player) {
        return player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null) ?
            player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null).getStages() : Collections.EMPTY_SET;
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

    private static void modifyStage(EntityPlayer player, Set<String> stages, Mode mode, Consumer<IStoreStageData> consumer) {
        if (stages.stream().anyMatch(Objects::isNull) || !player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null)) {
            return;
        }

        consumer.accept(player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null));

        if (player instanceof EntityPlayerMP) {
            PacketHandler.INSTANCE.sendTo(new PacketStageSync(stages, mode), (EntityPlayerMP) player);
        }
    }

}

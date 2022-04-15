package superhelo.icrutils.utils;

import java.util.Objects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import superhelo.icrutils.handlers.StageHandler;

public class StageUtils {

    public static void addStageToPlayer(EntityPlayer player, String stage) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean(stage, true);
        player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).merge(nbt);
    }

    public static boolean hasStage(EntityPlayer player, String stage) {
        if (Objects.nonNull(stage)) {
            return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getBoolean(stage);
        }

        return false;
    }

    public static boolean hasStage(EntityPlayer player, ItemStack stack) {
        if (!stack.isEmpty() && StageHandler.hasStage(stack)) {
            return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getBoolean(StageHandler.getStage(stack));
        }

        return false;
    }

}

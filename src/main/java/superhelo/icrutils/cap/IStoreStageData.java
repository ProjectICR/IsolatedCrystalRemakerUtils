package superhelo.icrutils.cap;

import java.util.Set;
import javax.annotation.Nonnull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IStoreStageData extends INBTSerializable<NBTTagCompound> {

    @Nonnull
    Set<String> getStages();

    boolean hasStage(String stage);

    void addStage(String stage);

    void removeStage(String stage);

    void setStage(@Nonnull Set<String> stages);

    void clearStage();

}

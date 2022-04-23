package superhelo.icrutils.capability;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nonnull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;

public class StoreStageData implements IStoreStageData {

    private Set<String> stages = Sets.newHashSet();

    @Nonnull
    @Override
    public Set<String> getStages() {
        return stages;
    }

    @Override
    public boolean hasStage(String stage) {
        return stages.contains(stage);
    }

    @Override
    public void addStage(String stage) {
        this.stages.add(stage);
    }

    @Override
    public void removeStage(String stage) {
        this.stages.remove(stage);
    }

    @Override
    public void setStage(@Nonnull Set<String> stages) {
        this.stages = Sets.newHashSet(stages.iterator());
    }

    @Override
    public void clearStage() {
        this.stages.clear();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        nbt.setTag("stages", list);
        stages.forEach(stage -> list.appendTag(new NBTTagString(stage)));
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList list = nbt.getTagList("stages", NBT.TAG_STRING);

        for (int i = 0; i < list.tagCount(); i++) {
            stages.add(list.getStringTagAt(i));
        }
    }

}

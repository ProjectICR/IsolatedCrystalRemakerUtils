package superhelo.icrutils.capability;

import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;
import superhelo.icrutils.utils.nbt.TagCompoundProxy;

public class StoreStageData {

    private Set<String> stages = Sets.newHashSet();

    @Nonnull
    public Set<String> getStages() {
        return stages;
    }

    public boolean hasStage(String stage) {
        return stages.contains(stage);
    }

    public void addStage(String stage) {
        if (Objects.nonNull(stage)) {
            this.stages.add(stage);
        }
    }

    public void removeStage(String stage) {
        this.stages.remove(stage);
    }

    public void setStage(@Nonnull Set<String> stages) {
        this.stages = Sets.newHashSet(stages.iterator());
    }

    public void clearStage() {
        this.stages.clear();
    }

    public NBTTagCompound serializeNBT() {
        NBTTagList list = new NBTTagList();
        stages.forEach(stage -> list.appendTag(new NBTTagString(stage)));
        return new TagCompoundProxy().setTag("stages", list).getNbt();
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        nbt.getTagList("stages", NBT.TAG_STRING).forEach(nbtBase -> stages.add(((NBTTagString) nbtBase).getString()));
    }

}

package superhelo.icrutils.event;

import com.google.common.collect.Lists;
import crafttweaker.api.item.IIngredient;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class StarLightRecipeEvent extends BaseEvent {

    private final List<EntityItem> entityItemsInSamePos;
    private final EntityItem input;
    private final BlockPos pos;
    private List<ItemStack> additionalOutput = Lists.newArrayList();
    private List<IIngredient> additionalInput = Lists.newArrayList();
    private ItemStack output;
    private int seconds;

    public StarLightRecipeEvent(EntityItem input, ItemStack output, List<EntityItem> list, BlockPos pos, int seconds) {
        this.entityItemsInSamePos = list;
        this.seconds = seconds;
        this.output = output;
        this.input = input;
        this.pos = pos;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(@Nonnull ItemStack output) {
        this.output = output;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EntityItem getInput() {
        return input;
    }

    public List<EntityItem> getEntityItemsInSamePos() {
        return entityItemsInSamePos;
    }

    public List<ItemStack> getAdditionalOutput() {
        return this.additionalOutput;
    }

    public void setAdditionalOutput(@Nonnull List<ItemStack> additionalOutput) {
        this.additionalOutput = additionalOutput;
    }

    public void addOutput(@Nonnull ItemStack stack) {
        this.additionalOutput.add(stack);
    }

    public List<IIngredient> getAdditionalInput() {
        return this.additionalInput;
    }

    public void setAdditionalInput(@Nonnull List<IIngredient> additionalInput) {
        this.additionalInput = additionalInput;
    }

    public void addInput(@Nonnull IIngredient stack) {
        this.additionalInput.add(stack);
    }

}

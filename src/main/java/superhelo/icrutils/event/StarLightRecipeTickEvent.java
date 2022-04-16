package superhelo.icrutils.event;

import com.google.common.collect.Lists;
import crafttweaker.api.item.IIngredient;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class StarLightRecipeTickEvent extends BaseEvent {

    private int seconds;
    private final List<EntityItem> entityItemsInSamePos;
    private final String name;
    private final BlockPos pos;
    private final EntityItem input;
    private List<ItemStack> additionalOutput;
    private List<IIngredient> additionalInput;
    private ItemStack output;

    public StarLightRecipeTickEvent(String name, EntityItem input, ItemStack output, List<EntityItem> list, BlockPos pos, int seconds, List<IIngredient> additionalInput, List<ItemStack> additionalOutput) {
        this.additionalOutput = additionalOutput.stream().map(ItemStack::copy).collect(Collectors.toList());
        this.additionalInput = Lists.newArrayList(additionalInput);
        this.entityItemsInSamePos = list;
        this.output = output.copy();
        this.seconds = seconds;
        this.input = input;
        this.name = name;
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

    public World getWorld() {
        return input.world;
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

    public void addAdditionalOutput(ItemStack stack) {
        this.additionalOutput.add(stack);
    }

    public List<IIngredient> getAdditionalInput() {
        return this.additionalInput;
    }

    public void setAdditionalInput(List<IIngredient> additionalInput) {
        this.additionalInput = additionalInput;
    }

    public void addAdditionalInput(IIngredient iIngredient) {
        this.additionalInput.add(iIngredient);
    }

    public String getName() {
        return name;
    }

}

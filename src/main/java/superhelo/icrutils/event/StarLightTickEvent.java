package superhelo.icrutils.event;

import com.google.common.collect.Lists;
import crafttweaker.api.item.IIngredient;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class StarLightTickEvent extends BaseEvent {

    private int seconds;
    private final List<IIngredient> additionalInput;
    private final List<ItemStack> additionalOutput;
    private final List<EntityItem> entityItemsInSamePos;
    private final List<IIngredient> extraInput = Lists.newArrayList();
    private final String name;
    private final BlockPos pos;
    private final EntityItem input;
    private ItemStack output;

    public StarLightTickEvent(String name, EntityItem input, ItemStack output, List<EntityItem> list, BlockPos pos, int seconds, List<IIngredient> additionalInput, List<ItemStack> additionalOutput) {
        this.additionalOutput = additionalOutput.stream().map(ItemStack::copy).collect(Collectors.toList());
        this.additionalInput = additionalInput;
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

    public void setOutput(ItemStack output) {
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

    public List<IIngredient> getAdditionalInput() {
        return Collections.unmodifiableList(this.additionalInput);
    }

    public void addAdditionalOutput(ItemStack stack) {
        this.additionalOutput.add(stack);
    }

    public void setAdditionalOutput(List<ItemStack> additionalOutput) {
        this.additionalOutput.clear();
        this.additionalOutput.addAll(additionalOutput);
    }

    public List<IIngredient> getInputEventAdd() {
        return this.extraInput;
    }

    public void addInput(IIngredient iIngredient) {
        this.extraInput.add(iIngredient);
    }

    public String getName() {
        return name;
    }

}

package superhelo.icrutils.recipe;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.EntityEntry;
import superhelo.icrutils.utils.item.ItemStackProxy;

public class FusionTableRecipe {

    public static final List<FusionTableRecipe> RECIPES = Lists.newArrayList();

    private final List<ItemStackProxy> inputs;
    private final EntityEntry output;
    private final int tick;

    private FusionTableRecipe(EntityEntry output, List<ItemStack> inputs, int seconds) {
        this.output = output;
        this.tick = seconds * 20;
        this.inputs = inputs.stream().map(ItemStackProxy::new).collect(Collectors.toList());
        RECIPES.add(this);
    }

    public static FusionTableRecipe create(EntityEntry output, List<ItemStack> inputs, int seconds) {
        if (inputs.size() <= 4) {
            return new FusionTableRecipe(output, inputs, seconds);
        }
        return null;
    }

    public boolean match(List<ItemStackProxy> inputs) {
        return this.inputs.containsAll(inputs);
    }

    public int getTick() {
        return tick;
    }

    public EntityEntry getOutput() {
        return output;
    }

    public List<ItemStack> getInputs() {
        return inputs.stream().map(ItemStackProxy::getStack).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FusionTableRecipe)) {
            return false;
        }

        FusionTableRecipe that = (FusionTableRecipe) o;

        if (tick != that.tick || !inputs.equals(that.inputs)) {
            return false;
        }
        return output.equals(that.output);
    }

    @Override
    public int hashCode() {
        int result = inputs.hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + tick;
        return result;
    }

}

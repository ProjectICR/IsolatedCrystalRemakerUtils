package superhelo.icrutils.utils.item;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import superhelo.icrutils.utils.Utils;

public class ItemStackProxy {

    private final ItemStack stack;

    public ItemStackProxy(@Nonnull ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof ItemStackProxy) {
            return Utils.areItemStacksEqual(this.stack, ((ItemStackProxy) o).stack);
        }

        return false;
    }

}

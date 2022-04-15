package superhelo.icrutils.mixins;

import moze_intel.projecte.gameObjs.container.slots.transmutation.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import superhelo.icrutils.handlers.StageHandler;
import superhelo.icrutils.utils.StageUtils;

@Mixin(value = SlotOutput.class)
public abstract class MixinSlotOutput extends SlotItemHandler {

    public MixinSlotOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        throw new AssertionError("Cannot happen");
    }

    @Inject(method = "canTakeStack", at = @At(value = "HEAD"), cancellable = true)
    private void injectCanTakeStack(EntityPlayer player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = this.getStack();

        if (StageHandler.hasStage(stack) && !StageUtils.hasStage(player, stack)) {
            cir.setReturnValue(false);
        }

    }

}

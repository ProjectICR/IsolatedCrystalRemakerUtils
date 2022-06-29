package superhelo.icrutils.mixins;

import moze_intel.projecte.gameObjs.container.slots.transmutation.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import superhelo.icrutils.stage.StageUtils;

@Mixin(value = SlotOutput.class)
public abstract class MixinSlotOutput extends SlotItemHandler {

    public MixinSlotOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Inject(method = "canTakeStack", at = @At(value = "HEAD"), cancellable = true)
    private void injectCanTakeStack(EntityPlayer player, CallbackInfoReturnable<Boolean> cir) {
        if (!StageUtils.hasStageIgnoreNBT(player, this.getStack())) {
            cir.setReturnValue(false);
        }

    }

}

package superhelo.icrutils.mixins;

import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import superhelo.icrutils.stage.StageUtils;

@Mixin(value = TransmutationInventory.class, remap = false)
public abstract class MixinTransmutationInventory {

    @Final
    @Shadow
    public EntityPlayer player;

    @Final
    @Shadow
    public IItemHandlerModifiable outputs;

    @Inject(method = "writeIntoOutputSlot", at = @At(value = "HEAD"), cancellable = true)
    private void injectWriteIntoOutputSlot(int slot, ItemStack stack, CallbackInfo ci) {
        if (!StageUtils.hasStage(this.player, stack)) {
            this.outputs.setStackInSlot(slot, ItemStack.EMPTY);
            ci.cancel();
        }
    }

}

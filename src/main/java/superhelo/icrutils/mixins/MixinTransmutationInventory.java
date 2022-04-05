package superhelo.icrutils.mixins;

import java.util.Objects;
import moze_intel.projecte.gameObjs.container.inventory.TransmutationInventory;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.itemstages.ItemStages;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TransmutationInventory.class, remap = false)
public class MixinTransmutationInventory {

    @Final
    @Shadow
    public IItemHandlerModifiable outputs;

    @Final
    @Shadow
    public EntityPlayer player;

    @Inject(method = "writeIntoOutputSlot", at = @At(value = "HEAD"), cancellable = true)
    private void injectWriteIntoOutputSlot(int slot, ItemStack stack, CallbackInfo ci) {
        String stage = ItemStages.getStage(stack);

        if (Objects.nonNull(stage) && !GameStageHelper.hasStage(this.player, stage)) {
            this.outputs.setStackInSlot(slot, ItemStack.EMPTY);
            ci.cancel();
        }
    }

}

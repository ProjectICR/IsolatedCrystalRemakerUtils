package superhelo.icrutils.mixin;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import superhelo.icrutils.recipe.StarLightRecipe;

@Mixin(value = EntityItem.class, remap = false)
public abstract class MixinEntityItem {

    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V", shift = Shift.AFTER))
    private void injectOnUpdate(CallbackInfo ci) {
        if (StarLightRecipe.haveRecipe(this.getItem())) {
            StarLightRecipe.getRecipeByOutput(this.getItem()).test((EntityItem) (Object) this);
        }
    }

}

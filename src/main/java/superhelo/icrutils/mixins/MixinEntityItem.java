package superhelo.icrutils.mixins;

import hellfirepvp.astralsorcery.common.block.fluid.FluidBlockLiquidStarlight;
import java.util.Objects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import superhelo.icrutils.recipe.StarLightRecipe;
import superhelo.icrutils.recipe.StarLightUtils;

@Mixin(value = EntityItem.class)
public abstract class MixinEntityItem extends Entity {

    @Shadow
    private int age;
    @Shadow(remap = false)
    public int lifespan;

    public MixinEntityItem(World worldIn) {
        super(worldIn);
    }

    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;getItem()Lnet/minecraft/item/ItemStack;", ordinal = 2), cancellable = true)
    private void injectOnUpdate(CallbackInfo ci) {
        ItemStack item = this.getItem();

        if (!this.world.isRemote && this.age >= lifespan) {
            int hook = net.minecraftforge.event.ForgeEventFactory.onItemExpire((EntityItem) (Object) this, item);
            if (hook < 0) {
                this.setDead();
            } else {
                this.lifespan += hook;
            }
        }

        if (item.isEmpty()) {
            this.setDead();
        }

        IBlockState state = world.getBlockState(new BlockPos(this));
        if (!this.world.isRemote && StarLightUtils.haveRecipeForMainInput(item) && state.getBlock() instanceof FluidBlockLiquidStarlight && state.getValue(BlockFluidBase.LEVEL) == 0) {
            StarLightRecipe recipe = StarLightUtils.getRecipeByMainInput(item);
            if (Objects.nonNull(recipe)) {
                recipe.test((EntityItem) (Object) this);
            }
        }

        ci.cancel();
    }

}

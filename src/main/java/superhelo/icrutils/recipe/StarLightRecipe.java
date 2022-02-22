package superhelo.icrutils.recipe;

import com.google.common.collect.Lists;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import hellfirepvp.astralsorcery.common.block.fluid.FluidBlockLiquidStarlight;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import superhelo.icrutils.event.StarLightRecipeEvent;
import superhelo.icrutils.handlers.RecipeHandler;
import superhelo.icrutils.utils.Utils;

public class StarLightRecipe implements Predicate<EntityItem> {

    private final int seconds;
    private final ItemStack output;
    private final IIngredient input;

    public StarLightRecipe(ItemStack output, IIngredient input, int seconds) {
        this.input = input;
        this.output = output;
        this.seconds = seconds;
        RecipeHandler.STAR_LIGHT_RECIPE_RECIPE.put(CraftTweakerMC.getIItemStack(output), this);
    }

    public static boolean haveRecipe(ItemStack output) {
        return RecipeHandler.STAR_LIGHT_RECIPE_RECIPE.containsKey(CraftTweakerMC.getIItemStack(output));
    }

    public static StarLightRecipe getRecipeByOutput(ItemStack output) {
        return RecipeHandler.STAR_LIGHT_RECIPE_RECIPE.get(CraftTweakerMC.getIItemStack(output));
    }

    public int getSeconds() {
        return seconds;
    }

    public IIngredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public boolean test(EntityItem entityItem) {
        World world = entityItem.world;
        BlockPos pos = Utils.getBlockPos(entityItem);
        IBlockState state = world.getBlockState(pos);

        if (!world.isRemote && state.getBlock() instanceof FluidBlockLiquidStarlight && state.getValue(BlockFluidBase.LEVEL) == 0) {
            List<EntityItem> entityItems = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos));
            StarLightRecipeEvent event = new StarLightRecipeEvent(entityItem, output, entityItems, pos, seconds);

            if (!event.post()) {
                return false;
            }

            List<IIngredient> additionalInput = event.getAdditionalInput();
            List<ItemStack> stacksMatch = Lists.newArrayList();

            if (!additionalInput.isEmpty()) {
                RecipeMatcher matcher = new RecipeMatcher(additionalInput, entityItems.stream().map(EntityItem::getItem).collect(Collectors.toList()));
                if (matcher.match()) {
                    stacksMatch = matcher.stacksMatch;
                } else {
                    return false;
                }
            }

            NBTTagCompound nbt = entityItem.getEntityData();
            if (!nbt.hasKey("recipeTime")) {
                nbt.setInteger("recipeTime", 1);
            } else {
                nbt.setInteger("recipeTime", nbt.getInteger("recipeTime") + 1);
            }

            if (nbt.getInteger("recipeTime") >= (event.getSeconds() * 20)) {
                Utils.shrinkEntityItem(entityItem);
                stacksMatch.forEach(stack -> stack.shrink(1));
                Utils.spawnEntity(world, pos.up(), event.getOutput());
                event.getAdditionalOutput().forEach(stack -> Utils.spawnEntity(world, pos.up(), stack));
                return true;
            }
        }
        return false;
    }

    private static class RecipeMatcher {

        private final List<ItemStack> stacksMatch = Lists.newArrayList();
        private final List<IIngredient> additionalInput;
        private final List<ItemStack> stacksInWorld;
        private final boolean[] matchFlag;

        public RecipeMatcher(@Nonnull List<IIngredient> additionalInput, @Nonnull List<ItemStack> stacksInWorld) {
            this.additionalInput = additionalInput;
            this.stacksInWorld = stacksInWorld;
            this.matchFlag = new boolean[additionalInput.size()];
        }

        public boolean match() {
            if (additionalInput.isEmpty()) {
                return true;
            }

            int i = 0;
            for (IIngredient input : additionalInput) {
                for (ItemStack stack : stacksInWorld) {
                    if (i == additionalInput.size()) {
                        break;
                    } else if (Objects.nonNull(input) && input.matches(get(stack))) {
                        i++;
                        stacksMatch.add(stack);
                        matchFlag[additionalInput.indexOf(input)] = true;
                    }
                }
            }
            return flagsIsMatched();
        }

        @Nonnull
        public List<IIngredient> getAdditionalInput() {
            return additionalInput;
        }

        @Nonnull
        public List<ItemStack> getStacksInWorld() {
            return stacksInWorld;
        }

        @Nonnull
        public List<ItemStack> getStacksMatch() {
            return stacksMatch;
        }

        @Nullable
        private IItemStack get(ItemStack stack) {
            return CraftTweakerMC.getIItemStack(stack);
        }

        private boolean flagsIsMatched() {
            for (boolean flag : matchFlag) {
                if (!flag) {
                    return false;
                }
            }
            return true;
        }

    }

}

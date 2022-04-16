package superhelo.icrutils.recipe;

import com.google.common.collect.Maps;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;
import superhelo.icrutils.event.StarLightRecipeTickEvent;
import superhelo.icrutils.utils.Utils;

public class StarLightRecipe implements Predicate<EntityItem> {

    public static final String NBT_KEY = "starLightRecipeTime";
    private final String name;
    private final int seconds;
    private final ItemStack output;
    private final IIngredient input;
    private final List<ItemStack> additionalOutput;
    private final List<IIngredient> additionalInput;

    public static final Map<String, StarLightRecipe> STAR_LIGHT_RECIPE_MAP = Maps.newHashMap();

    public StarLightRecipe(String name, ItemStack output, IIngredient input, int seconds, List<ItemStack> additionalOutput, List<IIngredient> additionalInput) {
        this.name = name;
        this.input = input;
        this.output = output;
        this.seconds = seconds;
        this.additionalInput = additionalInput;
        this.additionalOutput = additionalOutput;
        STAR_LIGHT_RECIPE_MAP.put(name, this);
    }

    public String getName() {
        return name;
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

    public List<ItemStack> getAdditionalOutput() {
        return additionalOutput;
    }

    public List<IIngredient> getAdditionalInput() {
        return additionalInput;
    }

    @Override
    public boolean test(EntityItem entityItem) {
        World world = entityItem.world;
        BlockPos pos = new BlockPos(entityItem);

        List<EntityItem> entityItems = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos));
        StarLightRecipeTickEvent event = new StarLightRecipeTickEvent(name, entityItem, output, entityItems, pos, seconds, additionalInput, additionalOutput);

        if (event.post()) {
            return false;
        }

        RecipeMatcher matcher = new RecipeMatcher(event.getAdditionalInput(), entityItems);
        if (!matcher.match()) {
            return false;
        }

        NBTTagCompound nbt = entityItem.getEntityData();
        if (!nbt.hasKey(NBT_KEY)) {
            nbt.setInteger(NBT_KEY, 1);
        } else {
            nbt.setInteger(NBT_KEY, nbt.getInteger(NBT_KEY) + 1);
        }

        if (nbt.getInteger(NBT_KEY) >= (event.getSeconds() * 20)) {
            entityItem.getItem().shrink(input.getAmount());
            matcher.stacksMatch.forEach((amount, item) -> item.shrink(amount));
            Utils.spawnEntityItem(world, pos.up(), event.getOutput());
            event.getAdditionalOutput().forEach(stack -> Utils.spawnEntityItem(world, pos.up(), stack));
            if (!entityItem.getItem().isEmpty()) {
                nbt.setInteger(NBT_KEY, 0);
            }
            return true;
        }
        return false;
    }

    private static class RecipeMatcher {

        private final Map<Integer, ItemStack> stacksMatch = Maps.newHashMap();
        private final List<IIngredient> additionalInput;
        private final List<ItemStack> stacksInWorld;

        public RecipeMatcher(List<IIngredient> additionalInput, List<EntityItem> stacksInWorld) {
            this.additionalInput = additionalInput.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            this.stacksInWorld = stacksInWorld.stream()
                .filter(item -> {
                    NBTTagCompound nbt = item.getEntityData();
                    return nbt.getBoolean("starLightInput") ||
                        nbt.getBoolean("starLightAdditionalInput") ||
                        additionalInput.stream().anyMatch(iIngredient -> iIngredient.matches(CraftTweakerMC.getIItemStack(item.getItem())));
                })
                .map(EntityItem::getItem)
                .collect(Collectors.toList());
        }

        public boolean match() {
            MutableInt i = new MutableInt();
            outside:
            for (IIngredient input : additionalInput) {
                for (ItemStack stack : stacksInWorld) {
                    if (input.matches(CraftTweakerMC.getIItemStack(stack))) {
                        i.increment();
                        stacksMatch.put(input.getAmount(), stack);
                        continue outside;
                    }
                }
            }

            return i.intValue() == additionalInput.size();
        }

    }

}

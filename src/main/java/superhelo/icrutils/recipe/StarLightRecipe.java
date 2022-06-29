package superhelo.icrutils.recipe;

import com.google.common.collect.Maps;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IMutableItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanhebben.zenscript.util.Pair;
import superhelo.icrutils.event.StarLightTickEvent;
import superhelo.icrutils.utils.ListUtils;
import superhelo.icrutils.utils.Utils;
import superhelo.icrutils.utils.WorldUtils;

public class StarLightRecipe implements Predicate<EntityItem> {

    private final String name;
    private final int seconds;
    private final ItemStack output;
    private final IIngredient input;
    private final List<ItemStack> additionalOutput;
    private final List<IIngredient> additionalInput;

    public static final Map<String, StarLightRecipe> STAR_LIGHT_RECIPE_MAP = Maps.newHashMap();
    public static final Map<Item, Pair<IngredientType, StarLightRecipe>> STAR_LIGHT_INPUT_MAP = Maps.newHashMap();
    private static final String NBT_KEY = "starLightRecipeTime";

    private StarLightRecipe(String name, ItemStack output, IIngredient input, int seconds, List<ItemStack> additionalOutput, List<IIngredient> additionalInput) {
        this.name = name;
        this.input = input;
        this.output = output;
        this.seconds = seconds;
        this.additionalInput = additionalInput;
        this.additionalOutput = additionalOutput;

        STAR_LIGHT_RECIPE_MAP.put(name, this);

        input.getItems().forEach(itemStack -> STAR_LIGHT_INPUT_MAP.put(Utils.getItem(itemStack), new Pair<>(IngredientType.MAIN, this)));
        additionalInput.forEach(ingredient -> ingredient.getItems()
            .stream()
            .filter(stack -> !STAR_LIGHT_INPUT_MAP.containsKey(Utils.getItem(stack)))
            .forEach(stack -> STAR_LIGHT_INPUT_MAP.put(Utils.getItem(stack), new Pair<>(IngredientType.ADDITION, this))));
    }

    @Nullable
    public static StarLightRecipe create(String name, ItemStack output, IIngredient input, int seconds, List<ItemStack> additionalOutput, List<IIngredient> additionalInput) {
        if (!StarLightUtils.haveRecipe(name)) {
            return new StarLightRecipe(name, output, input, seconds, additionalOutput, additionalInput);
        }
        return null;
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
        StarLightTickEvent event = new StarLightTickEvent(name, entityItem, output, entityItems, pos, seconds, additionalInput, additionalOutput);

        if (event.post()) {
            return false;
        }

        RecipeHelper helper = new RecipeHelper(event);
        if (!helper.match()) {
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
            helper.stacksMatch.forEach(ItemStack::shrink);
            WorldUtils.spawnEntityItem(world, pos.up(), event.getOutput());
            event.getAdditionalOutput().forEach(stack -> WorldUtils.spawnEntityItem(world, pos.up(), stack));
            if (!entityItem.getItem().isEmpty()) {
                nbt.setInteger(NBT_KEY, 0);
            }
            return true;
        }
        return false;
    }

    private static class RecipeHelper {

        private final List<IItemStack> stacksInWorld;
        private final List<IIngredient> additionalInput;
        private Map<ItemStack, Integer> stacksMatch = null;

        public RecipeHelper(StarLightTickEvent event) {
            this.additionalInput = ListUtils.mergeList(event.getAdditionalInput(), event.getInputEventAdd());
            this.stacksInWorld = event.getEntityItemsInSamePos()
                .stream()
                .filter(entityItem -> entityItem != event.getInput())
                .map(EntityItem::getItem)
                .map(CraftTweakerMC::getIItemStackMutable)
                .collect(Collectors.toList());
        }

        public boolean match() {
            if (additionalInput.size() == stacksInWorld.size() - 1) {
                int times = 0;
                out:
                for (IIngredient ingredient : additionalInput) {
                    for (IItemStack stack : stacksInWorld) {
                        if (ingredient.contains(stack)) {
                            times++;
                            continue out;
                        }
                    }
                }

                if (times == additionalInput.size()) {
                    stacksMatch = Maps.newHashMap();
                    stacksInWorld.forEach(stack -> stacksMatch.put(CraftTweakerMC.getItemStack((IMutableItemStack) stack), stack.getAmount()));
                    return true;
                }
            }

            return false;
        }

    }

}

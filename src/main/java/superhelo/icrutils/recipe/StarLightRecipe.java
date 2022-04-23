package superhelo.icrutils.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
import org.apache.commons.lang3.mutable.MutableInt;
import superhelo.icrutils.event.StarLightRecipeTickEvent;
import superhelo.icrutils.utils.Utils;

public class StarLightRecipe implements Predicate<EntityItem> {

    private final String name;
    private final int seconds;
    private final ItemStack output;
    private final IIngredient input;
    private final List<ItemStack> additionalOutput;
    private final List<IIngredient> additionalInput;

    public static final Set<Item> ADDITIONAL_INPUT_LIST = Sets.newHashSet();
    public static final Map<Item, StarLightRecipe> STAR_LIGHT_INPUT_MAP = Maps.newHashMap();
    public static final Map<Item, List<StarLightRecipe>> STAR_LIGHT_OUTPUT_MAP = Maps.newHashMap();
    public static final Map<String, StarLightRecipe> STAR_LIGHT_RECIPE_MAP = Maps.newHashMap();
    private static final String NBT_KEY = "starLightRecipeTime";

    private StarLightRecipe(String name, ItemStack output, IIngredient input, int seconds, List<ItemStack> additionalOutput, List<IIngredient> additionalInput) {
        this.name = name;
        this.input = input;
        this.output = output;
        this.seconds = seconds;
        this.additionalInput = additionalInput;
        this.additionalOutput = additionalOutput;

        STAR_LIGHT_RECIPE_MAP.put(name, this);
        input.getItems().forEach(stack -> STAR_LIGHT_INPUT_MAP.putIfAbsent(Utils.getItem(stack), this));
        additionalInput.forEach(inputList -> inputList.getItems().forEach(stack -> ADDITIONAL_INPUT_LIST.add(Utils.getItem(stack))));

        if (StarLightUtils.haveRecipeForOutput(output)) {
            StarLightUtils.getRecipeByOutput(output).add(this);
        } else {
            StarLightRecipe.STAR_LIGHT_OUTPUT_MAP.put(output.getItem(), Lists.newArrayList(this));
        }
    }

    @Nullable
    public static StarLightRecipe create(String name, ItemStack output, IIngredient input, int seconds, List<ItemStack> additionalOutput, List<IIngredient> additionalInput) {
        if (!StarLightUtils.haveRecipeForName(name)) {
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
        StarLightRecipeTickEvent event = new StarLightRecipeTickEvent(name, entityItem, output, entityItems, pos, seconds, additionalInput, additionalOutput);

        if (event.post()) {
            return false;
        }

        RecipeMatcher matcher = new RecipeMatcher(event);
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
            matcher.stacksMatch.forEach(ItemStack::shrink);
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

        private final Map<ItemStack, Integer> stacksMatch = Maps.newHashMap();
        private final List<IIngredient> additionalInput;
        private final List<ItemStack> stacksInWorld;

        public RecipeMatcher(StarLightRecipeTickEvent event) {
            this.additionalInput = Utils.addAllList(event.getAdditionalInput(), event.getExtraInput())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            this.stacksInWorld = event.getEntityItemsInSamePos()
                .stream()
                .filter(item ->
                    StarLightUtils.haveRecipeForInput(item.getItem()) ||
                        event.getExtraInput().stream().anyMatch(iIngredient -> iIngredient.matches(CraftTweakerMC.getIItemStack(item.getItem()))))
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
                        stacksMatch.put(stack, input.getAmount());
                        continue outside;
                    }
                }
            }

            return i.intValue() == additionalInput.size();
        }

    }

}

package superhelo.icrutils.utils;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class Utils {

    private Utils() {
    }

    public static Item getItem(IItemStack stack) {
        return CraftTweakerMC.getItemStack(stack).getItem();
    }

    public static List<ItemStack> getItemStacks(IItemStack... stack) {
        return Arrays.stream(CraftTweakerMC.getItemStacks(stack)).collect(Collectors.toList());
    }

    public static List<ItemStack> getItemStacks(IIngredient ingredient) {
        return ingredient.getItems()
            .stream()
            .map(stack -> stack.amount(ingredient.getAmount()))
            .map(CraftTweakerMC::getItemStack)
            .collect(Collectors.toList());
    }

    public static boolean isNotEmpty(ItemStack stack) {
        return !stack.isEmpty();
    }

    public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
        if (stackA.isEmpty() && stackB.isEmpty()) {
            return true;
        } else {
            return !stackA.isEmpty() && !stackB.isEmpty() && isItemStackEqual(stackA, stackB);
        }
    }

    private static boolean isItemStackEqual(ItemStack stackA, ItemStack stackB) {
        if (stackA.getCount() != stackB.getCount()) {
            return false;
        } else if (stackA.getItem() != stackB.getItem()) {
            return false;
        } else if (stackA.getItemDamage() != OreDictionary.WILDCARD_VALUE && stackA.getItemDamage() != stackB.getItemDamage()) {
            return false;
        } else if (stackA.getTagCompound() == null && stackB.getTagCompound() != null) {
            return false;
        } else {
            return (stackA.getTagCompound() == null || Objects.equals(stackA.getTagCompound(), stackB.getTagCompound()));
        }
    }

}
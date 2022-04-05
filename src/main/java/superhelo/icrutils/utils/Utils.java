package superhelo.icrutils.utils;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class Utils {

    public static String toUpperCamelCase(String str) {
        StringBuilder result = new StringBuilder();
        if (StringUtils.isBlank(str)) {
            return str;
        } else if (!str.contains("_")) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        for (String camel : str.split("_")) {
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    public static void spawnEntityItem(World world, BlockPos pos, ItemStack stack) {
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

    public static List<ItemStack> getItemStacks(IItemStack... stack) {
        return Arrays.stream(CraftTweakerMC.getItemStacks(stack)).collect(Collectors.toList());
    }

    public static List<ItemStack> getItemStacks(IIngredient iIngredient) {
        return iIngredient.getItems()
            .stream()
            .map(stack -> stack.amount(iIngredient.getAmount()))
            .map(CraftTweakerMC::getItemStack)
            .collect(Collectors.toList());
    }

    public static <T> List<T> addAllList(T element, List<T> list) {
        List<T> l = new ArrayList<>(1 + list.size());
        l.add(element);
        l.addAll(list);
        return l;
    }

}
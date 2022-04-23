package superhelo.icrutils.utils;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Utils {

    public static void spawnEntityItem(World world, BlockPos pos, ItemStack stack) {
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

    public static Item getItem(IItemStack stack) {
        return CraftTweakerMC.getItemStack(stack).getItem();
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

    public static <T> List<T> addAllList(List<? extends T> to, List<? extends T> from) {
        List<T> l = new ArrayList<>(to.size() + from.size());
        l.addAll(to);
        l.addAll(from);
        return l;
    }

    public static <T> List<T> addAllList(T element, List<T> list) {
        List<T> l = new ArrayList<>(1 + list.size());
        l.add(element);
        l.addAll(list);
        return l;
    }

}
package superhelo.icrutils.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class WorldUtils {

    private WorldUtils() {
    }

    public static void spawnEntity(World world, BlockPos pos, Entity entity) {
        entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(entity);
    }

    public static void spawnEntityItem(World world, BlockPos pos, ItemStack stack) {
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

}
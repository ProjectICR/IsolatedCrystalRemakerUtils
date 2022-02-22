package superhelo.icrutils.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Utils {

    public static BlockPos getBlockPos(Entity entity) {
        int x = MathHelper.floor(entity.posX);
        int y = MathHelper.floor(entity.posY);
        int z = MathHelper.floor(entity.posZ);

        return new BlockPos(x, y, z);
    }

    public static void shrinkEntityItem(EntityItem entityItem) {
        ItemStack stack = entityItem.getItem();
        stack.shrink(1);
    }

    public static void spawnEntity(World world, BlockPos pos, ItemStack stack) {
        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

}

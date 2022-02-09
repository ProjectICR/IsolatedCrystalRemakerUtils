package superhelo.icrutils.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFluxCore {

    int absorptionRate(World world, BlockPos pos);

    int absorptionQuantity(World world, BlockPos pos);

}

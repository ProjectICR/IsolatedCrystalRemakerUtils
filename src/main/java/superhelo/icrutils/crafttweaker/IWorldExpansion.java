package superhelo.icrutils.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import javax.annotation.Nullable;
import net.minecraft.tileentity.TileEntity;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
public class IWorldExpansion {

    @Nullable
    @ZenMethod
    public static ICeremonialColumnTile getCCTileInGame(IWorld world, IBlockPos pos) {
        TileEntity te = CraftTweakerMC.getWorld(world).getTileEntity(CraftTweakerMC.getBlockPos(pos));
        return te instanceof ICeremonialColumnTile ? (ICeremonialColumnTile) te : null;
    }

}

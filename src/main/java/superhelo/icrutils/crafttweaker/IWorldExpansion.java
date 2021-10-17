package superhelo.icrutils.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.tileentity.TileEntity;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.crafttweaker.api.ICeremonialColumnTileInGame;

@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
public class IWorldExpansion {

    @ZenMethod
    public static ICeremonialColumnTileInGame getCCTileInGame(IWorld world, IBlockPos pos) {
        TileEntity te = CraftTweakerMC.getWorld(world).getTileEntity(CraftTweakerMC.getBlockPos(pos));
        return te instanceof ICeremonialColumnTileInGame ? (ICeremonialColumnTileInGame) te : null;
    }
}

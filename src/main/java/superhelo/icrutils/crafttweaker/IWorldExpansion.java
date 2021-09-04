package superhelo.icrutils.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.tileentity.TileEntity;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.api.CeremonialColumnTileInGame;

@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
public class IWorldExpansion {

    @ZenMethod
    public static CeremonialColumnTileInGame getCCTileInGame(IWorld world, IBlockPos pos) {
        TileEntity te = CraftTweakerMC.getWorld(world).getTileEntity(CraftTweakerMC.getBlockPos(pos));
        return te instanceof CeremonialColumnTileInGame ? (CeremonialColumnTileInGame) te : null;
    }
}

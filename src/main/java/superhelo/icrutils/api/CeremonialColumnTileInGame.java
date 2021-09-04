package superhelo.icrutils.api;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.icrutils.support.CCTileInGame")
public interface CeremonialColumnTileInGame {

    @ZenGetter("stack")
    IItemStack getStackInInv();

    @ZenMethod
    @ZenSetter("stack")
    void setStackInInv(IItemStack stack);

    @ZenGetter("world")
    IWorld getIWorld();

    @ZenGetter("pos")
    IBlockPos getIBlockPos();
}

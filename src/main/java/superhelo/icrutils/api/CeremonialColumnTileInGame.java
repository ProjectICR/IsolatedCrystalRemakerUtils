package superhelo.icrutils.api;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
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
    IItemStack getStackInInventory();

    @ZenMethod
    @ZenSetter("stack")
    void setStackInInventory(IItemStack stack);

    @ZenGetter("world")
    IWorld getIWorld();

    @ZenGetter("pos")
    IBlockPos getIBlockPos();

    @ZenGetter("data")
    IData getData();

    @ZenMethod
    void updateData(IData data);
}

package superhelo.icrutils.api;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("icrutils.support.CCTileInGame")
public interface CeremonialColumnTileInGame {

    @ZenGetter("stack")
    IItemStack getStackInInv();

    @ZenMethod
    @ZenSetter("stack")
    void setStackInInv(IItemStack stack);
}

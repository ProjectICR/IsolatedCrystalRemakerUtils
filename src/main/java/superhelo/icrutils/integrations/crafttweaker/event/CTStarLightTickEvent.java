package superhelo.icrutils.integrations.crafttweaker.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import java.util.List;
import java.util.stream.Collectors;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;
import superhelo.icrutils.event.StarLightTickEvent;
import superhelo.icrutils.utils.Utils;

@ZenRegister
@ZenClass("mods.icrutils.support.StarLightTickEvent")
public class CTStarLightTickEvent implements IEventCancelable {

    private final StarLightTickEvent event;

    public CTStarLightTickEvent(StarLightTickEvent event) {
        this.event = event;
    }

    @ZenMethod
    @ZenGetter("name")
    public String getName() {
        return event.getName();
    }

    @ZenMethod
    @ZenGetter("seconds")
    public int getSeconds() {
        return event.getSeconds();
    }

    @ZenMethod
    @ZenSetter("seconds")
    public void setSeconds(int seconds) {
        event.setSeconds(seconds);
    }

    @ZenMethod
    @ZenGetter("output")
    public IItemStack getOutput() {
        return CraftTweakerMC.getIItemStack(event.getOutput());
    }

    @ZenMethod
    @ZenSetter("output")
    public void setOutput(IItemStack output) {
        event.setOutput(CraftTweakerMC.getItemStack(output));
    }

    @ZenMethod
    @ZenGetter("pos")
    public IBlockPos getBlockPos() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }

    @ZenMethod
    @ZenGetter("inputEntity")
    public IEntityItem getInputEntity() {
        return CraftTweakerMC.getIEntityItem(event.getInput());
    }

    @ZenMethod
    @ZenGetter("world")
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }

    @ZenMethod
    @ZenGetter("additionalInput")
    public List<IIngredient> getAdditionalInput() {
        return event.getAdditionalInput();
    }

    @ZenMethod
    public void addInput(IIngredient iIngredient) {
        event.addInput(iIngredient);
    }

    @ZenMethod
    public List<IIngredient> getInputEventAdd() {
        return event.getInputEventAdd();
    }

    @ZenMethod
    @ZenGetter("additionalOutput")
    public List<IItemStack> getAdditionalOutput() {
        return event.getAdditionalOutput().stream().map(CraftTweakerMC::getIItemStack).collect(Collectors.toList());
    }

    @ZenMethod
    @ZenSetter("additionalOutput")
    public void setAdditionalOutput(IItemStack[] stack) {
        event.setAdditionalOutput(Utils.getItemStacks(stack));
    }

    @ZenMethod
    public void addAdditionalOutput(IItemStack stack) {
        event.addAdditionalOutput(CraftTweakerMC.getItemStack(stack));
    }

    @ZenMethod
    public List<IEntityItem> getEntityItemsInSamePos() {
        return event.getEntityItemsInSamePos().stream().map(CraftTweakerMC::getIEntityItem).collect(Collectors.toList());
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }

}

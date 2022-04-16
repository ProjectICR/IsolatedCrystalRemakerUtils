package superhelo.icrutils.integrations.crafttweaker.action;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.stage.StageHandler;

@ZenRegister
@ZenClass("mods.icrutils.support.StageItemStack")
public class CTAddStageItemStack {

    @ZenMethod
    public static void register(String stage, IItemStack stack) {
        CraftTweakerAPI.apply(new ActionAddStageItemStack(stage, stack));
    }

    @ZenMethod
    public static void register(String stage, IOreDictEntry ore) {
        ore.getItems().forEach(stack -> register(stage, stack));
    }

    private static class ActionAddStageItemStack implements IAction {

        private final String stage;
        private final IItemStack stack;

        public ActionAddStageItemStack(String stage, IItemStack stack) {
            this.stage = stage;
            this.stack = stack;
        }

        @Override
        public void apply() {
            StageHandler.registerStageItemStack(stage, CraftTweakerMC.getItemStack(stack));
        }

        @Override
        public String describe() {
            return "Register " + stage + " for " + stack.toCommandString();
        }

    }

}

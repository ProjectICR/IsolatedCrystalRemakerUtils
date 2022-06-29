package superhelo.icrutils.integrations.crafttweaker.action;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemStack;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.EntityEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.recipe.FusionTableRecipe;
import superhelo.icrutils.utils.Utils;

@ZenRegister
@ZenClass("mods.icrutils.support.FusionTable")
public class CTFusionTableRecipe {

    @ZenMethod
    public static void addRecipe(IEntityDefinition output, IItemStack[] inputs, int time) {
        CraftTweakerAPI.apply(new ActionFusionTableRecipe((EntityEntry) output.getInternal(), Utils.getItemStacks(inputs), time));
    }

    private static class ActionFusionTableRecipe implements IAction {

        private final EntityEntry output;
        private final List<ItemStack> inputs;
        private final int time;

        public ActionFusionTableRecipe(EntityEntry output, List<ItemStack> inputs, int time) {
            this.time = time;
            this.output = output;
            this.inputs = inputs;
        }

        @Override
        public void apply() {
            FusionTableRecipe.create(output, inputs, time);
        }

        @Override
        public String describe() {
            return "Add a fusion recipe for " + output.getName();
        }

    }

}

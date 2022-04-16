package superhelo.icrutils.integrations.crafttweaker.action;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.recipe.StarLightRecipe;
import superhelo.icrutils.recipe.StarLightUtils;
import superhelo.icrutils.utils.Utils;

@ZenRegister
@ZenClass("mods.icrutils.support.StarLightRecipe")
public class CTStarLightRecipe {

    @ZenMethod
    public static void addRecipe(String name, IItemStack output, IIngredient input, int seconds) {
        addRecipe(name, output, input, seconds, new IIngredient[0], new IItemStack[0]);
    }

    @ZenMethod
    public static void addRecipe(String name, IItemStack output, IIngredient input, int seconds, IIngredient[] additionalInput) {
        addRecipe(name, output, input, seconds, additionalInput, new IItemStack[0]);
    }

    @ZenMethod
    public static void addRecipe(String name, IItemStack output, IIngredient input, int seconds, IIngredient[] additionalInput, IItemStack[] additionalOutput) {
        CraftTweakerAPI.apply(new ActionStarLightRecipe(name, output, input, seconds, Arrays.stream(additionalInput).collect(Collectors.toList()), Utils.getItemStacks(additionalOutput)));
    }

    public static class ActionStarLightRecipe implements IAction {

        private final String name;
        private final int seconds;
        private final IItemStack output;
        private final IIngredient input;
        private final List<ItemStack> additionalOutput;
        private final List<IIngredient> additionalInput;

        public ActionStarLightRecipe(String name, IItemStack output, IIngredient input, int seconds, List<IIngredient> additionalInput, List<ItemStack> additionalOutput) {
            this.name = name;
            this.input = input;
            this.output = output;
            this.seconds = seconds;
            this.additionalInput = additionalInput;
            this.additionalOutput = additionalOutput;
        }

        @Override
        public void apply() {
            new StarLightRecipe(name, CraftTweakerMC.getItemStack(output), input, seconds, additionalOutput, additionalInput);
        }

        @Override
        public boolean validate() {
            return !StarLightUtils.haveRecipeForName(name);
        }

        @Override
        public String describeInvalid() {
            return "Add a starlight recipe for " + output.toString() + "failed";
        }

        @Override
        public String describe() {
            return "Add a starlight recipe for " + output.toString();
        }
    }

}

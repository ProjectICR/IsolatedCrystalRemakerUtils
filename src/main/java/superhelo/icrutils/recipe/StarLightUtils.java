package superhelo.icrutils.recipe;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;

public class StarLightUtils {

    public static boolean haveRecipeForName(String name) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.containsKey(name);
    }

    public static boolean haveRecipeForInput(ItemStack input) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.values().stream().anyMatch(recipeIn -> recipeIn.getInput().matches(CraftTweakerMC.getIItemStack(input)));
    }

    public static boolean haveRecipeForOutput(ItemStack output) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.values().stream().anyMatch(recipeIn -> ItemStack.areItemStacksEqual(recipeIn.getOutput(), output));
    }

    public static StarLightRecipe getRecipeByName(String name) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.get(name);
    }

    public static StarLightRecipe getRecipeByInput(ItemStack input) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.values().stream().filter(recipeIn -> recipeIn.getInput().matches(CraftTweakerMC.getIItemStack(input))).findFirst().orElse(null);
    }

    public static StarLightRecipe getRecipeByOutput(ItemStack output) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.values().stream().filter(recipeIn -> ItemStack.areItemStacksEqual(recipeIn.getOutput(), output)).findFirst().orElse(null);
    }

}

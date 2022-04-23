package superhelo.icrutils.recipe;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StarLightUtils {

    public static boolean haveRecipeForName(String name) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.containsKey(name);
    }

    public static StarLightRecipe getRecipeByName(String name) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.get(name);
    }

    public static boolean haveRecipeForInput(ItemStack input) {
        Item item = input.getItem();
        return StarLightRecipe.STAR_LIGHT_INPUT_MAP.containsKey(item) || StarLightRecipe.ADDITIONAL_INPUT_LIST.contains(item);
    }

    public static boolean haveRecipeForMainInput(ItemStack input) {
        return StarLightRecipe.STAR_LIGHT_INPUT_MAP.containsKey(input.getItem());
    }

    public static boolean haveRecipeForAdditionalInput(ItemStack input) {
        return StarLightRecipe.ADDITIONAL_INPUT_LIST.contains(input.getItem());
    }

    public static StarLightRecipe getRecipeByMainInput(ItemStack input) {
        return StarLightRecipe.STAR_LIGHT_INPUT_MAP.get(input.getItem());
    }

    public static boolean haveRecipeForOutput(ItemStack output) {
        return StarLightRecipe.STAR_LIGHT_OUTPUT_MAP.containsKey(output.getItem());
    }

    public static List<StarLightRecipe> getRecipeByOutput(ItemStack output) {
        return StarLightRecipe.STAR_LIGHT_OUTPUT_MAP.get(output.getItem());
    }

}

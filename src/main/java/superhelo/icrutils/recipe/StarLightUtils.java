package superhelo.icrutils.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.util.Pair;

public final class StarLightUtils {

    private StarLightUtils() {
    }

    public static boolean haveRecipe(String name) {
        return StarLightRecipe.STAR_LIGHT_RECIPE_MAP.containsKey(name);
    }

    public static boolean haveRecipe(ItemStack input) {
        Item item = input.getItem();
        return StarLightRecipe.STAR_LIGHT_INPUT_MAP.containsKey(item) && StarLightRecipe.STAR_LIGHT_INPUT_MAP.get(item).getKey().equals(IngredientType.MAIN);
    }

    public static StarLightRecipe getRecipe(ItemStack input) {
        Pair<IngredientType, StarLightRecipe> pair = StarLightRecipe.STAR_LIGHT_INPUT_MAP.get(input.getItem());
        return pair.getKey().equals(IngredientType.MAIN) ? pair.getValue() : null;
    }

}

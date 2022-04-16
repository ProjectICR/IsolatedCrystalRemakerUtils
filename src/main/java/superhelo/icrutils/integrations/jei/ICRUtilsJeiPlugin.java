package superhelo.icrutils.integrations.jei;

import java.util.stream.Collectors;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.recipe.StarLightRecipe;

@JEIPlugin
public class ICRUtilsJeiPlugin implements IModPlugin {

    private static StarLightRecipesCategory STAR_LIGHT_RECIPES_CATEGORY;

    private static void addStarLightRecipes(IModRegistry registry) {
        registry.addRecipes(StarLightRecipe.STAR_LIGHT_RECIPE_MAP.values()
            .stream()
            .map(StarLightRecipesWrapper::new)
            .collect(Collectors.toList()), STAR_LIGHT_RECIPES_CATEGORY.getUid());
        registry.addRecipeCatalyst(ICRUtils.BUCKET, STAR_LIGHT_RECIPES_CATEGORY.getUid());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        STAR_LIGHT_RECIPES_CATEGORY = new StarLightRecipesCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(STAR_LIGHT_RECIPES_CATEGORY);
    }

    @Override
    public void register(IModRegistry registry) {
        addStarLightRecipes(registry);
    }

}

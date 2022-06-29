package superhelo.icrutils.integrations.jei;

import java.util.stream.Collectors;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.blocks.BlockHandler;
import superhelo.icrutils.integrations.jei.category.FusionTableRecipeCategory;
import superhelo.icrutils.integrations.jei.category.StarLightRecipesCategory;
import superhelo.icrutils.integrations.jei.wrapper.FusionTableRecipeWrapper;
import superhelo.icrutils.integrations.jei.wrapper.StarLightRecipesWrapper;
import superhelo.icrutils.recipe.FusionTableRecipe;
import superhelo.icrutils.recipe.StarLightRecipe;

@JEIPlugin
public class ICRUtilsJeiPlugin implements IModPlugin {

    private static StarLightRecipesCategory STAR_LIGHT_RECIPES_CATEGORY;
    private static FusionTableRecipeCategory FUSION_TABLE_RECIPE_CATEGORY;

    private static void addStarLightRecipes(IModRegistry registry) {
        registry.addRecipes(StarLightRecipe.STAR_LIGHT_RECIPE_MAP.values()
            .stream()
            .map(StarLightRecipesWrapper::new)
            .collect(Collectors.toList()), STAR_LIGHT_RECIPES_CATEGORY.getUid());
        registry.addRecipeCatalyst(ICRUtils.BUCKET, STAR_LIGHT_RECIPES_CATEGORY.getUid());
    }

    private static void addFusionTableRecipes(IModRegistry registry) {
        registry.addRecipes(FusionTableRecipe.RECIPES
            .stream()
            .map(FusionTableRecipeWrapper::new)
            .collect(Collectors.toList()), FUSION_TABLE_RECIPE_CATEGORY.getUid());
        registry.addRecipeCatalyst(new ItemStack(BlockHandler.FUSION_TABLE), FUSION_TABLE_RECIPE_CATEGORY.getUid());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        STAR_LIGHT_RECIPES_CATEGORY = new StarLightRecipesCategory(registry.getJeiHelpers().getGuiHelper());
        FUSION_TABLE_RECIPE_CATEGORY = new FusionTableRecipeCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(STAR_LIGHT_RECIPES_CATEGORY);
        registry.addRecipeCategories(FUSION_TABLE_RECIPE_CATEGORY);
    }

    @Override
    public void register(IModRegistry registry) {
        addStarLightRecipes(registry);
        addFusionTableRecipes(registry);
    }

}

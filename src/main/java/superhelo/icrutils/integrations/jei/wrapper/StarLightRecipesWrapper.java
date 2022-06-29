package superhelo.icrutils.integrations.jei.wrapper;

import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import java.util.stream.Collectors;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraftforge.fluids.FluidStack;
import superhelo.icrutils.recipe.StarLightRecipe;
import superhelo.icrutils.utils.ListUtils;
import superhelo.icrutils.utils.Utils;

public class StarLightRecipesWrapper implements BaseRecipeWrapper<StarLightRecipe> {

    private final StarLightRecipe recipe;

    public StarLightRecipesWrapper(StarLightRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, new FluidStack(BlocksAS.fluidLiquidStarlight, 1000));

        ingredients.setOutputs(VanillaTypes.ITEM, ListUtils.addObjectToListHead(recipe.getOutput(), recipe.getAdditionalOutput()));

        ingredients.setInputLists(VanillaTypes.ITEM, ListUtils.addObjectToListHead(recipe.getInput(), recipe.getAdditionalInput())
            .stream()
            .map(Utils::getItemStacks)
            .collect(Collectors.toList()));

    }

    @Override
    public StarLightRecipe getRecipe() {
        return recipe;
    }

}

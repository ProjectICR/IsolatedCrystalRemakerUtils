package superhelo.icrutils.integrations.jei;

import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import java.util.stream.Collectors;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;
import superhelo.icrutils.recipe.StarLightRecipe;
import superhelo.icrutils.utils.Utils;

public class StarLightRecipesWrapper implements IRecipeWrapper {

    private final StarLightRecipe recipe;

    public StarLightRecipesWrapper(StarLightRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, new FluidStack(BlocksAS.fluidLiquidStarlight, 1000));

        ingredients.setOutputs(VanillaTypes.ITEM, Utils.addAllList(recipe.getOutput(), recipe.getAdditionalOutput()));

        ingredients.setInputLists(VanillaTypes.ITEM, Utils.addAllList(recipe.getInput(), recipe.getAdditionalInput())
            .stream()
            .map(Utils::getItemStacks)
            .collect(Collectors.toList()));

    }

}

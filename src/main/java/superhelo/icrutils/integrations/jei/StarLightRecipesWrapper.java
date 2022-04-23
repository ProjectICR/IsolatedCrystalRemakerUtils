package superhelo.icrutils.integrations.jei;

import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.translation.I18n;
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

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer renderer = minecraft.fontRenderer;
        renderer.drawStringWithShadow(I18n.translateToLocalFormatted("icrutils.jei.time_required", recipe.getSeconds()), 64, -10, 0xFFFFFF);
    }

}
